/**
 * Class:				NodeCanvas.java
 * Project:				Lowpan Network Sim
 * Author:				Jason Van Kerkhoven
 * Date of Update:		25/11/2017
 * Version:				1.0.0
 * 
 * Purpose:				Draw a system of nodes, including signal wells and mesh links.
 * 
 * Update Log:			v1.0.0
 * 							- null
 */
package ui;


import java.awt.Graphics;

import javax.swing.JPanel;

import ctrl.LowpanNetwork;
import datatype.LowpanNode;
import datatype.Tree;

public class NodeCanvas extends JPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//declaring static class constants
	private static final double SIM_X = LowpanNetwork.MAX_X;
	private static final double SIM_Y = LowpanNetwork.MAX_Y;
	private static final int ROUTING_THICCNESS = 4;
	public static final int NODE_DIAMETER = 20;
	
	Tree<LowpanNode> nodes;
	
	//generic constructor
	public NodeCanvas(Tree<LowpanNode> nodes)
	{
		super();
		this.nodes = nodes;
	}
	
		
	private void drawTree(Graphics g, Tree<LowpanNode> node, int yLoc, int yIncrement, int xStart, int xEnd) {
		int x = (int) xStart + ((xEnd - xStart)/2);
		int y = (int) yLoc;
		//draw the node at x, y
		int numChildren = node.getChildren().size();
		int i = 1; // for the foreach loop. Yes, it starts at 1 because that makes drawing easier
		for (Tree<LowpanNode> child : node.getChildren()) {
			int newXrangeStart = xStart * i;
			int newXrangeInc = (xStart - xEnd)/numChildren;
			int newXrangeEnd = (xStart + newXrangeInc);
			
			drawTree(g, child, y + yIncrement, yIncrement, newXrangeStart, newXrangeEnd);			
			//draw a line from x,y to 
		}
	}
	
	
	public void draw() {
		int yIncrement = (int) Math.floor(SIM_Y/nodes.getDepth());
		if (nodes != null) {
			drawTree(/*Graphics g?*/nothing, nodes, SIM_Y, yIncrement, 0, SIM_X);
		}
	}
	
	
}
