package org.arachnidium.testng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;



import org.arachnidium.util.logging.Log.LogRecWithAttach;
import org.testng.ITestResult;


public class ResultStore 
{
	//this hashMap contains ITestResult as key and object of ResultStore  as value 
	private static final HashMap<ITestResult, ResultStore> mappedResults = new HashMap<ITestResult, ResultStore>();
	private List<LogRecWithAttach> records;
	private List<Level> levels;
	
	private ResultStore(ITestResult result) 
	{
		records    = new ArrayList<LogRecWithAttach>();
		levels     = new ArrayList<Level>();
		mappedResults.put(result, this);
	}
	
	public synchronized static ResultStore get(ITestResult getResult)
	{
		ResultStore result = mappedResults.get(getResult);
		if (result==null)
		{
			result = new ResultStore(getResult);
			mappedResults.put(getResult, result);
		}
		return result;
	}
	
	protected void addLogRecord(LogRecWithAttach rec)
	{
		records.add(rec);
		if (!levels.contains(rec.getLevel()))
		{
			levels.add(rec.getLevel());
		}
	}
	
	public List<LogRecWithAttach> getLogRecords()
	{
		return(records);
	}
	
	//list of all methods that have been started
	public synchronized static List<ITestResult> getResults()
	{
		ArrayList<ITestResult> results = new ArrayList<ITestResult>(mappedResults.keySet());
		return results;
	}	
	
	//we get real status of test completion according to existing log levels
	public synchronized static Level getLevel(ITestResult result)
	{
		ResultStore store = get(result);
		List<Level> levelsOfResult = store.levels; 
		if (levelsOfResult.contains(Level.SEVERE))
		{
			return(Level.SEVERE);
		}
		else if (levelsOfResult.contains(Level.WARNING))
		{
			return(Level.WARNING);
		}
		else
		{
			return(Level.INFO);
		}
	}
}
