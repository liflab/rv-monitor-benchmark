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
package monitorlab.monitor.beepbeep3;

import ca.uqac.lif.azrael.PrintException;
import ca.uqac.lif.azrael.size.SizePrinter;
import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.cep.Pushable;
import ca.uqac.lif.cep.ltl.Troolean;
import ca.uqac.lif.cep.tmf.SinkLast;
import monitorlab.monitor.Monitor;
import monitorlab.monitor.MonitorException;

/**
 * A monitor using <a href="https://liflab.github.io/beepbeep-3">BeepBeep 3</a>
 * processor chains.
 *
 * @param <T> The type of the input events
 */
public class BeepBeep3Monitor<T> implements Monitor<T>
{
	/**
	 * The name of the underlying tool for this monitor
	 */
	public static final transient String TOOL_NAME = "BeepBeep 3";
	
	/**
	 * The BeepBeep processor to be used as the monitor
	 */
	protected Processor m_processor;
	
	/**
	 * A pushable to feed events to the monitor
	 */
	protected Pushable m_pushable;
	
	/**
	 * The sink where events will be pushed
	 */
	protected SinkLast m_sink;
	
  /**
   * An Azrael printer used to evaluate memory usage
   */
  protected transient SizePrinter m_sizePrinter;
  
  /**
   * Creates a new BeepBeep monitor
   * @param p The processor chain to be evaluated
   */
  public BeepBeep3Monitor(Processor p)
  {
  	super();
  	m_processor = p;
  	m_pushable = p.getPushableInput();
  	m_sink = new SinkLast();
  	Connector.connect(m_processor, m_sink);
  	m_sizePrinter = new SizePrinter();
  }

	@Override
	public void feed(T event) throws MonitorException
	{
		m_pushable.push(event);
	}

	@Override
	public Troolean.Value getVerdict() throws MonitorException
	{
		Object[] o = m_sink.getLast();
		if (o == null)
		{
			return null;
		}
		return (Troolean.Value) o[0];
	}

	@Override
	public long getMemory() throws MonitorException
	{
		m_sizePrinter.reset();
		try
		{
			return m_sizePrinter.print(m_processor).longValue();
		}
		catch (PrintException e)
		{
			throw new MonitorException(e);
		}
	}
}
