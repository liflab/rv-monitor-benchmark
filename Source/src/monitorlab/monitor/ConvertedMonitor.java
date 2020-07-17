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

import ca.uqac.lif.cep.ltl.Troolean.Value;
import monitorlab.source.EventConverter;
import monitorlab.source.SourceException;

/**
 * Converts a monitor accepting events of type <tt>T</tt> into
 * a monitor accepting events of type <tt>U</tt>.
 *
 * @param <T> The original input type of the monitor
 * @param <U> The input type of the converted monitor
 */
public class ConvertedMonitor<T,U> implements NativeMonitor<U>
{
	/**
	 * The monitor to call
	 */
	protected NativeMonitor<T> m_monitor;
	
	/**
	 * The converter used to convert events to the input format of the monitor
	 */
	protected EventConverter<U,T> m_converter;
	
	/**
	 * Creates a new converted monitor
	 * @param monitor The monitor to convert
	 * @param converter The converter used to convert envents to the input format
	 * of the monitor
	 */
	public ConvertedMonitor(NativeMonitor<T> monitor, EventConverter<U,T> converter)
	{
		super();
		m_monitor = monitor;
		m_converter = converter;
	}
	
	@Override
	public void feed(U event) throws MonitorException
	{
		try
		{
			m_monitor.feed(m_converter.convert(event));
		}
		catch (SourceException e)
		{
			throw new MonitorException(e);
		}
	}

	@Override
	public Value getVerdict() throws MonitorException
	{
		return m_monitor.getVerdict();
	}

	@Override
	public long getMemory() throws MonitorException
	{
		// Converted monitors are not penalized by being wrapped into another
		// object; the memory measured is that of the original monitor
		return m_monitor.getMemory();
	}
}