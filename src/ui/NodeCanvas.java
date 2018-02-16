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


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import ctrl.LowpanNetwork;
import datatype.LowpanNode;
import datatype.Tree;

public class NodeCanvas extends JPanel 
{
	private static final long serialVersionUID = 1L;
	//declaring static class constants
	private static final int YMARGIN = 30;
	private static final int XMARGIN = 0;
	private static final int ROUTING_THICCNESS = 4;
	public static final int NODE_DIAMETER = 20;
	public static final int CHILDLIST_WIDTH = 150;
	
	Graphics g;
	LowpanNetwork network;
	
	
	
	//generic constructor
	public NodeCanvas(LowpanNetwork network)
	{
		this.network = network;
		
	}
	
		
	private void drawTree(Graphics g, Tree<LowpanNode> node, int yLoc, int yIncrement, int xStart, int xEnd) {
//		System.out.println("xrange is : " + xStart + "," + xEnd);
		int x = (int) xStart + ((xEnd - xStart)/2);
		int y = (int) yLoc;
		//draw the node at x, y
//		System.out.println("Drawing " + node.getData() + " at " + x + "," + y);
		int centroidX = x - NODE_DIAMETER/2;
		int centroidY = y - NODE_DIAMETER/2;
		g.setColor(Color.BLACK);
		g.fillOval(centroidX, centroidY, NODE_DIAMETER, NODE_DIAMETER);
		g.drawString(node.getData().getName() + " : " + node.getData().getRank(), x, y - NODE_DIAMETER);
		int numChildren = node.getChildren().size();
		int i = 0; // for the foreach loop.
		for (Tree<LowpanNode> child : node.getChildren()) {
			int newXrangeInc = (xEnd - xStart)/numChildren;
			int newXrangeStart = xStart + (newXrangeInc * i);
			int newXrangeEnd = (newXrangeStart + newXrangeInc);
			
			drawTree(g, child, y + yIncrement, yIncrement, newXrangeStart, newXrangeEnd);			
			//draw a line from [x,y] to [(newXrangeInc/2 + newXrangeStart), (y + yIncrement)]
			g.drawLine(x, y, (newXrangeInc/2 + newXrangeStart), (y + yIncrement));
			++i;
		}
	}
	
	
	public void draw(Graphics g) {
		Dimension d = this.getSize();
		int ySize = (int) d.getHeight();
		int xSize = (int) d.getWidth();
		if (network.getNetwork() == null) return; //wait until there's things to print
		int yIncrement = (int) Math.floor(ySize/network.getNetwork().getDepth());
		if (network.getNetwork() != null) {
			drawTree(g, network.getNetwork(), YMARGIN, yIncrement, 0, (xSize - XMARGIN) - CHILDLIST_WIDTH);
		}
		for (LowpanNode orphan : network.getOrphans()) {
			int centroidX = x - NODE_DIAMETER/2;
			int centroidY = y - NODE_DIAMETER/2;
			g.setColor(Color.BLACK);
			g.fillOval(centroidX, centroidY, NODE_DIAMETER, NODE_DIAMETER);
			System.out.println("\t\tOrphan : " + orphan.getName());
		}
	}
	
	
	public void paint(Graphics g) {
		super.paint(g);
		draw(g);
	}
	
	
}
	