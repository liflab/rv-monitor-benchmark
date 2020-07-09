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

import ca.uqac.info.monitor.Event;
import monitorlab.source.EventConverter;
import monitorlab.source.SourceException;

public class StringToAtom implements EventConverter<String,Event>
{
	/**
	 * Reference to a single public instance of the converter.
	 */
	public static final transient StringToAtom instance = new StringToAtom();

	/**
	 * Creates a new instance of the converter
	 */
	protected StringToAtom()
	{
		super();
	}
	
	@Override
	public Event convert(String e) throws SourceException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
