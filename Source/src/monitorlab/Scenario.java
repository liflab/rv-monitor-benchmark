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
package monitorlab;

import ca.uqac.lif.labpal.Region;
import monitorlab.monitor.Monitor;
import monitorlab.monitor.MonitorExperiment;
import monitorlab.source.PullSource;

/**
 * A tuple made of a source and a property, each with
 * additional parameters.
 * @author Sylvain Hallé
 */
public abstract class Scenario<T>
{
	/**
	 * Name of the parameter "Scenario"
	 */
	public static final transient String SCENARIO = "Scenario";

	/**
	 * The name of the scenario
	 */
	protected String m_name;

	/**
	 * The name of the source
	 */
	protected String m_sourceName;

	/**
	 * The name of the property to monitor
	 */
	protected String m_propertyName;

	/**
	 * Creates a new scenario.
	 * @param name The name of the scenario
	 * @param source_name The name of the source
	 * @param property_name The name of the property to monitor
	 */
	public Scenario(String name, String source_name, String property_name)
	{
		super();
		m_name = name;
		m_sourceName = source_name;
		m_propertyName = property_name;
	}

	/**
	 * Makes the initial setup of an experiment based on the current
	 * scenario.
	 * @param e The experiment
	 * @param r A region describing the scenario
	 */
	public void setup(MonitorExperiment<T> e, Region r)
	{
		e.setInput(SCENARIO, m_name);
		getSource(e, r);
		getMonitor(e, r);
	}

	/**
	 * Gets the event source associated to this scenario
	 * @param e The experiment
	 * @param r A region describing the scenario
	 * @return The source
	 */
	public abstract PullSource<T> getSource(MonitorExperiment<T> e, Region r);

	/**
	 * Gets the monitor associated to this scenario
	 * @param e The experiment
	 * @param r A region describing the scenario
	 * @return The source
	 */
	public abstract Monitor<T> getMonitor(MonitorExperiment<T> e, Region r);

	@Override
	public int hashCode()
	{
		return m_name.hashCode() + m_propertyName.hashCode();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o)
	{
		if (o == null || !(o instanceof Scenario))
		{
			return false;
		}
		Scenario<T> s = (Scenario<T>) o;
		return m_name.compareTo(s.m_name) == 0 && m_sourceName.compareTo(s.m_sourceName) == 0 
				&& m_propertyName.compareTo(s.m_propertyName) == 0;
	}
}
