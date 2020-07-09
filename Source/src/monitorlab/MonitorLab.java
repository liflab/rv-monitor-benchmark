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

import ca.uqac.lif.labpal.Laboratory;
import ca.uqac.lif.labpal.LatexNamer;
import ca.uqac.lif.labpal.Region;
import ca.uqac.lif.labpal.TitleNamer;
import ca.uqac.lif.labpal.table.ExperimentTable;
import ca.uqac.lif.mtnp.table.ExpandAsColumns;
import ca.uqac.lif.mtnp.table.TransformedTable;
import ca.uqac.lif.synthia.random.RandomBoolean;
import ca.uqac.lif.synthia.random.RandomFloat;
import monitorlab.monitor.MonitorExperiment;
import monitorlab.monitor.beepbeep3.BeepBeepMonitor;
import monitorlab.monitor.dummy.DummyMonitor;
import monitorlab.scenario.iterator.HasNextFile;
import monitorlab.scenario.iterator.HasNextRandom;

import static monitorlab.monitor.MonitorExperiment.MAX_MEMORY;
import static monitorlab.monitor.MonitorExperiment.THROUGHPUT;
import static monitorlab.monitor.MonitorExperiment.TOOL;
import static monitorlab.Scenario.SCENARIO;

/**
 * A laboratory comparing the performance of various Java runtime monitors on
 * different scenarios.
 * 
 * @author Sylvain Hallé
 */
public class MonitorLab extends Laboratory
{
	/**
	 * The interval, in number of events, between updates of the experiment's
	 * measurements
	 */
	public static int s_eventStep = 1000;

	/**
	 * A nicknamer
	 */
	public static transient LatexNamer s_nicknamer = new LatexNamer();

	/**
	 * A title namer
	 */
	public static transient TitleNamer s_titleNamer = new TitleNamer();

	/**
	 * An experiment factory
	 */
	public transient MonitorExperimentFactory m_factory = new MonitorExperimentFactory(this);

	@Override
	public void setup()
	{
		// Basic lab metadata
		setTitle("Benchmark for runtime monitors");
		setDoi("TODO");
		setAuthor("Rania Taleb, Sylvain Hallé");

		// Setup of RNGs for the random experiments
		RandomFloat random_float = new RandomFloat();
		random_float.setSeed(getRandomSeed());
		RandomBoolean random_boolean = new RandomBoolean();
		random_boolean.setSeed(getRandomSeed());

		// Factory setup: adding scenarios
		{
			m_factory.addScenario(HasNextFile.NAME, new HasNextFile());
			m_factory.addScenario(HasNextRandom.NAME, new HasNextRandom(random_float));
		}
		
		// Adding scenarios and monitors
		Region big_r = new Region();
    big_r.add(SCENARIO, HasNextFile.NAME, HasNextRandom.NAME);
    big_r.add(TOOL, DummyMonitor.TOOL_NAME, BeepBeepMonitor.TOOL_NAME);
    
    // Comparison of all tools on all scenarios
    {
    	ExperimentTable t_throughput = new ExperimentTable(SCENARIO, TOOL, THROUGHPUT);
    	t_throughput.setShowInList(false);
    	add(t_throughput);
    	ExperimentTable t_memory = new ExperimentTable(SCENARIO, TOOL, MAX_MEMORY);
    	t_memory.setShowInList(false);
    	add(t_memory);
    	for (Region r_s : big_r.all(SCENARIO, TOOL))
    	{
    		MonitorExperiment<?> exp = m_factory.get(r_s);
    		t_throughput.add(exp);
    		t_memory.add(exp);
    	}
    	TransformedTable tt_throughput = new TransformedTable(new ExpandAsColumns(TOOL, THROUGHPUT), t_throughput);
    	tt_throughput.setTitle("Throughput comparison");
    	tt_throughput.setNickname("tThroughput");
    	add(tt_throughput);
    	TransformedTable tt_memory = new TransformedTable(new ExpandAsColumns(TOOL, MAX_MEMORY), t_memory);
    	tt_memory.setTitle("Memory comparison");
    	tt_memory.setNickname("tMemory");
    	add(tt_memory);
    }
	}

	/**
	 * The main loop of the laboratory
	 * @param args Command-line arguments
	 */
	public static void main(String[] args)
	{
		// Nothing else to do here
		initialize(args, MonitorLab.class);
	}
}
