package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ctrl.LowpanNetwork;
import datatype.LowpanNode;
import datatype.Tree;

public class NodeFrame extends JFrame {

	private static final double SIM_X = LowpanNetwork.MAX_X;
	private static final double SIM_Y = LowpanNetwork.MAX_Y;
	private static final int DEFAULT_WINDOW_X = 1301;
	private static final int DEFAULT_WINDOW_Y = 698;
	private static final String TITLE = "Lowpan Visualizer";
	
	JPanel canvasPane;
	
	public NodeFrame(Tree<LowpanNode> tree) {
		super(TITLE);
		this.setBounds(0, 0, DEFAULT_WINDOW_X, DEFAULT_WINDOW_Y);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(this);
		this.setVisible(true);
		
		canvasPane = new NodeCanvas(tree);
		
		canvasPane.setBackground(Color.WHITE);
		canvasPane.setBorder(BorderFactory.createLineBorder(Color.black));
		canvasPane.setPreferredSize(new Dimension(LowpanNetwork.MAX_X, LowpanNetwork.MAX_Y));
	}
	
	public void update() {
		canvasPane.repaint();
	}

}
