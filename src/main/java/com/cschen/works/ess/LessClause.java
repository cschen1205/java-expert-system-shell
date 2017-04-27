package com.cschen.works.ess;

public class LessClause extends LEqualClause {
	public LessClause(String variable, String value)
	{
		super(variable, value);
		m_condition="<";
	}
	
	protected  IntersectionType intersect(Clause rhs)
	{
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
			//v1 < a
			//v2 < b 
			//matched: b <= a
			//unmatched: b > a
			if(b <= a)
			{
				return IntersectionType.INCLUDE;
			}
			else
			{
				return IntersectionType.UNKNOWN;
			}
		}
		else if(rhs instanceof LEqualClause)
		{
			//v1 < a
			//v2 <= b 
			//matched: b < a
			//unmatched: b >= a
			if(b < a)
			{
				return IntersectionType.INCLUDE;
			}
			else
			{
				return IntersectionType.UNKNOWN;
			}
		}
		else if(rhs instanceof IsClause)
		{
			//v1 < a
			//v2 = b
			//matched: b < a
			//mutually exclusive: b >= a
			if(b < a)
			{
				return IntersectionType.INCLUDE;
			}
			else
			{
				return IntersectionType.MUTUALLY_EXCLUDE;
			}
		}
		else if(rhs instanceof GEqualClause)
		{
			//v1 < a
			//v2 >= b
			//mutually exclusive: b >= a
			//unmatched: b < a
			if(b >= a)
			{
				return IntersectionType.MUTUALLY_EXCLUDE;
			}
			else
			{
				return IntersectionType.UNKNOWN;
			}
		}
		else if(rhs instanceof GreaterClause)
		{
			//v1 < a
			//v2 > b
			//mutually exclusive: b >= a
			//unmatched: b < a
			if(b >= a)
			{
				return IntersectionType.MUTUALLY_EXCLUDE;
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
