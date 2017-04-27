package com.cschen.works.ess;

import java.util.Vector;
import java.util.Iterator;

public class Rule {
	protected Clause m_consequent=null;
	protected Vector<Clause> m_antecedents=new Vector<Clause>();
	protected boolean m_fired=false;
	protected String m_name;
	protected int m_index=0;
	
	public Rule(String name)
	{
		m_name=name;
	}
	
	public void firstAntecedent()
	{
		m_index=0;
	}
	
	public boolean hasNextAntecedents()
	{
		return m_index < m_antecedents.size();
	}
	
	public Clause nextAntecedent()
	{
		Clause c=m_antecedents.get(m_index);
		m_index++;
		return c;
	}
	
	public String getName()
	{
		return m_name;
	}
	
	public void setConsequent(Clause consequent)
	{
		m_consequent=consequent;
	}
	
	public void addAntecedent(Clause antecedent)
	{
		m_antecedents.add(antecedent);
	}
	
	public Clause getConsequent()
	{
		return m_consequent;
	}
	
	public boolean isFired()
	{
		return m_fired;
	}
	
	public void fire(WorkingMemory wm)
	{
		if(!wm.isFact(m_consequent))
		{
			wm.addFact(m_consequent);
		}
		
		m_fired=true;
	}
	
	public boolean isTriggered(WorkingMemory wm) 
	{				
		Iterator<Clause> itant=m_antecedents.iterator();
		while(itant.hasNext())
		{
			Clause antecedent=itant.next();
			if(!wm.isFact(antecedent))
			{
				return false;
			}
		}
		
		return true;
	}
}
