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
package monitorlab.monitor;

import ca.uqac.lif.cep.ltl.Troolean;

/**
 * Provides a uniform way of interacting with various monitors.
 * It is up to each concrete monitoring tool to implement a wrapper
 * that supports the methods defined in this interface.
 *
 * @param <T> The type of events consumed by the monitor
 */
public interface Monitor<T> 
{
	/**
	 * Gives a new event to be processed by the monitor.
	 * @param event The event
	 * @throws MonitorException If the operation was unsuccessful
	 */
	public void feed(T event) throws MonitorException;
	
	/**
	 * Gets the current verdict computed by the monitor on the stream
	 * of events received so far.  
	 * @return The verdict
	 * @throws MonitorException If the operation was unsuccessful
	 */
	public Troolean.Value getVerdict() throws MonitorException;
	
	/**
	 * Gets the memory consumed by the monitor in its current state.
	 * @return The memory used by the monitor (in bytes), or <tt>-1</tt> if
	 * this value cannot be calculated
	 * @throws MonitorException If the operation was unsuccessful
	 */
	public long getMemory() throws MonitorException;
}
