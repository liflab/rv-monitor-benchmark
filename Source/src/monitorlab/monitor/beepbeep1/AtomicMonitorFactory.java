package monitorlab.monitor.beepbeep1;

import ca.uqac.info.ltl.Atom;
import ca.uqac.info.monitor.Event;
import ca.uqac.info.monitor.Monitor;
import ca.uqac.info.monitor.MonitorException;
import ca.uqac.info.monitor.MonitorFactory;
import ca.uqac.info.monitor.MonitorVisitor;
import monitorlab.scenario.iterator.beepbeep1.StringToAtom.AtomicEvent;

public class AtomicMonitorFactory extends MonitorFactory
{
	@Override
  public void visit(Atom o)
  {
    m_stack.push(new AtomicMonitor(o.getSymbol()));
  }
	
	public static class AtomicMonitor extends Monitor
	{
		protected String m_symbol;
		
		protected Verdict m_verdict;
		
		public AtomicMonitor(String symbol)
		{
			super();
			m_symbol = symbol;
			m_verdict = null;
		}

		@Override
		public void processEvent(Event e) throws MonitorException
		{
			if (m_verdict != null)
			{
				if (!(e instanceof AtomicEvent))
				{
					m_verdict = Verdict.FALSE;
				}
				m_verdict = Verdict.TRUE;
			}
		}

		@Override
		public Monitor deepClone()
		{
			return new AtomicMonitor(m_symbol);
		}

		@Override
		public void setValue(Atom a, Atom v)
		{
			// Nothing to do
		}

		@Override
		public void accept(MonitorVisitor v)
		{
			// Nothing to do
		}

		@Override
		public void instanceAcceptPostfix(MonitorVisitor v)
		{
			// Nothing to do
		}
	}
}