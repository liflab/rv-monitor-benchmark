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
import ca.uqac.lif.synthia.Picker;
import monitorlab.Scenario;
import monitorlab.monitor.Monitor;
import monitorlab.monitor.MonitorExperiment;
import monitorlab.monitor.beepbeep3.BeepBeepMonitor;
import monitorlab.monitor.dummy.DummyMonitor;
import monitorlab.scenario.iterator.beepbeep3.HasNextProcessor;
import monitorlab.source.PullSource;

/**
 * On a stream of method names, checks the property that <tt>next</tt> is
 * always followed by <tt>hasNext</tt>. The source of events in this scenario
 * is an randomly generated sequence of method names.
 */
public class HasNextRandom extends Scenario<String>
{
	/**
	 * The name of this scenario
	 */
	public static transient String NAME = "HasNext (generated)";
	
	/**
	 * A source of randomly generated floating-point numbers
	 */
	protected Picker<Float> m_floatSource;
	
	/**
	 * Creates a new instance of this scenario
	 * @param monitor_name The name of the monitor used in the scenario
	 */
	public HasNextRandom(Picker<Float> float_source)
	{
		super(NAME, "", "HasNext");
		m_floatSource = float_source;
	}

	@Override
	public PullSource<String> getSource(MonitorExperiment<String> e, Region r)
	{
		IteratorRandomSource source = new IteratorRandomSource(m_floatSource, 10000);
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
		if (tool_name.compareTo(DummyMonitor.TOOL_NAME) == 0)
		{
			monitor = new DummyMonitor<String>();
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
