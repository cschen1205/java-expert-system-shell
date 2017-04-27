package com.cschen.works.ess;

import java.util.Vector;
import java.util.Iterator;

public class RuleInferenceEngine {
	protected Vector<Rule> m_rules=new Vector<Rule>();
	protected WorkingMemory m_wm=new WorkingMemory();
	
	public RuleInferenceEngine()
	{
		
	}
	
	public void addRule(Rule rule)
	{
		m_rules.add(rule);
	}
	
	public void clearRules()
	{
		m_rules.clear();
	}
	
	//forward chain
	public void infer()
	{
		Vector<Rule> cs=null;
		do{
			cs=match();
			if(cs.size()>0)
			{
				if(!fireRule(cs))
				{
					break;
				}
			}
		}while(cs.size()>0);
	}
	
	//backward chain
	public Clause infer(String goal_variable, Vector<Clause> unproved_conditions)
	{
		Clause conclusion=null;
		Vector<Rule> goal_stack=new Vector<Rule>();
		
		Iterator<Rule> itrl=m_rules.iterator();
		while(itrl.hasNext())
		{
			Rule rule=itrl.next();
			Clause consequent=rule.getConsequent();
			if(consequent.getVariable().compareTo(goal_variable)==0)
			{
				goal_stack.add(rule);
			}
		}
		
		itrl=goal_stack.iterator();
		while(itrl.hasNext())
		{
			Rule rule=itrl.next();
			
			rule.firstAntecedent();
			boolean goal_reached=true;
			while(rule.hasNextAntecedents())
			{
				Clause antecedent=rule.nextAntecedent();
				if(!m_wm.isFact(antecedent))
				{
					if(m_wm.isNotFact(antecedent)) //conflict with memory
					{
						goal_reached=false;
						break;
					}
					else if(isFact(antecedent, unproved_conditions)) //deduce to be a fact
					{
						m_wm.addFact(antecedent);
					}
					else //deduce to not be a fact
					{
						goal_reached=false;
						break;
					}
				}
			}
			
			if(goal_reached)
			{
				conclusion = rule.getConsequent();
				break; 
			}
		}
		
		return conclusion;
	}
	
	public void clearFacts()
	{
		m_wm.clearFacts();
	}
	
	protected boolean isFact(Clause goal, Vector<Clause> unproved_conditions)
	{
		Iterator<Rule> itrl=m_rules.iterator();
		Vector<Rule> goal_stack=new Vector<Rule>();
		
		while(itrl.hasNext())
		{
			Rule rule=itrl.next();
			Clause consequent=rule.getConsequent();
			IntersectionType it=consequent.matchClause(goal);
			if(it==IntersectionType.INCLUDE)
			{
				goal_stack.add(rule);
			}
		}
		
		if(goal_stack.size()==0)
		{
			unproved_conditions.add(goal);
		}
		else
		{
			itrl=goal_stack.iterator();
			while(itrl.hasNext())
			{
				Rule rule=itrl.next();
				
				rule.firstAntecedent();
				boolean goal_reached=true;
				while(rule.hasNextAntecedents())
				{
					Clause antecedent=rule.nextAntecedent();
					if(!m_wm.isFact(antecedent))
					{
						if(m_wm.isNotFact(antecedent))
						{
							goal_reached=false;
							break;
						}
						else if(isFact(antecedent, unproved_conditions))
						{
							m_wm.addFact(antecedent);
						}
						else
						{
							goal_reached=false;
							break;
						}
					}
				}
				
				if(goal_reached)
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	protected boolean fireRule(Vector<Rule> conflictingRules)
	{
		Iterator<Rule> itrl=conflictingRules.iterator();
		boolean hasRule2Fire=false;
		while(itrl.hasNext())
		{
			Rule rule=itrl.next();
			if(!rule.isFired())
			{
				hasRule2Fire=true;
				rule.fire(m_wm);
			}
		}
		
		return hasRule2Fire;
		
	}
	
	public WorkingMemory getFacts()
	{
		return m_wm;
	}
	
	public void addFact(Clause c)
	{
		m_wm.addFact(c);
	}
	
	protected Vector<Rule> match() 
	{
		Vector<Rule> cs=new Vector<Rule>();
		Iterator<Rule> itcs=m_rules.iterator();
		while(itcs.hasNext())
		{
			Rule rule=itcs.next();
			if(rule.isTriggered(m_wm))
			{
				cs.add(rule);
			}
		}
		return cs;
	}
	
	
}
