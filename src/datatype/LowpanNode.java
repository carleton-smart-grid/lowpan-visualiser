/**
 * Class:				LowpanNode.java
 * Project:				Lowpan Network Sim
 * Author:				Jason Van Kerkhoven
 * Date of Update:		25/11/2017
 * Version:				1.0.0
 * 
 * Purpose:				Represent a generic 6lowpan networked node.
 * 
 * Update Log:			v1.0.0
 * 							- null
 */
package datatype;


//import libraries

//import packages




public class LowpanNode 
{
	
	private String name;
	private int rank;
	
	//generic constructor
	public LowpanNode(String name, int rank)
	{
		this.name = name;
		this.rank = rank;
	}
	
	
	
	public String getName()
	{
		return name;
	}
	
	public int getRank() {
		return rank;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	
	@Override
//	generic equals
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		else if (obj instanceof LowpanNode)
		{
			LowpanNode otherNode = (LowpanNode)obj;
			return (name.equals(otherNode.name));
		}
		else
		{
			return false;
		}
	}

	
	@Override
	public int hashCode() {
		return name.hashCode(); //same name different rank should return the same hashcode
	}
	
//	public boolean equalsByName(Object obj) {
//		if (this == obj)
//		{
//			return true;
//		}
//		else if (obj instanceof LowpanNode)
//		{
//			LowpanNode otherNode = (LowpanNode)obj;
//			return (name.equals(otherNode.name));
//		}
//		else
//		{
//			return false;
//		}
//	}
//	
	
	@Override
	//nice printable
	public String toString()
	{
		return "Node " + name;
	}
}
