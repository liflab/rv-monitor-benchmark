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

import java.util.HashMap;
import java.util.Map;

import ca.uqac.lif.labpal.ExperimentFactory;
import ca.uqac.lif.labpal.Region;
import monitorlab.monitor.MonitorExperiment;

import static monitorlab.Scenario.SCENARIO;

/**
 * An {@link ExperimentFactory} that produces {@link StreamExperiment}s.
 */
@SuppressWarnings("rawtypes")
public class MonitorExperimentFactory extends ExperimentFactory<MonitorLab,MonitorExperiment>
{
	/**
	 * A map associating each scenario to its name
	 */
	protected Map<String,Scenario> m_scenarios;

	/**
	 * Creates a new instance of the factory
	 * @param lab The lab this factory is associated to. New instances of
	 * experiments will be added to this lab.
	 */
	public MonitorExperimentFactory(MonitorLab lab)
	{
		super(lab, MonitorExperiment.class);
		m_scenarios = new HashMap<String,Scenario>();
	}

	public MonitorExperimentFactory addScenario(String name, Scenario s)
	{
		m_scenarios.put(name, s);
		return this;
	}

	@Override
	protected MonitorExperiment createExperiment(Region r)
	{
		String scenario_s = r.getString(SCENARIO);
		if (!m_scenarios.containsKey(scenario_s))
		{
			return null;
		}
		Scenario scenario = m_scenarios.get(scenario_s);
		if (scenario == null)
		{
			return null;
		}
		MonitorExperiment exp = scenario.setup(r);
		return exp;
	}
}
