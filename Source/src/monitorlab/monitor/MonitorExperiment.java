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
import monitorlab.source.PullSource;

/**
 * An experiment that measures various parameters on the execution of a
 * specific monitor on a scenario.
 * @author Sylvain Hallé
 *
 * @param <T> The type of the events accepted by the monitor
 */
public abstract class MonitorExperiment<T> extends Experiment
{
	/**
	 * Name of the parameter "Tool"
	 */
	public static transient final String TOOL = "Tool";

	/**
	 * Name of the parameter "Time"
	 */
	public static transient final String TIME = "Time";
	
	/**
	 * Name of the parameter "Total time"
	 */
	public static transient final String TOTAL_TIME = "Total time";

	/**
	 * Name of the parameter "Length"
	 */
	public static transient final String LENGTH = "Length";

	/**
	 * Name of the parameter "Trace length"
	 */
	public static transient final String TRACE_LENGTH = "Trace length";

	/**
	 * Name of the parameter "Query"
	 */
	public static transient final String PROPERTY = "Query";

	/**
	 * Name of the parameter "Memory"
	 */
	public static transient final String MEMORY = "Memory";

	/**
	 * Name of the parameter "Memory per event"
	 */
	public static transient final String MEM_PER_EVENT = "Memory per event";

	/**
	 * Name of the parameter "Throughput"
	 */
	public static transient final String THROUGHPUT = "Throughput";

	/**
	 * Name of the parameter "Max memory"
	 */
	public static transient final String MAX_MEMORY = "Max memory";

	/**
	 * Name of the parameter "Verdict"
	 */
	public static transient final String VERDICT = "Verdict";

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
		describe(TOTAL_TIME, "Global running time (in ms)");
		describe(LENGTH, "Number of events processed");
		describe(TRACE_LENGTH, "The total length of the trace processed");
		describe(PROPERTY, "The name of the query being evaluated on the event log");
		describe(MAX_MEMORY, "The maximum amount of memory consumed during the evaluation of the property (in bytes)");
		describe(VERDICT, "The verdict produced by the monitor on this trace");
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

	/**
	 * Sets the native monitor that is being used in this experiment.
	 * @param m The monitor
	 * @throws ExperimentException If this type of monitor is not supported by
	 * the experiment
	 */
	public abstract void setMonitor(Monitor<T> m) throws ExperimentException;
}
