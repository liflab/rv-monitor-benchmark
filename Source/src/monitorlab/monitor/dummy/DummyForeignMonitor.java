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

import ca.uqac.lif.cep.ltl.Troolean;
import ca.uqac.lif.cep.ltl.Troolean.Value;
import ca.uqac.lif.labpal.CommandRunner;
import monitorlab.monitor.MonitorException;
import monitorlab.monitor.foreign.CommandLineMonitor;

/**
 * A "dummy" monitor that does not process events and always returns the
 * <tt>TRUE</tt> verdict. It is used just so that there are multiple monitors
 * in the plots and tables.
 * @author Sylvain Hallé
 *
 */
public class DummyForeignMonitor extends CommandLineMonitor
{
	/**
	 * The name of the underlying tool for this monitor
	 */
	public static final transient String TOOL_NAME = "Dummy (foreign)";
	
	/**
	 * The output produced by the command
	 */
	protected String m_output = "";
	
	/**
	 * Creates a new instance of the dummy monitor
	 */
	public DummyForeignMonitor()
	{
		super();
		m_externalPath += "dummy" + SLASH;
	}

	@Override
	public void run() throws MonitorException
	{
		String trace_filename = m_externalPath + m_source.getFilename();
		String[] arguments = {"wc", trace_filename};
		CommandRunner runner = new CommandRunner(arguments);
		runner.run();
		m_output = new String(runner.getBytes());
		int exit_code = runner.getErrorCode();
		if (exit_code != 0)
		{
			throw new MonitorException("Got return code " + exit_code);
		}
	}

	@Override
	public Value getVerdict() throws MonitorException
	{
		if (m_output.isEmpty())
		{
			throw new MonitorException("No output value");
		}
		return Troolean.Value.TRUE;
	}

	@Override
	public long getMemory() throws MonitorException
	{
		// Produce a dummy memory value
		return (long) (Math.random() * 10000);
	}
}
