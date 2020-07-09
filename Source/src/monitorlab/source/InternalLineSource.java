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

import java.io.InputStream;
import java.util.Scanner;

import monitorlab.MonitorLab;

/**
 * A {@link LineSource} that opens a scanner on an internal input stream.
 * @param <T> The type of the output events
 */
public class InternalLineSource<T> extends LineSource<T>
{
	/**
	 * The path to read the lines from
	 */
	protected String m_path;
	
	/**
	 * Creates a new internal line source.
	 * @param converter The converter that turns lines into events
	 * @param path The path to read the lines from
	 */
	public InternalLineSource(String path, LineConverter<T> converter)
	{
		super(null, converter);
		m_path = path;
		m_converter = converter;
	}
	
	/**
	 * Creates a new internal line source.
	 * @param path The path to read the lines from
	 * @param converter The converter that turns lines into events
	 * @param line_count The number of lines in the source
	 */
	public InternalLineSource(String path, LineConverter<T> converter, int num_lines)
	{
		super(null, converter, num_lines);
		m_path = path;
	}
	
	@Override
	public void open() throws SourceException
	{
		InputStream is = MonitorLab.class.getResourceAsStream(m_path);
		if (m_lineCount <= 0)
		{
			m_scanner = new Scanner(is);
			int line_count = 0;
			while (m_scanner.hasNextLine())
			{
				m_scanner.nextLine();
				line_count++;
			}
			m_lineCount = line_count;
			m_scanner.close();
		}
		is = MonitorLab.class.getResourceAsStream(m_path);
		m_scanner = new Scanner(is);
	}
	
	@Override
	public void close() throws SourceException
	{
		m_scanner.close();
	}
}
