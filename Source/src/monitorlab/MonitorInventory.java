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
package monitorlab;

import monitorlab.monitor.ForeignMonitorExperiment;
import monitorlab.monitor.MonitorExperiment;
import monitorlab.monitor.NativeMonitorExperiment;
import monitorlab.monitor.beepbeep1.BeepBeep1Monitor;
import monitorlab.monitor.beepbeep3.BeepBeep3Monitor;
import monitorlab.monitor.dummy.DummyNativeMonitor;

/**
 * A helper class that produces the proper instance of {@link MonitorExperiment},
 * depending on whether the monitor is a {@link NativeMonitor} or a
 * {@link ForeignMonitor}.
 * @author Sylvain Hallé
 *
 * @param <T> The type of events accepted by the monitor
 */
public class MonitorInventory<T>
{
	/**
	 * The names of the tools that are native monitors. 
	 * <strong>Caveat:</strong> you must hard-code the
	 * names into this array; if you create a new native monitor class, make sure
	 * to add it here! 
	 */
	protected static transient String[] NATIVE_MONITORS = new String[] {
			DummyNativeMonitor.TOOL_NAME, BeepBeep1Monitor.TOOL_NAME, BeepBeep3Monitor.TOOL_NAME
	}; 
	
	/**
	 * Gets an instance of either {@link NativeMonitorExperiment} or
	 * {@link ForeignMonitorExperiment} depending on the name of the tool.
	 * @param tool_name The name of the tool
	 * @return The experiment
	 */
	public MonitorExperiment<T> getExperiment(String tool_name)
	{
		if (tool_name == null || tool_name.isEmpty())
		{
			return null;
		}
		for (String tool : NATIVE_MONITORS)
		{
			if (tool_name.compareTo(tool) == 0)
			{
				return new NativeMonitorExperiment<T>();
			}
		}
		return new ForeignMonitorExperiment<T>();
	}
}
