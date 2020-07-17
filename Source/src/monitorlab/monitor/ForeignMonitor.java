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

/**
 * A monitor that is a non-native (i.e. "foreign") object. Foreign monitors
 * are typically external programs that are called on the command line, but
 * other types of monitors could be handled by this interface. Contrary to
 * a {@link NativeMonitor}, a foreign monitor cannot be fed events one by
 * one. The way it receives events is left unspecified; all that is assumed is
 * that it can be {@link #run()} and produce a verdict.
 * @author Sylvain Hallé
 *
 * @param <T> The type of the events accepted by the monitor
 */
public interface ForeignMonitor<T> extends Monitor<T>
{
	/**
	 * Determines if the monitor is ready to run. This is typically used to
	 * verify the presence of files on the disk that are required for the
	 * monitor to be executed.
	 * @return <tt>true</tt> if the monitor is ready to run, <tt>false</tt>
	 * otherwise
	 * @throws MonitorException If the operation could not be performed
	 */
	public boolean isReady() throws MonitorException;
	
	/**
	 * Prepares the monitor to be executed. This method should be called if
	 * {@link #isReady()} returns <tt>false</tt>. This is the place where a
	 * monitor can copy files at some locations on the disk before being executed.
	 * @throws MonitorException If the operation could not be performed
	 */
	public void prepare() throws MonitorException;
	
	/**
	 * Cleans up the side effects resulting from the monitor's execution.
	 * This typically amounts to deleting temporary files.
	 * @throws MonitorException  If the operation could not be performed
	 */
	public void cleanup() throws MonitorException;
	
	/**
	 * Runs the monitor. Once this method returns, the monitor is considered to
	 * be terminated and should be able to produce a verdict (using 
	 * {@link #getVerdict()}).
	 * @throws MonitorException If the operation could not be performed
	 */
	public void run() throws MonitorException;
}
