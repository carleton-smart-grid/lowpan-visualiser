/**
 * Class:				LowpanSim.java
 * Project:				Lowpan Network Sim
 * Author:				Jason Van Kerkhoven
 * Date of Update:		25/11/2017
 * Version:				1.0.0
 * 
 * Purpose:				Draw a lowpan mesh network using intuitive user controls.
 * 						Build to allow users to better grasp the goal of the overall project.
 * 						Support various strength lowpan nodes for realism.
 * 
 * Update Log:			v1.0.0
 * 							- null
 */
package ctrl;


//import libraries

//import packages
import datatype.LowpanNode;
import datatype.Tree;
import ui.NodeCanvas;




public class LowpanNetwork
{
	//declaring static class constants
	public static final String WINDOW_NAME = "6LoWPAN Mesh Network Sim";
	public static final int MAX_X = 1000;
	public static final int MAX_Y = 650;
	public static final int MIN_XY = 10;
	public static final String DEFAULT_NAME = "new_node";
	
	//declaring local instance variables
	private Tree<LowpanNode> network;
	
	//generic constructor
	public LowpanNetwork()
	{
		//initialize
		network = new Tree<LowpanNode>(null);
	}
	
	
	//create a new node
	public boolean addNode(String name, int rank, LowpanNode parent)
	{
		if (parent == null && network == null) { //it's the dodag root, add it as the root if it's empty
			network = new Tree<LowpanNode>(new LowpanNode(name, rank));
			return true;
		}
		
		if (network == null) return false; //we're waiting for the dodag
		
		if (parent == null) return true; //it's already been added, so technically this add was successful
		
		LowpanNode node = new LowpanNode(name, rank);
		
		return network.addNode(node, parent);
	}
	
	//remove a node from simulation
	public boolean removeNode(LowpanNode node)
	{
		if (network == null) return true; //the network is empty, remove probably worked
		
		return network.removeNode(node);
	}
	
	
	//remove all nodes from simulator
	public void removeAllNodes()
	{
		network = null;
	}
	
	
	//get all nodes in simulation
	public Tree<LowpanNode> getNetwork()
	{
		return network;
	}	
	
	
	//main runtime
	public static void main(String[] args)
	{
		LowpanNetwork network = new LowpanNetwork();
		NetListener nethandler = new NetListener(network); //have to send it so that the callback function exists
		nethandler.start(); //start this thread so it can listen for packets
		
		/*
		HashSet<LowpanNode> nodes = sim.getNodes();
		boolean flag = true;
		for (LowpanNode node : nodes)
		{
			if (flag)
			{
				System.out.println("treeify!");
				node.treeify();
				flag = false;
			}
		}	*/
	}
}