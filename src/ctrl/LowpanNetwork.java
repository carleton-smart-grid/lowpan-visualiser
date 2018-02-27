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


import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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
	public static final int INFINITE_RANK = 65535;
	public static final String DEFAULT_NAME = "new_node";
	
	//declaring local instance variables
	private Tree<LowpanNode> network;
	private Set<LowpanNode> orphanList;
	
	//generic constructor
	public LowpanNetwork()
	{
		//initialize
		network = null;
//		orphanList = new HashSet<LowpanNode>();
		orphanList = Collections.newSetFromMap(new ConcurrentHashMap<LowpanNode, Boolean>());
	}

	
	//create a new node
	public synchronized boolean addNode(String name, int rank, LowpanNode parent)
	{
		LowpanNode nodeToAdd = new LowpanNode(name, rank);
		nodeToAdd.update();
		
		if (parent == null && rank == DODAG_RANK) { //it's the dodag root, add it as the root if it's empty
			if (network == null) {
				System.out.println("Adding the root");
				network = new Tree<LowpanNode>(nodeToAdd);
				return true;
			}
			network.getData().update();
		}
		
		orphanList.remove(nodeToAdd); //don't bother checking, takes just as long if not longer than always removing
		
		if (network != null) {
			network.removeNode(nodeToAdd); //remove it here as well
		}
		
		if (network == null) {
			System.out.println("You are not the dodag and there is no dodag, adding " + nodeToAdd + " to orphanList");
			orphanList.add(nodeToAdd);
			return false; //we're waiting for the dodag
		}
		
		if (parent == null) { //This used to be a node on the network, and it's just left the network
			if(rank == DODAG_RANK) { //don't add the dodag again and don't remove it, it never has a parent
				return true;
			}
			System.out.println("This node reported that it has no parent, moving to orphanList");
			orphanList.add(nodeToAdd);
			return true;
		}
			
//		System.out.println("OrphanList.contains returned " + orphanList.contains(nodeToAdd));
		
		if (!network.addNode(nodeToAdd, parent)) {
			System.out.println("Your parent couldn't be found, adding to orphanList");
			orphanList.add(nodeToAdd);
			return true;
		}
		
		return true;
		
		
	}
	
	public synchronized void addOrphan(LowpanNode node) { //should only be called if you are CERTAIN it should be an orphan
		network.removeNode(node);
		orphanList.add(node);
	}
	
	//remove a node from simulation
	public synchronized boolean removeNode(LowpanNode node)
	{
		if (node.equals(network.getData())) { //if it's the root
			network = null;
		}
		orphanList.remove(node); //always remove it from the orphanList since this doesn't break on nulls
		if (network == null) return true; //the network is empty, remove probably worked
		
		return network.removeNode(node);
	}
	
	
	//remove all nodes from simulator
	public synchronized void removeAllNodes()
	{
		network = null;
	}
	
	
	//get all nodes in simulation
	public synchronized Tree<LowpanNode> getNetwork()
	{
		return network;
	}	
	
	
	public synchronized Set<LowpanNode> getOrphans(){
		return orphanList;
	}
	
	//main runtime
	public static void main(String[] args)
	{
		LowpanNetwork network = new LowpanNetwork();
		NodeFrame nodeFrame = new NodeFrame(network);
		NetListener nethandler = new NetListener(network, nodeFrame); //have to send it so that the callback function exists
		ElementExpirer expirer = new ElementExpirer(network, nodeFrame); //so it can repaint on expiry
		expirer.start(); //start the expirer
		nethandler.start(); //start this thread so it can listen for packets
		
	}
}