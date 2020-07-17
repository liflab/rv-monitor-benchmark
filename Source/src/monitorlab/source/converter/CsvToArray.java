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
 * Converts a line of comma-separated values (CSV) into an array of strings.
 */
public class CsvToArray implements LineConverter<Object[]>
{	
	/**
	 * The prefix to add to the output filename to create the filename of the
	 * converted trace
	 */
	protected String m_prefix;
	
	/**
	 * Creates a new instance of the converter
	 * @param prefix The prefix to add to the output filename to create
	 * the filename of the converted trace
	 */
	protected CsvToArray(String prefix)
	{
		super();
		m_prefix = prefix;
	}
	
	@Override
	public Object[] convert(String line)
	{
		String[] parts = line.split(",");
		Object[] array = new Object[parts.length];
		for (int i = 0; i < parts.length; i++)
		{
			array[i] = parts[i];
		}
		return array;
	}
	
	@Override
	public String getFilename(String input_filename)
	{
		return m_prefix + "-" + input_filename;
	}
	
	@Override
	public String getExtension()
	{
		return "csv";
	}
}
