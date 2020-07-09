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

import ca.uqac.info.monitor.Event;
import ca.uqac.lif.labpal.Region;
import monitorlab.Scenario;
import monitorlab.monitor.ConvertedMonitor;
import monitorlab.monitor.Monitor;
import monitorlab.monitor.MonitorExperiment;
import monitorlab.monitor.beepbeep1.BeepBeep1Monitor;
import monitorlab.monitor.beepbeep3.BeepBeep3Monitor;
import monitorlab.monitor.dummy.DummyMonitor;
import monitorlab.scenario.iterator.beepbeep1.HasNextMonitor;
import monitorlab.scenario.iterator.beepbeep1.StringToAtom;
import monitorlab.scenario.iterator.beepbeep3.HasNextProcessor;

/**
 * On a stream of method names, checks the property that <tt>next</tt> is
 * always followed by <tt>hasNext</tt>.
 */
public abstract class HasNextScenario extends Scenario<String>
{
	public HasNextScenario(String name, String source_name, String property_name)
	{
		super(name, source_name, property_name);
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
		if (tool_name.compareTo(BeepBeep1Monitor.TOOL_NAME) == 0)
		{
			monitor = new ConvertedMonitor<Event,String>(new HasNextMonitor(), new StringToAtom().add("next").add("hasNext"));
		}
		if (tool_name.compareTo(BeepBeep3Monitor.TOOL_NAME) == 0)
		{
			monitor = new BeepBeep3Monitor<String>(new HasNextProcessor());
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
