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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.uqac.lif.cep.ltl.Troolean;
import ca.uqac.lif.cep.ltl.Troolean.Value;
import ca.uqac.lif.labpal.CommandRunner;
import ca.uqac.lif.labpal.FileHelper;
import monitorlab.MonitorLab;
import monitorlab.monitor.MonitorException;
import monitorlab.monitor.foreign.CommandLineMonitor;

/**
 * Monitor that calls <a href="https://infsec.ethz.ch/research/software/monpoly.html">MonPoly</a>
 * on an external static file.
 * @author Sylvain Hallé
 *
 */
public abstract class MonPolyMonitor extends CommandLineMonitor
{
	/**
	 * The name of the underlying tool for this monitor
	 */
	public static final transient String TOOL_NAME = "MonPoly";

	/**
	 * The regex pattern used to extract memory consumption from the tool's
	 * output
	 */
	protected static Pattern s_memoryPattern = Pattern.compile("===STATS=== mem: (\\d+)");

	/**
	 * The output produced by the command
	 */
	protected String m_output = "";

	/**
	 * The name of the file containing the signature
	 */
	protected String m_signatureFilename = "";

	/**
	 * The name of the file containing the property to evaluate
	 */
	protected String m_propertyFilename = "";

	/**
	 * The verdict returned by MonPoly
	 */
	protected Troolean.Value m_verdict;

	/**
	 * The memory usage reported by MonPoly
	 */
	protected long m_memory;

	/**
	 * Creates a new instance of monitor.
	 * @param signature_filename The name of the file containing the signature
	 * @param property_filename The name of the file containing the property
	 * to evaluate
	 */
	public MonPolyMonitor(String signature_filename, String property_filename)
	{
		super();
		m_externalPath = MonitorLab.TRACE_PATH + SLASH + "monpoly" + SLASH;
		m_signatureFilename = m_externalPath + signature_filename;
		m_propertyFilename = m_externalPath + property_filename;
		m_verdict = null;
		m_memory = -1;
	}

	@Override
	public void run() throws MonitorException
	{
		String trace_filename = m_externalPath + m_source.getFilename();
		String[] arguments = {"monpoly", "-sig", m_signatureFilename, 
				"-formula", m_propertyFilename, "-log", trace_filename, "-stats"};
		CommandRunner runner = new CommandRunner(arguments);
		runner.run();
		m_output = new String(runner.getBytes());
		int exit_code = runner.getErrorCode();
		if (exit_code != 0)
		{
			throw new MonitorException("Got return code " + exit_code + "\n" + m_output + "\n");
		}
		parseOutput(m_output);
	}

	@Override
	public boolean isReady() throws MonitorException
	{
		if (!super.isReady())
		{
			return false;
		}
		return FileHelper.fileExists(m_signatureFilename) && FileHelper.fileExists(m_propertyFilename);
	}

	@Override
	public void prepare() throws MonitorException
	{
		super.prepare();
		// Write signature to file
		try
		{
			if (!FileHelper.fileExists(m_signatureFilename))
			{
				PrintStream ps = new PrintStream(new FileOutputStream(new File(m_signatureFilename)));
				writeSignature(ps);
				ps.close();
			}
			// Write property to file
			if (!FileHelper.fileExists(m_propertyFilename))
			{
				PrintStream ps = new PrintStream(new FileOutputStream(new File(m_propertyFilename)));
				writeProperty(ps);
				ps.close();
			}
		}
		catch (FileNotFoundException e)
		{
			throw new MonitorException(e);
		}
	}

	@Override
	public void cleanup() throws MonitorException
	{
		super.cleanup();
		if (FileHelper.fileExists(m_signatureFilename))
		{
			FileHelper.deleteFile(m_signatureFilename);
		}
		if (FileHelper.fileExists(m_propertyFilename))
		{
			FileHelper.deleteFile(m_propertyFilename);
		}
	}

	@Override
	public Value getVerdict() throws MonitorException
	{
		return m_verdict;
	}

	@Override
	public long getMemory() throws MonitorException
	{
		return m_memory;
	}

	/**
	 * Parses the tool's output
	 * @param output The tool's output
	 */
	protected void parseOutput(String output)
	{
		if (output.contains("false"))
		{
			m_verdict = Troolean.Value.FALSE;
		}
		else
		{
			m_verdict = Troolean.Value.TRUE;
		}
		Matcher mat = s_memoryPattern.matcher(output);
		if (mat.find())
		{
			String mem = mat.group(1);
			m_memory = Long.parseLong(mem);
		}
	}

	/**
	 * Checks the host system's environment to make sure it can run MonPoly.
	 * @return <tt>null</tt> if MonPoly can run, a string explaining the problem
	 * otherwise
	 */
	public static String checkEnvironment()
	{
		CommandRunner runner = new CommandRunner(new String[] {"monopoly", "--help"});
		runner.run();
		String s = runner.getString();
		if (s == null || s.contains("not found") || runner.getErrorCode() != 0)
		{
			return "MonPoly is not installed in the system's path. Experiments involving MonPoly will not work.";
		}
		return null;
	}

	public abstract void writeSignature(PrintStream ps);

	public abstract void writeProperty(PrintStream ps);
}
