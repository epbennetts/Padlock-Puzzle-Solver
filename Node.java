package my_package;
import java.util.*;

public class Node {
	//declare:
	//consider which of these should be static/protected/etc.

	private String data;// /number/string/whatever
	private Node parent;
	private ArrayList<Node> children;
	private ArrayList<Node> siblings;
	private int depth;
	//other
	private int last_changed; 
	//index (0,1,2) of last digit changed 
	//for 1st node (root) avoid this bit of code or set to -1 and catch exc
	
	
	//could try-catch nullpointer for root but seems clunky
	public Node(String data, Node parent, ArrayList<Node> children, 
	ArrayList<Node> siblings) {

		this.data = data;
		//see if this works
		if (parent == null) {
			depth = 0;
		}
		this.parent = parent;
		this.children = children;
		this.siblings = siblings;
		
		//the idea is to not "arbitrarily" set depth when we create node, so not input arg
		//but still having it as var so we don't have to traverse whole tree every time
		//!this.depth = parent.getDepth() = 1;
		
		//other?		
	}
	

	//methods
	
	//!decide if want to return Str of sth else 
	public String getData() {
		return data;		
	}	
	
	public Node getParent() {
		return parent;		
	}
	
	public ArrayList<Node> getChildren() {
		return children;		
	}
	
	public ArrayList<Node> getSiblings() {
		//assumes the null parent if statement works
		if (depth != 0) {
			return siblings;
		}
		else {
			return null;
		}
				
	}
	
	//static? -> not but maybe when we set it yes
	//!public int getDepth() {
	//sth like: depth = parent.depth() + 1;
	//}
	
	//could make boolean or sth?
	//!consider making appendNewChildren but prob not nec
	public void appendChild(Node newChild) {
		children.add(newChild);
		//needs to be ordered ArrayList!
	}
	
	//will probs be relevant for cycles
	//ALTHOUGH won't need this if check for cycles BEFORE adding children
	public void deleteNode() {
		//sth sth
	}
	
	public boolean equals(Node node) {
//!		if (data is same -check weird Str equals thing, mb do toInteger -  
//		AND  children are the same -ideally don't have to check order bc they are ordered already, but mb be safe)
//		{
//			return true;
//		}
		return false;
	}
	
	
	
	//.is leaf? If can't expand -> don't think this is a thing
	// what do if all children are repeated (cycles)? Go back to algo I guess
	
	
	//getAncestors -> to get path. 
	//This way don't create new one every time, but can get when nec.
	//maybe just go .getPath()
	
	
	
}
