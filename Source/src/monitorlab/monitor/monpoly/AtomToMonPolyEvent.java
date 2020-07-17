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
package monitorlab.monitor.monpoly;

import monitorlab.source.ConvertedSource;
import monitorlab.source.LineConverter;
import monitorlab.source.SourceException;

/**
 * Converts events expressed as atomic symbols into timestamped predicates
 * suitable for the MonPoly monitor.
 * @author Sylvain Hallé
 *
 */
public class AtomToMonPolyEvent implements LineConverter<String>
{
	/**
	 * A counter to increment timestamps for each event
	 */
	protected int m_timestamp;
	
	/**
	 * The list of all atomic symbols that can occur as events
	 */
	protected String[] m_predicates;
	
	/**
	 * Creates a new conversion function.
	 * @param predicates The list of all atomic symbols that can occur as events
	 */
	public AtomToMonPolyEvent(String ... predicates)
	{
		super();
		m_predicates = predicates;
		m_timestamp = 0;
	}
	
	@Override
	public String convert(String e) throws SourceException
	{
		m_timestamp++;
		StringBuilder out = new StringBuilder();
		out.append("@").append(m_timestamp).append("\n");
		for (String pred : m_predicates)
		{
			if (pred.compareTo(e) == 0)
			{
				out.append(e).append("()\n");
			}
		}
		return out.toString();
	}

	@Override
	public String getFilename(String input_filename)
	{
		return ConvertedSource.replaceExtension("mfotl-" + input_filename, getExtension());
	}
	
	@Override
	public String getExtension()
	{
		return "log";
	}
}
