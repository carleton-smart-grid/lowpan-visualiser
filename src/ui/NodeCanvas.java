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
import java.awt.Font;
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
	public static final int CHILDLIST_WIDTH = 250;
	public static final int HALFFONTWIDTH = 3;
	public static final int TEXTOFFSET = (int) ((int) NODE_DIAMETER/1.5); //Java complains if there isn't a double cast here and I don't know why (because 
	
	Graphics g;
	LowpanNetwork network;
	
	
	
	//generic constructor
	public NodeCanvas(LowpanNetwork network)
	{
		this.network = network;
		
	}
	
		
	private void drawTree(Graphics g, Tree<LowpanNode> node, int yLoc, int yIncrement, int xStart, int xEnd) {
		int x = ((int) xStart + ((xEnd - xStart)/2));
//		System.out.println("xrange is : " + xStart + "," + xEnd);
		//draw the node at x, y
//		System.out.println("Drawing " + node.getData() + " at " + x + "," + y);
		int centroidX = x - NODE_DIAMETER/2;
		int centroidY = yLoc - NODE_DIAMETER/2;
		if (node.getData().isInfiniteRank()) {
			g.setColor(Color.RED);
		} else {
			g.setColor(Color.BLACK);
		}
		g.fillOval(centroidX, centroidY, NODE_DIAMETER, NODE_DIAMETER);
		String s = node.getData().getName() + " : " + node.getData().getRank();
		g.drawString(s, x - (s.length() * HALFFONTWIDTH), yLoc - TEXTOFFSET);
		int numChildren = node.getChildren().size();
		int i = 0; // for the foreach loop.
		for (Tree<LowpanNode> child : node.getChildren()) {
			int newXrangeInc = (xEnd - xStart)/numChildren;
			int newXrangeStart = xStart + (newXrangeInc * i);
			int newXrangeEnd = (newXrangeStart + newXrangeInc);
			
			drawTree(g, child, yLoc + yIncrement, yIncrement, newXrangeStart, newXrangeEnd);			
			//draw a line from [x,y] to [(newXrangeInc/2 + newXrangeStart), (y + yIncrement)]
			g.drawLine(x, yLoc, (newXrangeInc/2 + newXrangeStart), (yLoc + yIncrement));
			++i;
		}
	}
	
	
	public void draw(Graphics g) {
		g.setFont(new Font("ComicSans", Font.PLAIN, 16));
		Dimension d = this.getSize();
		int ySize = (int) d.getHeight();
		int xSize = (int) d.getWidth();
		g.setColor(Color.BLACK);
		g.drawLine((xSize - XMARGIN) - CHILDLIST_WIDTH, 0, (xSize - XMARGIN) - CHILDLIST_WIDTH, ySize);
		if (network.getNetwork() == null) return; //wait until there's things to print
		int yIncrement = (int) Math.floor(ySize/network.getNetwork().getDepth());
		if (network.getNetwork() != null) {
			drawTree(g, network.getNetwork(), YMARGIN, yIncrement, 0, (xSize - XMARGIN) - CHILDLIST_WIDTH);
		}
		if (!network.getOrphans().isEmpty()) {
			int i = 0;
			yIncrement = ySize/(network.getOrphans().size());
			for (LowpanNode orphan : network.getOrphans()) {
//				System.out.println(orphan);
				int x = (xSize - (CHILDLIST_WIDTH/2));
				int centroidX = x - NODE_DIAMETER/2;
				int y = YMARGIN + (yIncrement * i);
				int centroidY = y - NODE_DIAMETER/2;
				if (orphan.isInfiniteRank()) {
					g.setColor(Color.RED);
				} else {
					g.setColor(Color.BLACK);
				}
				g.fillOval(centroidX, centroidY, NODE_DIAMETER, NODE_DIAMETER);
				String s = orphan.getName() + " : " + orphan.getRank();
				g.drawString(s, x - (s.length() * HALFFONTWIDTH), y - TEXTOFFSET);

				++i;
			}
		}

		
	}
	
	
	public void paint(Graphics g) {
		super.paint(g);
		draw(g);
	}
	
	
}
	