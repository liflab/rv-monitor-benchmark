/*
    A benchmark for runtime monitors
    Copyright (C) 2020 Laboratoire d'informatique formelle

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package monitorlab.scenario.iterator;

import ca.uqac.lif.labpal.Region;
import monitorlab.Scenario;
import monitorlab.monitor.Monitor;
import monitorlab.monitor.MonitorExperiment;
import monitorlab.monitor.beepbeep3.BeepBeepMonitor;
import monitorlab.scenario.iterator.beepbeep3.HasNextProcessor;
import monitorlab.source.PullSource;
import monitorlab.source.converter.Identity;

/**
 * On a stream of method names, checks the property that <tt>next</tt> is
 * always followed by <tt>hasNext</tt>.
 */
public class HasNext extends Scenario<String>
{
	/**
	 * The name of this scenario
	 */
	public static transient String NAME = "HasNext";
	
	/**
	 * Creates a new instance of this scenario
	 * @param monitor_name The name of the monitor used in the scenario
	 */
	public HasNext()
	{
		super("Iterator", "", "HasNext");
	}

	@Override
	public PullSource<String> getSource(MonitorExperiment<String> e, Region r)
	{
		IteratorSource source = new IteratorSource(Identity.instance);
		e.setSource(source);
		return source;
	}

	@Override
	public Monitor<String> getMonitor(MonitorExperiment<String> e, Region r)
	{
		Monitor<String> monitor = null;
		String tool_name = r.getString(MonitorExperiment.TOOL);
		if (tool_name == null)
		{
			return null;
		}
		if (tool_name.compareTo(BeepBeepMonitor.TOOL_NAME) == 0)
		{
			monitor = new BeepBeepMonitor<String>(new HasNextProcessor());
		}
		if (monitor != null)
		{
			e.setMonitor(monitor);
			e.setInput(MonitorExperiment.TOOL, tool_name);
			return monitor;
		}
		return null;
	}
}
