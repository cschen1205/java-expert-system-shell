package com.cschen.works.ess;

public class IsClause extends Clause {
	
	public IsClause(String variable, String value)
	{
		super(variable, value);
		m_condition="=";
	}
	
	public IntersectionType intersect(Clause rhs)
	{	
		if(rhs instanceof IsClause)
		{
			if(m_value.compareTo(rhs.getValue())==0)
			{
				return IntersectionType.INCLUDE;
			}
			else
			{
				return IntersectionType.MUTUALLY_EXCLUDE;
			}
		}
		
		String v1=m_value;
		String v2=rhs.getValue();
		
		double a=0;
		double b=0;
		
		try{
			a=Double.parseDouble(v1);
			b=Double.parseDouble(v2);
			
		}catch(Exception e)
		{
			return IntersectionType.UNKNOWN;
		}
		
		if(rhs instanceof LessClause)
		{			
			if(a >= b)
			{
				return IntersectionType.MUTUALLY_EXCLUDE;
			}
			else
			{
				return IntersectionType.INCLUDE;
			}
		}
		else if(rhs instanceof LEqualClause)
		{
			if(a > b)
			{
				return IntersectionType.MUTUALLY_EXCLUDE;
			}
			else
			{
				return IntersectionType.INCLUDE;
			}
		}
		else if(rhs instanceof GreaterClause)
		{			
			if(a <= b)
			{
				return IntersectionType.MUTUALLY_EXCLUDE;
			}
			else
			{
				return IntersectionType.INCLUDE;
			}
		}
		else if(rhs instanceof GEqualClause)
		{
			if(a < b)
			{
				return IntersectionType.MUTUALLY_EXCLUDE;
			}
			else
			{
				return IntersectionType.INCLUDE;
			}
		}
		else
		{
			return IntersectionType.UNKNOWN;
		}
		
	}
}
