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

import monitorlab.source.InternalLineSource;
import monitorlab.source.LineConverter;

/**
 * A source of method names read from an internal text file.
 */
public class IteratorFileSource extends InternalLineSource<String>
{
	/**
	 * Creates a new instance of the source.
	 * @param converter The converter to turn text lines into events to be
	 * consumed by a monitor
	 */
	public IteratorFileSource(LineConverter<String> converter)
	{
		super("/monitorlab/scenario/iterator/events.txt", converter);
	}
}
