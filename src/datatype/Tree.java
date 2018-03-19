package datatype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Tree<T> {

	T data;
	Set<Tree<T>> children;
	Tree<T> parent;
	
	public Tree(T rootData) { //this should only be called when creating the root, otherwise addChild will be called
		data = rootData;
		children = Collections.newSetFromMap(new ConcurrentHashMap<Tree<T>, Boolean>());
		parent = null;
	}

	
	//Recursively calls addNode working down the tree, if every single function returns false, the parent can't be found and the root function returns false
	public boolean addNode(Tree<T> nodeToAdd, T parentToLeaf) {
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
	
	public T getData() {
		return data;
	}


	public boolean addChild(Tree<T> tToAdd) {
		if (!children.contains(tToAdd)) {
//			children.remove(tToAdd);
			children.add(tToAdd);
		}
		return true;
	}
	
	public Integer getDepth() {
		if (this.children.isEmpty()) { //base case
			return 1;
		}
		
		ArrayList<Integer> nums = new ArrayList<Integer>();
		
		for (Tree<T> item : children) {
			nums.add(item.getDepth() + 1);
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
	
	public Tree<T> get(T node){
		Tree<T> rc = null;		
		
		for (Tree<T> child : children) {
			
			if (child.getData().equals(node)) { //base case
				System.out.println("Found the node");
				return child;
			}
			
			rc = child.get(node);
			if (rc != null) return rc;
		}
		return null;
	}


	public Set<Tree<T>> getChildren() {
		return children;
	}
	
	@Override
	public int hashCode() {
		return data.hashCode();
	}
	
	@Override
//	generic equals
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		else if (obj instanceof Tree)
		{
			Tree<?> other = (Tree<?>)obj;
			return (data.equals(other.getData()));//only compare the data (the name)
		}
		else
		{
			return false;
		}
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
