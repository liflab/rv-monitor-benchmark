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
 * A monitor experiment where the monitor is native Java object that
 * runs within the lab.
 * @author Sylvain Hallé
 *
 * @param <T> The type of the events accepted by the monitor
 */
public class NativeMonitorExperiment<T> extends MonitorExperiment<T>
{
	/**
	 * The monitor that is being used in this experiment
	 */
	protected transient NativeMonitor<T> m_monitor;
	
	/**
	 * Creates a new instance of native monitor experiment.
	 */
	public NativeMonitorExperiment()
	{
		super();
	}
	
	@Override
	public void setMonitor(Monitor<T> m) throws ExperimentException
	{
		if (!(m instanceof NativeMonitor))
		{
			throw new ExperimentException("This type of monitor is not supported by the experiment");
		}
		m_monitor = (NativeMonitor<T>) m;
	}
	
	@Override
	public void execute() throws ExperimentException, InterruptedException 
	{
		long start = System.currentTimeMillis();
		int event_count = 0;
		long max_mem = 0;
		try
		{
			int source_length = m_source.getLength();
			m_source.open();
			while (m_source.hasNext())
			{
				event_count++;
				T event = m_source.pull();
				m_monitor.feed(event);
				if (event_count % m_eventStep == 0 && event_count > 0)
				{
					long mem = m_monitor.getMemory();
					max_mem = Math.max(max_mem, mem);
					addReading(event_count, System.currentTimeMillis() - start, mem);
					if (source_length > 0)
					{
						float prog = ((float) event_count) / ((float) source_length);
						setProgression(prog);
					}
				}
			}
			long end = System.currentTimeMillis();
			write(THROUGHPUT, (1000f * (float) event_count) / ((float) (end - start)));
			write(MAX_MEMORY, max_mem);
			if (event_count > 0)
			{
				write(MEM_PER_EVENT, max_mem / event_count);
			}
			write(TRACE_LENGTH, event_count);
			write(VERDICT, m_monitor.getVerdict().toString());
			m_source.close();
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
}
