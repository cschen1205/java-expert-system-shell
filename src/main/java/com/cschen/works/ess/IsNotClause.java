package com.cschen.works.ess;

public class IsNotClause extends Clause {
	public IsNotClause(String variable, String value)
	{
		super(variable, value);
		m_condition="!=";
	}
	
	public IntersectionType intersect(Clause rhs)
	{	
		if(rhs instanceof IsNotClause)
		{
			if(m_value.compareTo(rhs.getValue())==0)
			{
				return IntersectionType.INCLUDE;
			}
			else
			{
				return IntersectionType.UNKNOWN;
			}
		}
		else
		{
			return IntersectionType.UNKNOWN;
		}
	}
}
