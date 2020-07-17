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

import ca.uqac.lif.labpal.ExperimentException;
import monitorlab.source.SourceException;

/**
 * A monitor experiment where the monitor is a non-native (i.e. "foreign")
 * external program that is called at the command line and reads from
 * an external file.
 * @author Sylvain Hallé
 *
 * @param <T> The type of the events accepted by the monitor
 */
public class ForeignMonitorExperiment<T> extends MonitorExperiment<T>
{
	/**
	 * The monitor that is being used in this experiment
	 */
	protected transient ForeignMonitor<T> m_monitor;

	/**
	 * Creates a new instance of foreign monitor experiment.
	 */
	public ForeignMonitorExperiment()
	{
		super();
	}

	@Override
	public void setMonitor(Monitor<T> m) throws ExperimentException
	{
		if (!(m instanceof ForeignMonitor))
		{
			throw new ExperimentException("This type of monitor is not supported by the experiment");
		}
		m_monitor = (ForeignMonitor<T>) m;
	}

	@Override
	public boolean prerequisitesFulfilled()
	{
		try
		{
			if (!m_monitor.isReady())
			{
				return false;
			}
		}
		catch (MonitorException e)
		{
			return false;
		}
		return super.prerequisitesFulfilled();
	}

	@Override
	public void fulfillPrerequisites() throws ExperimentException
	{
		try
		{
			m_monitor.prepare();
		}
		catch (MonitorException e)
		{
			throw new ExperimentException(e);
		}
	}

	@Override
	public void execute() throws ExperimentException, InterruptedException
	{
		long max_mem = 0;
		long start = System.currentTimeMillis();
		try
		{
			int event_count = m_source.getLength();
			m_monitor.run();
			long end = System.currentTimeMillis();
			write(THROUGHPUT, (1000f * (float) event_count) / ((float) (end - start)));
			write(MAX_MEMORY, m_monitor.getMemory());
			if (event_count > 0)
			{
				write(MEM_PER_EVENT, max_mem / event_count);
			}
			write(TOTAL_TIME, end - start);
			write(TRACE_LENGTH, event_count);
			write(VERDICT, m_monitor.getVerdict().toString());
		}
		catch (MonitorException e)
		{
			throw new ExperimentException(e);
		}
		catch (SourceException e)
		{
			throw new ExperimentException(e);
		}
	}
	
	@Override
	public void cleanPrerequisites()
	{
		try
		{
			m_monitor.cleanup();
		}
		catch (MonitorException e)
		{
			e.printStackTrace();
		}
	}
}
