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
package monitorlab.scenario.iterator.beepbeep1;

import ca.uqac.info.ltl.Atom;
import ca.uqac.info.ltl.OperatorG;
import ca.uqac.info.ltl.OperatorImplies;
import ca.uqac.info.ltl.OperatorX;
import ca.uqac.info.monitor.Monitor;
import monitorlab.monitor.beepbeep1.AtomicMonitorFactory;
import monitorlab.monitor.beepbeep1.BeepBeep1Monitor;

/**
 * The BeepBeep 1 monitor for the {@link HasNextFile} scenario.
 * @author Sylvain Hallé
 */
public class HasNextMonitor extends BeepBeep1Monitor
{
	/**
	 * Creates a new instance of the monitor.
	 */
	public HasNextMonitor()
	{
		super(getMonitor());
	}
	
	/**
	 * Produces the BeepBeep 1 monitor for a given property
	 * @return The monitor
	 */
	protected static Monitor getMonitor()
	{
		OperatorG g = new OperatorG();
		OperatorImplies imp = new OperatorImplies();
		g.setOperand(imp);
		imp.setLeft(new Atom("next"));
		OperatorX x = new OperatorX();
		imp.setRight(x);
		x.setOperand(new Atom("hasNext"));
		AtomicMonitorFactory mf = new AtomicMonitorFactory();
		g.accept(mf);
		return mf.getMonitor();
	}
}
