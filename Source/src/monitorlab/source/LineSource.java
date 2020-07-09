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

import java.util.Scanner;

/**
 * A source that asks a scanner for text lines, and produces
 * an event for each line read.
 * @param <T> The type of the output events
 */
public abstract class LineSource<T> implements PullSource<T>
{
	/**
	 * A scanner reading lines from a file
	 */
	protected transient Scanner m_scanner;
	
	/**
	 * The number of lines in the source
	 */
	protected int m_lineCount;
	
	/**
	 * The converter that turns lines into events
	 */
	protected LineConverter<T> m_converter;
	
	/**
	 * Creates a new line source.
	 * @param scanner A scanner reading lines from a file
	 * @param converter The converter that turns lines into events
	 * @param line_count The number of lines in the source
	 */
	public LineSource(Scanner scanner, LineConverter<T> converter, int line_count)
	{
		super();
		m_scanner = scanner;
		m_converter = converter;
		m_lineCount = line_count;
	}
	
	/**
	 * Creates a new line source.
	 * @param scanner A scanner reading lines from a file
	 * @param converter The converter that turns lines into events
	 */
	public LineSource(Scanner scanner, LineConverter<T> converter)
	{
		super();
		m_scanner = scanner;
		m_lineCount = -1;
	}

	@Override
	public T pull() throws SourceException
	{
		if (!m_scanner.hasNextLine())
		{
			throw new SourceException("No more lines in the source");
		}
		return m_converter.convert(m_scanner.nextLine());
	}

	@Override
	public boolean hasNext() throws SourceException
	{
		return m_scanner.hasNextLine();
	}

	@Override
	public int getLength()
	{
		return m_lineCount;
	}
	
	@Override
	public void close() throws SourceException
	{
		m_scanner.close();
	}
}
