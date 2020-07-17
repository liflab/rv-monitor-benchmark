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
package monitorlab.monitor.foreign;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import ca.uqac.lif.labpal.FileHelper;
import monitorlab.monitor.ForeignMonitor;
import monitorlab.monitor.MonitorException;
import monitorlab.source.PullSource;
import monitorlab.source.SourceException;

/**
 * A foreign monitor that runs a script on the command line. Such a monitor
 * must be given a 
 * @author Sylvain Hallé
 *
 */
public abstract class CommandLineMonitor implements ForeignMonitor<String>
{
	/**
	 * The OS-dependent path separator
	 */
	public static final transient String SLASH = File.separator;
	
	/**
	 * The source of events that will be used by this monitor instance
	 */
	protected transient PullSource<String> m_source;
	
	/**
	 * The path on the disk where files for the trace will be copied
	 */
	protected transient String m_externalPath = "." + SLASH;
	
	/**
	 * Creates a new instance of command line monitor.
	 */
	public CommandLineMonitor()
	{
		super();
	}
	
	/**
	 * Sets the source of events that will be used by this monitor instance
	 * @param source The source
	 */
	public void setSource(PullSource<String> source)
	{
		m_source = source;
	}
	
	/**
	 * Sets the path on the disk where files for the trace will be copied
	 * @param path The path
	 */
	public void setExternalPath(String path)
	{
		m_externalPath = path;
		if (!m_externalPath.endsWith(SLASH))
		{
			m_externalPath += SLASH;
		}
	}
	
	@Override
	public boolean isReady() throws MonitorException
	{
		if (m_source == null)
		{
			throw new MonitorException("No source specified");
		}
		String trace_filename = m_externalPath + m_source.getFilename();
		return FileHelper.fileExists(trace_filename);
	}
	
	@Override
	public void prepare() throws MonitorException
	{
		if (m_source == null)
		{
			throw new MonitorException("No source specified");
		}
		// Create path if it does not exist
		File f = new File(m_externalPath);
		f.mkdirs();
		// Write trace to file
		String trace_filename = m_externalPath + m_source.getFilename();
		if (!FileHelper.fileExists(trace_filename))
		{
			writeSource(m_source, trace_filename);
		}
	}
	
	@Override
	public void cleanup() throws MonitorException
	{
		if (m_source != null)
		{
			String trace_filename = m_externalPath + m_source.getFilename();
			if (FileHelper.fileExists(trace_filename))
			{
				FileHelper.deleteFile(trace_filename);
			}	
		}
	}
	
	/**
	 * Writes the contents of a pull source to an external file
	 * @param source The source of events
	 * @param trace_filename The name of the file to write to
	 * @throws MonitorException If the operation could not be completed
	 */
	protected static void writeSource(PullSource<String> source, String trace_filename) throws MonitorException
	{
		try
		{
			PrintStream out = new PrintStream(new FileOutputStream(new File(trace_filename)));
			source.open();
			while (source.hasNext())
			{
				String e = source.pull();
				source.printEvent(e, out);
			}
			source.close();
			out.close();
		}
		catch (FileNotFoundException e)
		{
			throw new MonitorException(e);
		}
		catch (SourceException e)
		{
			throw new MonitorException(e);
		}
	}
}
