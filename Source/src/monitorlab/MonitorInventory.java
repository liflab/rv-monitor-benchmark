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
import monitorlab.monitor.dummy.DummyForeignMonitor;
import monitorlab.monitor.monpoly.MonPolyMonitor;

public class MonitorInventory<T>
{
	/**
	 * The names of the tools that are foreign monitors
	 */
	protected static transient String[] FOREIGN_MONITORS = new String[] {
			DummyForeignMonitor.TOOL_NAME, MonPolyMonitor.TOOL_NAME
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
		for (String tool : FOREIGN_MONITORS)
		{
			if (tool_name.compareTo(tool) == 0)
			{
				return new ForeignMonitorExperiment<T>();
			}
		}
		return new NativeMonitorExperiment<T>();
	}
}
