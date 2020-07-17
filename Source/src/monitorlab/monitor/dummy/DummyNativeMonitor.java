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
package monitorlab.monitor.dummy;

import ca.uqac.lif.cep.ltl.Troolean.Value;
import monitorlab.monitor.MonitorException;
import monitorlab.monitor.NativeMonitor;

/**
 * A "dummy" monitor that does not process events and always returns the
 * <tt>TRUE</tt> verdict. It is used just so that there are multiple monitors
 * in the plots and tables.
 * @author Sylvain Hallé
 *
 * @param <T> The type of the input events
 */
public class DummyNativeMonitor<T> implements NativeMonitor<T>
{
	/**
	 * The name of the underlying tool for this monitor
	 */
	public static final transient String TOOL_NAME = "Dummy (native)";

	/**
	 * Creates a new instance of dummy monitor
	 */
	public DummyNativeMonitor()
	{
		super();
	}

	@Override
	public void feed(T event) throws MonitorException
	{
		// Wait a few milliseconds to look like we're doing something
		if (Math.random() < 0.25)
		{
			try
			{
				Thread.sleep(1);
			}
			catch (InterruptedException e)
			{
				// Don't care
			}
		}
	}

	@Override
	public Value getVerdict() throws MonitorException
	{
		return Value.TRUE;
	}

	@Override
	public long getMemory() throws MonitorException
	{
		// Produce a dummy memory value
		return (long) (Math.random() * 10000);
	}
}
