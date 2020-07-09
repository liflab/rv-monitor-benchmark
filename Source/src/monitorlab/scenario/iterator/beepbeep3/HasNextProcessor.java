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
package monitorlab.scenario.iterator.beepbeep3;

import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.GroupProcessor;
import ca.uqac.lif.cep.functions.ApplyFunction;
import ca.uqac.lif.cep.functions.Constant;
import ca.uqac.lif.cep.functions.FunctionTree;
import ca.uqac.lif.cep.functions.StreamVariable;
import ca.uqac.lif.cep.ltl.TrooleanCast;
import ca.uqac.lif.cep.tmf.Fork;
import ca.uqac.lif.cep.tmf.Trim;
import ca.uqac.lif.cep.util.Booleans;
import ca.uqac.lif.cep.util.Equals;

/**
 * The BeepBeep processor chain for the {@link HasNextFile} scenario.
 * @author Sylvain Hallé
 */
public class HasNextProcessor extends GroupProcessor
{
	/**
	 * Creates a new instance of the processor.
	 */
	public HasNextProcessor()
	{
		super(1, 1);
		Fork f = new Fork(2);
		ApplyFunction is_next = new ApplyFunction(new FunctionTree(Equals.instance, StreamVariable.X, new Constant("next")));
		Connector.connect(f, 0, is_next, 0);
		Trim t = new Trim(1);
		Connector.connect(f, 1, t, 0);
		ApplyFunction is_hasNext = new ApplyFunction(new FunctionTree(Equals.instance, StreamVariable.X, new Constant("hasNext")));
		Connector.connect(t, is_hasNext);
		ApplyFunction imp = new ApplyFunction(new FunctionTree(TrooleanCast.instance, Booleans.implies));
		Connector.connect(is_next, 0, imp, 0);
		Connector.connect(is_hasNext, 0, imp, 1);
		addProcessors(f, is_next, t, is_hasNext, imp);
		associateInput(0, f, 0);
		associateOutput(0, imp, 0);
	}
	
	@Override
	public HasNextProcessor duplicate(boolean with_state)
	{
		if (with_state)
		{
			throw new UnsupportedOperationException("Duplication not supported on this processor");
		}
		return new HasNextProcessor();
	}
}
