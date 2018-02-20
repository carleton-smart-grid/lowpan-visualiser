package ctrl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import datatype.LowpanNode;
import ui.NodeFrame;

public class NetListener extends Thread {
	
	public static final int BUF_SIZE = 1024;
	public static final int NUM_FIELDS = 4;
	public static final int NUM_PRIMARY_FIELDS = 2;
	public static final int PORT_NUMBER = 34217;
	
	LowpanNetwork simulator;
	DatagramSocket sock;
	NodeFrame frame;
	
	
	public NetListener(LowpanNetwork sim, NodeFrame frame) {
		this.frame = frame;
		simulator = sim;
		try {
			sock = new DatagramSocket(PORT_NUMBER);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
		
	
	public void run() {
		while(true) {
			
			//Some paths through the while loop have a continue, and dont reach the draw at the end, so do it here instead
			frame.update();
			
			byte[] buf = new byte[BUF_SIZE];
			DatagramPacket p = new DatagramPacket(buf, BUF_SIZE);
			try {
				sock.receive(p);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String dataString = new String(buf);
			dataString = dataString.replace("\n", "").replace("\r", "");
			
			String[] data = dataString.split(",");
			
			for (int i = 0; i < data.length; ++i) {
				data[i] = data[i].trim();
			}
			
			if (data.length == NUM_PRIMARY_FIELDS) { //there's no parent for this node
				simulator.addNode(data[0], Integer.parseInt(data[1].trim()), null);
				System.out.println("Adding " + data[0] + " with rank " + data[1]);
				continue;
			}
			
			if (data.length != NUM_FIELDS) continue; //completely drop invalid packets
			
			simulator.addNode(data[0], Integer.parseInt(data[1]), new LowpanNode(data[2], Integer.parseInt(data[3])));
			
			System.out.println("Adding " + data[0] + " with rank " + data[1] + " and parent " + data[2] + " : " + data[3]);
			if (simulator.getNetwork() != null) {
				simulator.getNetwork().printNode();
			}
		}
		
	}
	
}
