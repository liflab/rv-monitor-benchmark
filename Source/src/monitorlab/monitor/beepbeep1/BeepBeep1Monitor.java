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
package monitorlab.monitor.beepbeep1;

import ca.uqac.info.monitor.Event;
import ca.uqac.info.monitor.Monitor.Verdict;
import ca.uqac.lif.azrael.PrintException;
import ca.uqac.lif.azrael.size.SizePrinter;
import ca.uqac.lif.cep.ltl.Troolean;
import ca.uqac.lif.cep.ltl.Troolean.Value;
import monitorlab.monitor.MonitorException;
import monitorlab.monitor.NativeMonitor;

/**
 * A monitor using <a href="https://beepbeep.sourceforge.net/">BeepBeep 1</a>
 * to evaluate LTL-FO+ formulas.
 */
public class BeepBeep1Monitor implements NativeMonitor<Event>
{
	/**
	 * The name of the underlying tool for this monitor
	 */
	public static final transient String TOOL_NAME = "BeepBeep 1";
	
	/**
	 * The monitor corresponding to the formula to evaluate
	 */
	protected ca.uqac.info.monitor.Monitor m_monitor;
	
	/**
   * An Azrael printer used to evaluate memory usage
   */
  protected transient SizePrinter m_sizePrinter;
  
  /**
   * Creates a new BeepBeep monitor
   * @param m The monitor to be evaluated
   */
  public BeepBeep1Monitor(ca.uqac.info.monitor.Monitor m)
  {
  	super();
  	m_monitor = m;
  	m_sizePrinter = new SizePrinter();
  }

	@Override
	public void feed(Event event) throws MonitorException
	{
		try
		{
			m_monitor.processEvent(event);
		}
		catch (ca.uqac.info.monitor.MonitorException e)
		{
			throw new MonitorException(e);
		}
	}

	@Override
	public Value getVerdict() throws MonitorException
	{
		Verdict v = m_monitor.getVerdict();
		if (v == Verdict.FALSE)
		{
			return Troolean.Value.FALSE;
		}
		if (v == Verdict.TRUE)
		{
			return Troolean.Value.TRUE;
		}
		return Troolean.Value.INCONCLUSIVE;
	}

	@Override
	public long getMemory() throws MonitorException
	{
		m_sizePrinter.reset();
		try
		{
			return m_sizePrinter.print(m_monitor).longValue();
		}
		catch (PrintException e)
		{
			throw new MonitorException(e);
		}
	}
}
