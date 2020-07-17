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

/**
 * A source that pulls events from another source and applies a converter to
 * each of them. 
 * @author Sylvain Hallé
 *
 * @param <T> The type of the original events
 * @param <U> The type of the converted events
 */
public class ConvertedSource<T,U> implements PullSource<U>
{
	/**
	 * A converter from events of a type to events of another type
	 */
	protected EventConverter<T,U> m_converter;
	
	/**
	 * The source to pull events from
	 */
	protected PullSource<T> m_source;
	
	/**
	 * A prefix to add to the filename of the underlying source
	 */
	protected String m_prefix;
	
	/**
	 * Creates a new converted source.
	 * @param converter A converter from events of a type to events of another type
	 * @param source The source to pull events from
	 * @param prefix A prefix to add to the filename of the underlying source
	 */
	public ConvertedSource(EventConverter<T,U> converter, PullSource<T> source, String prefix)
	{
		super();
		m_converter = converter;
		m_source = source;
		m_prefix = prefix;
	}
	
	@Override
	public U pull() throws SourceException
	{
		T e = m_source.pull();
		return m_converter.convert(e);
	}

	@Override
	public boolean hasNext() throws SourceException
	{
		return m_source.hasNext();
	}

	@Override
	public int getLength() throws SourceException
	{
		return m_source.getLength();
	}

	@Override
	public void open() throws SourceException
	{
		m_source.open();
	}

	@Override
	public void close() throws SourceException
	{
		m_source.close();
	}

	@Override
	public void printEvent(U event, PrintStream ps)
	{
		ps.println(event);
	}

	@Override
	public String getFilename()
	{
		return replaceExtension(m_prefix + m_source.getFilename(), m_converter.getExtension());
	}
	
	/**
	 * Replaces the extension of a filename by another one
	 * @param original_filename The original filename
	 * @param new_extension The new extension. If <tt>null</tt> is given, the
	 * original filename is returned as is. If an empty string is given, the
	 * extension of the original filename is removed and replaced by nothing. 
	 * @return The new filename with the replaced extension
	 */
	public static String replaceExtension(String original_filename, String new_extension)
	{
		if (new_extension == null)
		{
			return original_filename;
		}
		int i = original_filename.lastIndexOf(".");
		if (i < 0)
		{
			return original_filename + "." + new_extension;
		}
	  String name = original_filename.substring(0, i);
		if (new_extension.isEmpty())
		{
			return name;
		}
	  return name + "." + new_extension;
	}
}