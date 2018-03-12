package ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ctrl.LowpanNetwork;

public class NodeFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_WINDOW_X = 1301;
	private static final int DEFAULT_WINDOW_Y = 698;
	private static final String TITLE = "Lowpan Visualizer";
	
	JPanel canvasPane;
	
	public NodeFrame(LowpanNetwork network) {
		super(TITLE);
		this.setBounds(0, 0, DEFAULT_WINDOW_X, DEFAULT_WINDOW_Y);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(new ImageIcon("icon.gif").getImage());	
	
		canvasPane = new NodeCanvas(network);
		
		canvasPane.setBackground(Color.WHITE);
		canvasPane.setBorder(BorderFactory.createLineBorder(Color.black));
		canvasPane.setPreferredSize(new Dimension(DEFAULT_WINDOW_X, DEFAULT_WINDOW_Y));
		
		this.setContentPane(canvasPane);
		this.setVisible(true);
		
	}
	
	public void update() {
//		System.out.println("repainting");
		canvasPane.repaint();
	}

}
