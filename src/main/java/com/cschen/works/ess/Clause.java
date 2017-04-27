package com.cschen.works.ess;

public abstract class Clause {
	protected String m_variable;
	protected String m_value;
	protected String m_condition;
	
	public Clause(String variable, String value)
	{
		m_variable=variable;
		m_value=value;
	}
	
	public Clause(String variable, String condition, String value)
	{
		m_variable=variable;
		m_value=value;
		m_condition=condition;
	}
	
	public String getVariable()
	{
		return m_variable;
	}
	
	public String getValue()
	{
		return m_value;
	}
	
	public IntersectionType matchClause(Clause rhs) 
	{
		if(m_variable.compareTo(rhs.getVariable())!=0)
		{
			return IntersectionType.UNKNOWN;
		}
		
		return intersect(rhs);
	}
	
	protected abstract IntersectionType intersect(Clause rhs);
	
	public String toString()
	{
		return m_variable+" "+m_condition+" "+m_value;
	}
}
