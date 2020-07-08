package monitorlab.monitor;

public interface Monitor<T,U> 
{
	public void feed(T event) throws MonitorException;
	
	public U getVerdict() throws MonitorException;
	
	public long getMemory() throws MonitorException;
}
