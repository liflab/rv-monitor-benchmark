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
package monitorlab.source.converter;

import monitorlab.source.LineConverter;

/**
 * Converts a line of text into itself.
 */
public class Identity implements LineConverter<String>
{
	/**
	 * The prefix to add to the output filename to create the filename of the
	 * converted trace
	 */
	protected String m_prefix;
	
	/**
	 * Creates a new instance of the converter.
	 * @param prefix The prefix to add to the output filename to create the filename of the
	 * converted trace
	 */
	public Identity(String prefix)
	{
		super();
		m_prefix = prefix;
	}
	
	@Override
	public String convert(String line)
	{
		return line;
	}

	@Override
	public String getFilename(String input_filename)
	{
		return input_filename;
	}
}
