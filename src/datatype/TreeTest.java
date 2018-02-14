package datatype;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ctrl.LowpanNetwork;

class TreeTest {

	@Test
	void test() {
		LowpanNetwork network = new LowpanNetwork();
		assertEquals(network.addNode("root", 256, null), true);
		network.getNetwork().printNode();
		assertEquals(network.addNode("child1", 1024, new LowpanNode("root", 256)), true);
		network.getNetwork().printNode();
		assertEquals(network.addNode("child2", 1024, new LowpanNode("root", 256)), true);
		network.getNetwork().printNode();
		assertEquals(network.addNode("child3", 1792, new LowpanNode("child2", 1024)), true);
		for (Tree<LowpanNode> child : network.getNetwork().getChildren()){
			child.printNode();
		}
		assertEquals(network.addNode("child1", 1024, null), true);
		for (Tree<LowpanNode> child : network.getNetwork().getChildren()){
			child.printNode();
		}
	}

}
