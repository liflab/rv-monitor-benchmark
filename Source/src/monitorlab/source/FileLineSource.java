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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * A {@link LineSource} that opens a scanner on an external file specified
 * by a path.
 * @param <T> The type of the output events
 */
public class FileLineSource<T> extends LineSource<T>
{
	/**
	 * The path to read the lines from
	 */
	protected String m_path;

	/**
	 * Creates a new internal line source.
	 * @param path The path to read the lines from
	 * @param converter The converter that turns lines into events
	 */
	public FileLineSource(String path, LineConverter<T> converter)
	{
		super(null, converter);
		m_path = path;
	}

	/**
	 * Creates a new file line source.
	 * @param path The path to read the lines from
	 * @param converter The converter that turns lines into events
	 * @param line_count The number of lines in the source
	 */
	public FileLineSource(String path, LineConverter<T> converter, int num_lines)
	{
		super(null, converter, num_lines);
		m_path = path;
	}

	@Override
	public void open() throws SourceException
	{
		if (m_lineCount <= 0)
		{
			try
			{
				m_scanner = new Scanner(new FileInputStream(new File(m_path)));
			}
			catch (FileNotFoundException e)
			{
				throw new SourceException(e);
			}
			int line_count = 0;
			while (m_scanner.hasNextLine())
			{
				m_scanner.nextLine();
				line_count++;
			}
			m_lineCount = line_count;
			m_scanner.close();
		}
		try
		{
			m_scanner = new Scanner(new FileInputStream(new File(m_path)));
		}
		catch (FileNotFoundException e)
		{
			throw new SourceException(e);
		}
	}

	@Override
	public void close() throws SourceException
	{
		m_scanner.close();
	}

	@Override
	public void printEvent(T event, PrintStream ps)
	{
		ps.println(event);
	}

	@Override
	public String getFilename()
	{
		return m_path;
	}
}
