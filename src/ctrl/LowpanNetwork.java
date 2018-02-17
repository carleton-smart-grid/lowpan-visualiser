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


import java.util.HashSet;

//import libraries

//import packages
import datatype.LowpanNode;
import datatype.Tree;
import ui.NodeFrame;




public class LowpanNetwork
{
	//declaring static class constants
	public static final int MIN_XY = 10;
	public static final int DODAG_RANK = 256;
	public static final String DEFAULT_NAME = "new_node";
	
	//declaring local instance variables
	private Tree<LowpanNode> network;
	private HashSet<LowpanNode> orphanList;
	
	//generic constructor
	public LowpanNetwork()
	{
		//initialize
		network = null;
		orphanList = new HashSet<LowpanNode>();
	}

	
	//create a new node
	public boolean addNode(String name, int rank, LowpanNode parent)
	{
		LowpanNode nodeToAdd = new LowpanNode(name, rank);
		if (parent == null && network == null) { //it's the dodag root, add it as the root if it's empty
			System.out.println("Adding the root");
			network = new Tree<LowpanNode>(nodeToAdd);
			return true;
		}
		
		if (network == null) {
			System.out.println("You are not the dodag and there is no dodag, adding to orphanList");
			orphanList.add(nodeToAdd);
			return false; //we're waiting for the dodag
		}
		
		if (parent == null) { //This used to be a node on the network, and it's just left the network
			if(rank == DODAG_RANK) { //don't add the dodag again and don't remove it, it never has a parent
				return true;
			}
			System.out.println("This node reported that it has no parent, moving to orphanList");
			network.removeNode(nodeToAdd);
			orphanList.add(nodeToAdd);
			return true;
		}
			
//		System.out.println("OrphanList.contains returned " + orphanList.contains(nodeToAdd));
//		System.out.println("OrphanList.remove returned " + orphanList.remove(nodeToAdd)); //don't bother checking, takes just as long if not longer than always removing
		
		return network.addNode(nodeToAdd, parent);
		
		
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
	
	
	public HashSet<LowpanNode> getOrphans(){
		return orphanList;
	}
	
	//main runtime
	public static void main(String[] args)
	{
		LowpanNetwork network = new LowpanNetwork();
		NodeFrame nodeFrame = new NodeFrame(network);
		NetListener nethandler = new NetListener(network, nodeFrame); //have to send it so that the callback function exists
		nethandler.start(); //start this thread so it can listen for packets
		
	}
}