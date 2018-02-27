package ctrl;

import datatype.LowpanNode;
import datatype.Tree;
import ui.NodeFrame;

public class ElementExpirer extends Thread {
	
	public static int NET_REFRESH_INTERVAL = 1000;
	
	private LowpanNetwork network;
	private NodeFrame frame;
	
	public ElementExpirer(LowpanNetwork network, NodeFrame frame) {
		this.network = network;
		this.frame = frame;
	}
	
	public void expireElement(Tree<LowpanNode> tree) { //Every element below tree is added to orphanList
		
		for (Tree<LowpanNode> child : tree.getChildren()) {
			expireElement(child);//recursively expire to the base of the tree
		}
		
		network.addOrphan(tree.getData()); //for the base case, it will be removed immediately after. It makes the code more readable
	}
	
	
	//Checks every element and expires and then removes it if it's expired
	public void checkRemoveElements(Tree<LowpanNode> tree) {
		if (tree.getData().isExpired()) {
			System.out.println("Element " + tree.getData() + " has expired");
			expireElement(tree);
			network.removeNode(tree.getData());
		}
		for (Tree<LowpanNode> child : tree.getChildren()) {
			checkRemoveElements(child);
		}
	}
	
	public void checkRemoveOrphans(LowpanNetwork network) {
		//orphans have expiry dates too, not just mayonnaise
		for (LowpanNode child : network.getOrphans()) {
			if (child.isExpired()) {
				network.getOrphans().remove(child);
			} 
		}
	}
	
	public void run() {
		while(true) {
			frame.update();
			try {
				Thread.sleep(NET_REFRESH_INTERVAL);
			} catch (InterruptedException e) {
				//not actually dealing with this - the java way
				e.printStackTrace();
			}
			
			checkRemoveOrphans(network);
			
			if (network.getNetwork() == null) continue; //wait until there's elements
			checkRemoveElements(network.getNetwork());
			
		}
	}

}
