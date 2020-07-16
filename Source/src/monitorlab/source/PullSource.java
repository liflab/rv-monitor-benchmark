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
package monitorlab.source;

import java.io.PrintStream;

public interface PullSource<T>
{
	/**
	 * Pulls the next event from the source.
	 * @return The next event
	 * @throws SourceException Thrown if the operation caused an error
	 */
	public T pull() throws SourceException;

	/**
	 * Determines if the source has another event to produce.
	 * @return <tt>true</tt> if another event can be extracted by a call to
	 * {@link #pull()}, <tt>false</tt> otherwise
	 * @throws SourceException Thrown if the operation caused an error
	 */
	public boolean hasNext() throws SourceException;
	
	/**
	 * Gets the total number of events produced by this source before a call to
	 * {@link #hasNext()} returns <tt>false</tt>.
	 * @return The number of events, or <tt>-1</tt> if the bound is unknown
	 * @throws SourceException Thrown if the operation caused an error
	 */
	public int getLength() throws SourceException;
	
	/**
	 * Opens and initializes the source.
	 * @throws SourceException Thrown if the operation caused an error
	 */
	public void open() throws SourceException;
	
	/**
	 * Closes the source.
	 * @throws SourceException Thrown if the operation caused an error
	 */
	public void close() throws SourceException;

	/**
	 * Prints an event to a print stream.
	 * @param event The event
	 * @param ps The print stream to print the event to
	 */
	public void printEvent(T event, PrintStream ps);
	
	/**
   * Gets the name of the file corresponding to this source.
   * @return The filename
   */
  public String getFilename();
}