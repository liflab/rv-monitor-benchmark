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

/**
 * Interface allowing events from different inputs to be converted
 *
 * @param <T> The input type
 * @param <U> The output type
 */
public interface EventConverter<T,U>
{
	/**
	 * Converts an event into another event
	 * @param e The input event
	 * @return The output event
	 * @throws SourceException If the conversion cannot proceed
	 */
	public U convert(T e) throws SourceException;
	
	/**
	 * Gets the filename of the trace resulting from the conversion 
	 * @param input_filename The original filename
	 * @return The new filename
	 */
	public String getFilename(String input_filename);
	
	/**
	 * Gets the extension typically given to files containing events of
	 * type <tt>U</tt>.
	 * @return The extension
	 */
	public String getExtension();

}
