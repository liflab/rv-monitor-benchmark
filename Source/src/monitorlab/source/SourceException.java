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
package monitorlab.source;

public class SourceException extends Exception
{
	/**
	 * Dummy UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new monitor exception
	 * @param t The throwable to wrap into this exception
	 */
	public SourceException(Throwable t)
	{
		super(t);
	}

	/**
	 * Creates a new monitor exception
	 * @param message The message to set to this exception
	 */
	public SourceException(String message)
	{
		super(message);
	}

}
