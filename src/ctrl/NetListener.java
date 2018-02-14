package ctrl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import datatype.LowpanNode;

public class NetListener extends Thread {
	
	public static final int BUF_SIZE = 1024;
	public static final int NUM_FIELDS = 4;
	public static final int NUM_PRIMARY_FIELDS = 2;
	
	LowpanNetwork simulator;
	DatagramSocket sock;
	
	
	public NetListener(LowpanNetwork sim) {
		simulator = sim;
		try {
			sock = new DatagramSocket(34217);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
		
	
	public void run() {
		while(true) {
			byte[] buf = new byte[BUF_SIZE];
			DatagramPacket p = new DatagramPacket(buf, BUF_SIZE);
			try {
				sock.receive(p);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String dataString = new String(buf);
			
			String[] data = dataString.split(",");
			
			if (data.length == NUM_PRIMARY_FIELDS) { //there's no parent for this node
				simulator.addNode(data[0], Integer.parseInt(data[1]), null);
			}
			
			if (data.length != NUM_FIELDS) continue; //completely drop invalid packets
			
			simulator.addNode(data[0], Integer.parseInt(data[1]), new LowpanNode(data[2], Integer.parseInt(data[3])));
			
		}
		
	}
	
}
