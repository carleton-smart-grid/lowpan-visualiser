package datatype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Tree<T> {

	T data;
	HashSet<Tree<T>> children;
	Tree<T> parent;
	
	public Tree(T rootData) { //this should only be called when creating the root, otherwise addChild will be called
		data = rootData;
		children = new HashSet<Tree<T>>();
		parent = null;
	}
	
	
	//Recursively calls addNode working down the tree, if every single function returns false, the parent can't be found and the root function returns false
	public boolean addNode(T nodeToAdd, T parentToLeaf) {
//		System.out.println("Searching in " + data.toString());
		if (data.equals(parentToLeaf)) { //the root starts the chain, so this check has to be done
//			System.out.println("Found the parent, it is " + getData().toString());
			this.addChild(nodeToAdd);
			return true;
		}
		boolean recursiveReturn = false;
		for (Tree<T> item : children) {
			if (item.getData().equals(parentToLeaf)) { //base case, item is the parent
//				System.out.println("Found the parent, it is " + item.getData().toString());
				return item.addChild(nodeToAdd);
			}
			recursiveReturn = item.addNode(nodeToAdd, parentToLeaf);
			if (recursiveReturn) return true; //someone found the parent, exit
		}
		return false;
	}
	
	private T getData() {
		return data;
	}


	public boolean addChild(T nodeToAdd) {
		children.add(new Tree<T>(nodeToAdd));
		return true;
	}
	
	public Integer getDepth() {
		if (this.children.isEmpty()) { //base case
			return 1;
		}
		
		ArrayList<Integer> nums = new ArrayList<Integer>();
		
		for (Tree<T> item : children) {
			nums.add(item.getDepth());
		}
		
		return Collections.max(nums);
		
	}
	
	public boolean removeNode(T nodeToRemove) {		
		boolean rc = false;
		
		for (Tree<T> child : children) {
			if (child.getData().equals(nodeToRemove)){ //base case
				children.remove(child);
				return true;
			}
			rc = child.removeNode(nodeToRemove);
			if (rc) return true;
		}
		
		return false;
		
	}
	
	public boolean contains(T node) {
		boolean rc = false;		
		
		for (Tree<T> child : children) {
			
			if (child.getData().equals(node)) { //base case
				System.out.println("Found the node");
				return true;
			}
			
			rc = child.contains(node);
			if (rc) return true;
		}
		return false;
	}


	public HashSet<Tree<T>> getChildren() {
		return children;
	}
	
	public void printNode() {
		int numChildren = children.size();
		for	(int i = 0; i < numChildren; ++i) {
			System.out.print("Root is : \t");
		}
		System.out.println(data);
		System.out.print("Children are: " );
		for (Tree<T> child : children) {
			System.out.print(child.getData() + "\t");			
		}
		System.out.println("");
	}
	
}
