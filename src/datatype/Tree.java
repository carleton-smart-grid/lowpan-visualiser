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
		boolean recursiveReturn = false;
		for (Tree<T> item : children) {
			if (item.getData().equals(parentToLeaf)) { //base case, item is the parent
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
		if (children.contains(nodeToRemove)) {
			children.remove(nodeToRemove);
			return true;
		}
		
		boolean rc = false;
		
		for (Tree<T> child : children) {
			rc = removeNode(nodeToRemove);
			if (rc) return true;
		}
		
		return false;
		
	}


	public HashSet<Tree<T>> getChildren() {
		return children;
	}
	
	public void printNode() {
		int numChildren = children.size();
		System.out.println(data);
		for (Tree<T> child : children) {
			System.out.print(child);			
		}
		System.out.println("");
	}
	
}
