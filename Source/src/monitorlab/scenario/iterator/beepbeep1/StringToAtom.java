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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ca.uqac.info.ltl.Constant;
import ca.uqac.info.monitor.Event;
import ca.uqac.info.simplexpath.SimpleXPathExpression;
import monitorlab.source.EventConverter;
import monitorlab.source.SourceException;

/**
 * Converts a string into a BeepBeep 1 {@link AtomicEvent}.
 */
public class StringToAtom implements EventConverter<String,Event>
{
	/**
	 * A map associating strings to atomic events
	 */
	protected Map<String,AtomicEvent> m_events;
	
	/**
	 * Creates a new instance of the converter
	 */
	public StringToAtom()
	{
		super();
		m_events = new HashMap<String,AtomicEvent>();
	}
	
	/**
	 * Adds a new symbol to be converted
	 * @param symbol The symbol
	 * @return This object
	 */
	public StringToAtom add(String symbol)
	{
		m_events.put(symbol, new AtomicEvent(symbol));
		return this;
	}
	
	@Override
	public Event convert(String e) throws SourceException
	{
		return m_events.get(e);
	}
	
	/**
	 * Event containing a single atomic string.
	 */
	public static class AtomicEvent extends Event
	{
		/**
		 * The singleton containing the constant represented by this atomic event
		 */
		protected Set<Constant> m_evaluation;
		
		/**
		 * The symbol represented by this atomic event
		 */
		protected String m_symbol;
		
		/**
		 * Creates a new atomic event
		 * @param symbol The symbol represented by this atomic event
		 */
		public AtomicEvent(String symbol)
		{
			super();
			m_symbol = symbol;
			m_evaluation = new HashSet<Constant>();
			m_evaluation .add(new Constant(symbol));
		}

		@Override
		public Set<Constant> evaluate(SimpleXPathExpression query, Map<String, String> variables)
				throws EvaluationException
		{
			return m_evaluation;
		}
		
		@Override
		public int hashCode()
		{
			return m_symbol.hashCode();
		}
		
		@Override
		public boolean equals(Object o)
		{
			if (o == null || !(o instanceof AtomicEvent))
			{
				return false;
			}
			return m_symbol.compareToIgnoreCase(((AtomicEvent) o).m_symbol) == 0;
		}
	}

}
