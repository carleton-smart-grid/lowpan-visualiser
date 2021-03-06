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


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import ctrl.LowpanNetwork;
import datatype.LowpanNode;
import datatype.Tree;

public class NodeCanvas extends JPanel 
{
	private static final long serialVersionUID = 1L;
	//declaring static class constants
	private static final int YMARGIN = 30;
	private static final int YTOPMARGIN = 100;
	private static final int XMARGIN = 0;
	private static final int ROUTING_THICCNESS = 4; // I used it because a lonely thiccboi should never exist
	public static final int NODE_DIAMETER = 20;
	public static final int CHILDLIST_WIDTH = 250;
	public static final int HALFFONTWIDTH_16 = 3;
	public static final int HALFFONTWIDTH_20 = 6;
	public static final int TEXTOFFSET = (int) ((int) NODE_DIAMETER/1.5); //Java complains if there isn't a double cast here and I don't know why (because 
	
	Graphics g;
	LowpanNetwork network;
	
	
	
	//generic constructor
	public NodeCanvas(LowpanNetwork network)
	{
		this.network = network;
		
	}
	
	
	private void drawNode(LowpanNode node, int x, int y, Graphics g) {
		int centroidX = x - NODE_DIAMETER/2;
		int centroidY = y - NODE_DIAMETER/2;
		if (node.isInfiniteRank()) {
			g.setColor(Color.RED);
		} else {
			g.setColor(Color.BLACK);
		}
		g.fillOval(centroidX, centroidY, NODE_DIAMETER, NODE_DIAMETER);
		String s = node.getName() + " : " + node.getRank();
		g.drawString(s, x - (s.length() * HALFFONTWIDTH_16), y - TEXTOFFSET);
	}
	
		
	private void drawTree(Graphics g, Tree<LowpanNode> node, int yLoc, int yIncrement, int xStart, int xEnd) {
		int x = ((int) xStart + ((xEnd - xStart)/2));
//		System.out.println("xrange is : " + xStart + "," + xEnd);
		//draw the node at x, y
//		System.out.println("Drawing " + node.getData() + " at " + x + "," + y);
		int numChildren = node.getChildren().size();
		int i = 0; // for the foreach loop.
		for (Tree<LowpanNode> child : node.getChildren()) {
			int newXrangeInc = (xEnd - xStart)/numChildren;
			int newXrangeStart = xStart + (newXrangeInc * i);
			int newXrangeEnd = (newXrangeStart + newXrangeInc);
						
			//draw a line from [x,y] to [(newXrangeInc/2 + newXrangeStart), (y + yIncrement)]
			g.setColor(Color.GREEN);
			g.drawLine(x, yLoc, (newXrangeInc/2 + newXrangeStart), (yLoc + yIncrement));

			drawTree(g, child, yLoc + yIncrement, yIncrement, newXrangeStart, newXrangeEnd);
			++i;
		}
		drawNode(node.getData(), x, yLoc, g);
	}
	
	
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(ROUTING_THICCNESS));
		
		
		Dimension d = this.getSize();
		int ySize = (int) d.getHeight();
		int drawableYSize = (ySize - YMARGIN) - YTOPMARGIN;
		int xSize = (int) d.getWidth();
		int drawableXSize = (xSize - XMARGIN) - CHILDLIST_WIDTH;
		
		g.setFont(new Font("ComicSans", Font.PLAIN, 20));
		g.setColor(Color.BLUE);
		g.drawLine((xSize - XMARGIN) - CHILDLIST_WIDTH, 0, (xSize - XMARGIN) - CHILDLIST_WIDTH, ySize);
		String s = "Lowpan Network";
		g.drawString(s, YTOPMARGIN/2 /*drawableXSize/2 - (s.length() * HALFFONTWIDTH_20)*/, YTOPMARGIN/2); //the top margin is where the drawing goes
		s = "Orphaned Nodes";
		g.drawString(s, drawableXSize + YTOPMARGIN/2/*+ (CHILDLIST_WIDTH/2) - (s.length() * HALFFONTWIDTH_20)*/, YTOPMARGIN/2); //the top margin is where the drawing goes
		
		int yIncrement;
		g.setFont(new Font("ComicSans", Font.PLAIN, 16));
		g.setColor(Color.BLACK);
		
		if (network.getNetwork() != null) { //wait until there's things to print
			yIncrement = (int) Math.floor(drawableYSize/network.getNetwork().getDepth()); //need network for yIncrement, can't assign it until now		
			drawTree(g, network.getNetwork(), YMARGIN + YTOPMARGIN, yIncrement, 0, drawableXSize);
		}
		
		if (!network.getOrphans().isEmpty()) {
			int i = 0;
			yIncrement = drawableYSize/(network.getOrphans().size());
			for (LowpanNode orphan : network.getOrphans()) {
//				System.out.println(orphan);
				int x = (xSize - (CHILDLIST_WIDTH/2));
				int y = YMARGIN + YTOPMARGIN + (yIncrement * i);
				drawNode(orphan, x, y, g);
//				if (orphan.isInfiniteRank()) {
//					g.setColor(Color.RED);
//				} else {
//					g.setColor(Color.BLACK);
//				}
//				g.fillOval(centroidX, centroidY, NODE_DIAMETER, NODE_DIAMETER);
//				s = orphan.getName() + " : " + orphan.getRank();
//				g.drawString(s, x - (s.length() * HALFFONTWIDTH), y - TEXTOFFSET);

				++i;
			}
		}

		
	}
	
	
	public void paint(Graphics g) {
		super.paint(g);
		draw(g);
	}
	
	
}
	