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

/**
 * A source where events are generated on-the-fly.
 */
public abstract class GeneratedSource<T> implements PullSource<T>
{
	/**
	 * The number of events to produce
	 */
	protected int m_eventsToProduce;
	
	/**
	 * The number of events produced so far
	 */
	protected int m_eventsProduced;
	
	/**
	 * Creates a new instance of the source.
	 * @param event_count The number of events to produce
	 */
	public GeneratedSource(int event_count)
	{
		super();
		m_eventsToProduce = event_count;
		m_eventsProduced = 0;
	}

	@Override
	public T pull() throws SourceException
	{
		if (m_eventsProduced >= m_eventsToProduce)
		{
			throw new SourceException("No more events");
		}
		m_eventsProduced++;
		return produceEvent();
	}

	@Override
	public boolean hasNext() throws SourceException
	{
		return m_eventsProduced < m_eventsToProduce;
	}

	@Override
	public int getLength() throws SourceException
	{
		return m_eventsToProduce;
	}

	@Override
	public void close() throws SourceException
	{
		// Nothing to do by default
	}
	
	/**
	 * Produces the next event.
	 * @return The event
	 */
	protected abstract T produceEvent();
}
