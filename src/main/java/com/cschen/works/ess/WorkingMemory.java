package com.cschen.works.ess;

import java.util.Iterator;
import java.util.Vector;

public class WorkingMemory {
	protected Vector<Clause> m_facts=new Vector<Clause>();
	
	public WorkingMemory()
	{
		
	}
	
	public void addFact(Clause fact)
	{
		m_facts.add(fact);
	}
	
	public boolean isNotFact(Clause c)
	{
		Iterator<Clause> iter=m_facts.iterator();
		while(iter.hasNext())
		{
			Clause cc=iter.next();
			if(cc.matchClause(c)==IntersectionType.MUTUALLY_EXCLUDE)
			{
				return true; 
			}
		}
		
		return false;
	}
	
	public void clearFacts()
	{
		m_facts.clear();
	}
	
	public boolean isFact(Clause c) 
	{
		for(int i=0; i<m_facts.size(); ++i)
		{
			Clause cc=m_facts.get(i);
			if(cc.matchClause(c)==IntersectionType.INCLUDE)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public String toString()
	{
		StringBuilder message=new StringBuilder();
		Iterator<Clause> iter=m_facts.iterator();
		while(iter.hasNext())
		{
			Clause cc=iter.next();
			if(iter.hasNext())
			{
				message.append(cc.toString()+"\n");
			}
			else
			{
				message.append(cc.toString());
			}
		}
		
		return message.toString();
	}
}
