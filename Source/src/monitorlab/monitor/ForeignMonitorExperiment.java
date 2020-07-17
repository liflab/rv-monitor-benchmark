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
import ca.uqac.lif.labpal.FileHelper;

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
	 * The reference class of the experiment. The location of this class is used
	 * as the root of all relative paths pointing to internal files.
	 */
	protected Class<? extends ForeignMonitorExperiment<?>> m_class;
	
	/**
   * The name of the folder containing the R scripts <em>inside</em> the
   * LabPal directory structure. Must end with a forward slash.
   */
  protected String m_scriptFolder = "scripts/";
  
  /**
   * The name of the file containing the script <em>inside</em> the
   * LabPal directory structure, that is used to run the monitor.
   */
  protected transient String m_scriptFilename;
  
  /**
   * Arguments that must be passed to the script
   */
  /*@ null @*/ protected Object[] m_arguments = null;
	
	/**
	 * Creates a new instance of foreign monitor experiment.
	 * @param c The reference class of the experiment. The location of this class is used
	 * as the root of all relative paths pointing to internal files.
	 */
	public ForeignMonitorExperiment(Class<? extends ForeignMonitorExperiment<?>> c)
	{
		super();
		m_class = c;
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
		if (!FileHelper.fileExists(m_scriptFilename))
    {
      return false;
    }
    return super.prerequisitesFulfilled();
	}

	@Override
	public void execute() throws ExperimentException, InterruptedException
	{
		// TODO Auto-generated method stub
		
	}
}
