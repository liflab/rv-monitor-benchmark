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
package monitorlab.scenario.iterator;

import java.io.PrintStream;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.util.ElementPicker;
import monitorlab.source.GeneratedSource;
import monitorlab.source.SourceException;

/**
 * A source of randomly generated method names. The source receives as an
 * argument a random number generator.
 */
public class IteratorRandomSource extends GeneratedSource<String>
{
	/**
	 * The picker object that generates method names
	 */
	protected ElementPicker<String> m_methodPicker;
	
	/**
	 * Creates a new instance of the source
	 * @param picker A source of randomly generated floating-point numbers
	 * @param num_events The number of events to generate
	 */
	public IteratorRandomSource(Picker<Float> picker, int num_events)
	{
		super(num_events);
		m_methodPicker = new ElementPicker<String>(picker);
		m_methodPicker.add("next", 0.5f);
		m_methodPicker.add("hasNext", 0.5f);
	}
	
	@Override
	public void open() throws SourceException
	{
		m_methodPicker.reset();
	}

	@Override
	protected String produceEvent()
	{
		return m_methodPicker.pick();
	}

	@Override
	public void printEvent(String event, PrintStream ps)
	{
		ps.println(event);
	}

	@Override
	public String getFilename()
	{
		return "hasnext";
	}
}
