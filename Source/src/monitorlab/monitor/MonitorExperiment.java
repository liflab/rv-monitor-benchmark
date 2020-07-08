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

import ca.uqac.lif.json.JsonList;
import ca.uqac.lif.json.JsonNumber;
import ca.uqac.lif.labpal.Experiment;
import ca.uqac.lif.labpal.ExperimentException;
import monitorlab.monitor.Monitor;
import monitorlab.monitor.MonitorException;
import monitorlab.source.PullSource;
import monitorlab.source.SourceException;

public class MonitorExperiment<T,U> extends Experiment
{
	public static transient final String TOOL = "Tool";
	public static transient final String TIME = "Time";
	public static transient final String LENGTH = "Length";
	public static transient final String TRACE_LENGTH = "Trace length";
	public static transient final String PROPERTY = "Query";
	public static transient final String MEMORY = "Memory";
	public static transient final String MEM_PER_EVENT = "Memory per event";
	public static transient final String THROUGHPUT = "Throughput";
	public static transient final String MAX_MEMORY = "Max memory";

	/**
	 * The monitor that is being used in this experiment
	 */
	protected transient Monitor<T,U> m_monitor;

	/**
	 * The source from which the input events will originate
	 */
	protected transient PullSource<T> m_source;

	/**
	 * The interval at which the experiment updates its data on
	 * runtime and throughput
	 */
	protected int m_eventStep = 1000;

	/**
	 * The predicted throughput for this experiment. This is only used to give an
	 * estimate of the time remaining before the experiment is done.
	 */
	protected float m_predictedThroughput = 0;

	/**
	 * The description of the property being monitored
	 */
	protected transient String m_propertyDescription = null;

	/**
	 * Creates a new empty stream experiment
	 */
	public MonitorExperiment()
	{
		super();
		describe(THROUGHPUT, "The average number of events processed per second");
		describe(TIME, "Cumulative running time (in ms)");
		describe(LENGTH, "Number of events processed");
		describe(TRACE_LENGTH, "The total length of the trace processed");
		describe(PROPERTY, "The name of the query being evaluated on the event log");
		describe(MAX_MEMORY, "The maximum amount of memory consumed during the evaluation of the property (in bytes)");
		JsonList x = new JsonList();
		x.add(0);
		write(LENGTH, x);
		JsonList y = new JsonList();
		y.add(0);
		write(TIME, y);
		JsonList z = new JsonList();
		z.add(0);
		write(MEMORY, z);
	}

	@Override
	public void execute() throws ExperimentException, InterruptedException 
	{
		long start = System.currentTimeMillis();
		int event_count = 0;
		long max_mem = 0;
		try
		{
			while (m_source.hasNext())
			{

				T event = m_source.pull();
				m_monitor.feed(event);
				if (event_count % m_eventStep == 0 && event_count > 0)
				{
					long mem = m_monitor.getMemory();
					max_mem = Math.max(max_mem, mem);
					addReading(event_count, System.currentTimeMillis() - start, mem);
				}
			}
			long end = System.currentTimeMillis();
	    write(THROUGHPUT, (1000f * (float) event_count) / ((float) (end - start)));
	    write(MAX_MEMORY, max_mem);
	    write(MEM_PER_EVENT, max_mem / event_count);
	    write(TRACE_LENGTH, event_count);
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

	/**
	 * Sets the monitor that is being used in this experiment
	 * @param m The monitor
	 */
	public void setMonitor(Monitor<T,U> m)
	{
		m_monitor = m;
	}

	/**
	 * Sets the source from which the input events will originate
	 * @param s The source
	 */
	public void setSource(PullSource<T> s)
	{
		m_source = s;
	}

	/**
	 * Sets the interval at which the experiment updates its data on
	 * runtime and throughput
	 * @param step The interval
	 */
	public void setEventStep(int step)
	{
		m_eventStep = step;
	}

	/**
	 * Sets the textual description of the property being evaluated
	 * @param description The description
	 */
	public void setPropertyDescription(String description)
	{
		m_propertyDescription = description;
	}

	@Override
	public int countDataPoints()
	{
		int points = 3; // Throughput, max memory and mem per event
		points += ((JsonList) read(TIME)).size();
		points += ((JsonList) read(MEMORY)).size();
		return points;
	}

	/**
	 * Adds a new reading to the set of data points collected by the experiment
	 * @param length
	 * @param time
	 * @param memory
	 */
	public void addReading(int length, long time, long memory)
	{
		JsonList l_len = (JsonList) read(LENGTH);
		JsonList l_time = (JsonList) read(TIME);
		JsonList l_mem = (JsonList) read(MEMORY);
		l_len.add(new JsonNumber(length));
		l_time.add(new JsonNumber(time));
		l_mem.add(new JsonNumber(memory));
		write(LENGTH, l_len);
		write(TIME, l_time);
		write(MEMORY, l_mem);
	}
}
