//Done: implemented DFS
//ToDo: check DFS for errors, first submission, other algos

package my_package_new;
import java.io.*;
import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;


/**
 * @author elobe
 *
 */
public class ThreeDigits {

	public static final int MAX_EXPANDED = 1000;

	//what's best? static, private, private static, protected....
	private static char alg;
	private static File doc;
	private static Scanner scan; 

	private static int[] start, goal;
	private static int[][] forbidden;
	//private Queue<Node> expanded; //FIFO
	//private Queue<Node> fringe;//diff data structs for fringe dep on algo
	private static int num_expanded;
	private static boolean goal_found;
	
	private static TreeStruct tree;
	private static Node root;

	public static void main(String[] args){
		String start_str;
		String goal_str; 
		
		//num args
		if (args.length != 2){
			System.out.printf("No solution found.\n");
			return;
		} 		

		//parses the 2 args
		//assumes input is in correct format
		//!could use try catch if parsing doesn't work
		//!handle exceptions
		alg = args[0].charAt(0);
		String filename = args[1];

		try {

			doc = new File(filename);
			scan = new Scanner(doc);		

			//!check if the going to next line and stuff works properly
			start_str = scan.nextLine();
			start = strToDigits(start_str);
			goal_str = scan.nextLine();
			goal = strToDigits(goal_str);

			if (scan.hasNextLine()) {
				String forb = scan.nextLine();
				String[] forb_arr = forb.split(",");
				//for loop to convert each string array element into number(digit)
				//assumes 3 digits
				int[] dig = new int[3];
				forbidden = new int[forb_arr.length][3];
				for (int i = 0; i < forb_arr.length; i++) {
					dig = strToDigits(forb_arr[i]); 
					forbidden[i] = dig;					
				}
				
			}
		} 
		catch (FileNotFoundException exc) {
			exc.printStackTrace();
		}	
		
		root = new Node(null, start, -1);
		tree = new TreeStruct(root); //does nothing tbh
		
		
		solve(alg);
		
		if (goal_found) {
			//print things
		}
		else {
			//print other things 
			//or could print same and change what it prints in methods)
		}
		
		
		
		//...
		
//		//Note: How to generate and print children	
//				ArrayList<Node> children =  generateChildren(root);
//				ArrayList<Node> chil =  generateChildren(children.get(0));		
//				
//				//want to print chil (test)
//				for (int i = 0; i < chil.size(); i++) {
//				}
		
//		//Alternative (better) method, with inbuilt functions: How to generate and print children	
//		generateChildren(root);
//		ArrayList<Node> chil =  generateChildren(root.getChildren().get(0));		
//		
//		//want to print chil (test)
//		for (int i = 0; i < chil.size(); i++) {
//			int[] digt = chil.get(i).getDigits(); 
//			String chil_str = Arrays.toString(digt).replaceAll("\\[|\\]|,| |\\s", "");
//			System.out.print(chil_str);
//			if (i < chil.size() -1) {
//			System.out.print(",");
//			}
//		}
		
	}
	
	
	
	//public static? Also, not nec much point in having separate void method (could return sth to check if works) 
	public static void solve(char algo) {
		if (alg == 'B') {
			//if solve not void can equate it to var that we can use, like boolean
			solve_BFS();				
		} 
		else if (alg == 'D') {
			solve_DFS();
		} 
		else if (alg == 'I') {
			solve_IDS();
		} 
		else if (alg == 'G') {
			solve_Greedy();
		} 
		else if (alg == 'A') {
			solve_AStar();
		} 
		else if (alg == 'H') {
			solve_HillClimbing();
		}
		
		//Anything else? ...		
	}

	//START
	//still have to do case where we don't find the node!!
	//there's case where we haven't expanded everything (1000) but no more in fringe
	public static void solve_BFS() {
		//what things to check for?
		//num of nodes, whether any of them are in expanded, whether forbidden...
		LinkedList<Node> expanded = new LinkedList<Node> ();
		LinkedList<Node> fringe = new LinkedList<Node> ();
		
		num_expanded = 0;
		goal_found = false;
		//fringe.add(root);
		Node curr = root;
		
		System.out.println("164");
		//make sure this is right constraint. <=?	
		while (num_expanded < MAX_EXPANDED) {
			String str = Arrays.toString(curr.getDigits());
			System.out.println("Current is :" + str);
			printList(fringe, 'f');
			printList(expanded, 'e');
			System.out.println("167");
		//check if goal
		if (isGoal(curr)) {
			//print things? or later
			//save/make things for printing
			System.out.println("goal found");
			goal_found = true;
			expanded.add(curr);
			System.out.println("175");
			num_expanded++;
			break;
		}
		System.out.println("178");
		//check if this node already expanded
		for (Node otherNode : expanded) {
			//System.out.println("181");
			if (curr.equals(otherNode)) {
				//delete 2 lines
				String curr_str = Arrays.toString(curr.getDigits());
				String other_str = Arrays.toString(otherNode.getDigits());
				System.out.println("183");
				//will delete from fringe, don't expand
				//need to continue from OUTER loop!!
				System.out.println("node already visited: " + curr_str + " is same as " + other_str);
				curr.setVisited(true);
			}				
		}
		System.out.println("189");
		//if already visited, remove from fringe, 
		//don't add to expanded and skip this iteration
		if (curr.visited()) {
			System.out.println("visited, so remove from fringe");
			System.out.println("193");
			curr = fringe.poll();
			continue;
		}
		System.out.println("196");
		//maybe should make expanded and children same data structure?
		ArrayList<Node> ch = generateChildren(curr);
		expanded.add(curr);
		num_expanded++;
		//add children to fringe
		if (!ch.isEmpty()) {
		fringe.addAll(ch);//adds to tail
		}
		//don't even need this
		if (num_expanded < 2) {
			curr = fringe.poll();
			System.out.println("num_exp = 1 (i.e. this is root), poll fringe anyway");
		}
		else if (fringe.peek() != null) {
			System.out.println("fringe not null");
			System.out.println("203");
			curr = fringe.poll();
		} 
		else {//didn't find goal
			//print things, goal not found, etc.
			//something
			//
			System.out.println("goal not found");
			System.out.println("210");
			break;
		}
		System.out.println("nodes expanded: " + num_expanded);
	}
		
		System.out.println("215");
		//could do expanded.last or sth to be on the safe side but probs ok
		//not working
		System.out.println("output: path, expanded");
		if (goal_found) {
		printPath(curr);
		}
		else {
			System.out.println("No solution found.");
		}
		printExp(expanded);
		
		}

	


	
	
	
	
	
	
	
	
	//skeleton from BFS
	//same but fringe is a stack
	public static void solve_DFS() {
		//what things to check for?
		//num of nodes, whether any of them are in expanded, whether forbidden...
		LinkedList<Node> expanded = new LinkedList<Node> ();
		Stack<Node> fringe = new Stack<Node> ();
		
		num_expanded = 0;
		goal_found = false;
		Node curr = root;
		
		System.out.println("164");
		//make sure this is right constraint. <=?	
		while (num_expanded < MAX_EXPANDED) {
			String str = Arrays.toString(curr.getDigits());
			System.out.println("Current is :" + str);
			//do later
			//printList(fringe, 'f');
			printList(expanded, 'e');
			System.out.println("167");
		//check if goal
		if (isGoal(curr)) {
			//print things? or later
			//save/make things for printing
			System.out.println("goal found");
			goal_found = true;
			expanded.add(curr);
			System.out.println("175");
			num_expanded++;
			break;
		}
		System.out.println("178");
		//check if this node already expanded
		for (Node otherNode : expanded) {
			//System.out.println("181");
			if (curr.equals(otherNode)) {
				//delete 2 lines
				String curr_str = Arrays.toString(curr.getDigits());
				String other_str = Arrays.toString(otherNode.getDigits());
				System.out.println("183");
				//will delete from fringe, don't expand
				//need to continue from OUTER loop!!
				System.out.println("node already visited: " + curr_str + " is same as " + other_str);
				curr.setVisited(true);
			}				
		}
		System.out.println("189");
		//if already visited, remove from fringe, 
		//don't add to expanded and skip this iteration
		if (curr.visited()) {
			System.out.println("visited, so remove from fringe");
			System.out.println("193");
			curr = fringe.pop();
			continue;
		}
		System.out.println("196");
		//maybe should make expanded and children same data structure?
		ArrayList<Node> ch = generateChildren(curr);
		expanded.add(curr);
		num_expanded++;
		//add children to fringe
		Stack<Node> reverse = new Stack<Node>();
		if (!ch.isEmpty()) {
			for (Node child : ch) {
				reverse.push(child);//adds to top of stack
			}
			for (int i = 0; i < ch.size(); i++) {
				fringe.push(reverse.pop());//adds to top of stack
			}
		}
		//don't even need this
		if (num_expanded < 2) {
			curr = fringe.pop();
			System.out.println("num_exp = 1 (i.e. this is root), poll fringe anyway");
		}
		else if (fringe.peek() != null) {
			System.out.println("fringe not null");
			System.out.println("203");
			curr = fringe.pop();
		} 
		else {//didn't find goal
			//print things, goal not found, etc.
			//something
			//
			System.out.println("goal not found");
			System.out.println("210");
			break;
		}
		System.out.println("nodes expanded: " + num_expanded);
	}
		
		System.out.println("215");
		//could do expanded.last or sth to be on the safe side but probs ok
		//not working
		System.out.println("output: path, expanded");
		if (goal_found) {
		printPath(curr);
		}
		else {
			System.out.println("No solution found.");
		}
		printExp(expanded);
		
				
	}

	public static void solve_IDS() {
		while (num_expanded < MAX_EXPANDED) {
			
			num_expanded++;
		}
	}

	public static void solve_Greedy() {
		while (num_expanded < MAX_EXPANDED) {
			
			num_expanded++;
		}
	}

	public static void solve_AStar() {
		while (num_expanded < MAX_EXPANDED) {
			
			num_expanded++;
		}
	}

	public static void solve_HillClimbing() {
		while (num_expanded < MAX_EXPANDED) {
			
			num_expanded++;
		}
	}


	private static ArrayList<Node> generateChildren(Node node) {
		int[] digits = node.getDigits();
		int last_changed = node.lastChanged();

		//assumes parent null thing works		
		//i.e. if root (depth=0), then last_changed == -1

		//consider making the children in the main program and having a .add() function -why again?
		//issue: We're making an arraylist thing but we're not actually "assigning" children to Node
		
		//have to check if forbidden!
		ArrayList<Node> children = new ArrayList<Node>();
		int[] dig_temp = new int[3];
		for (int x = 0; x < 3; x++) {
			if (x != last_changed) {
				dig_temp = digits.clone();
				if (dig_temp[x] != 0) {
					dig_temp[x] = dig_temp[x]-1;
					if (isForbidden(dig_temp)) {
						//do nothing(?)
						String a = Arrays.toString(dig_temp);
						System.out.println("forbidden, not generating child: " + a);
					} 
					else {
						Node child = new Node(node, dig_temp, x);
						children.add(child);

						//testing
						for (int i = 0; i < 3; i++) {
							System.out.println(dig_temp[i]);
						}
						System.out.println("");
						//
					}
				}
				dig_temp = digits.clone();
				if (dig_temp[x] != 9) {
					dig_temp[x] = dig_temp[x]+1;
					
					if (isForbidden(dig_temp)) {
						//do nothing(?)
						String a = Arrays.toString(dig_temp);
						System.out.println("forbidden, not generating child: " + a);
					} 
					else {
						Node child = new Node(node, dig_temp, x);
						children.add(child);

						//testing
						for (int i = 0; i < 3; i++) {
							System.out.println(dig_temp[i]);
						}
						System.out.println("");
						//
					}
				} 			
			}
		}
		node.setChildren(children);
		return children;		
	}
	
	public static boolean isGoal(Node node) {
		int[] digits = node.getDigits();
		if (Arrays.equals(digits, goal)) {
			return true;
		}
		return false;
	}
	
	public static boolean isForbidden(int[] digits) {
		//forbidden.length should give # columns
		if (forbidden == null) {
			return false;
		}
		
		for (int i = 0; i < forbidden.length; i++) {	
			if (Arrays.equals(digits, forbidden[i])) {
				return true;
			}
		}
		return false;
	}
	
	//recall purpose of static?
	public static int[] strToDigits(String str) {
		int[] num_arr = new int[3];
		String[] str_arr = str.split(""); 
		//assumes 3 digits
		for (int i = 0; i < 3; i++) {
			num_arr[i] = Integer.parseInt(str_arr[i]);
		}
		return num_arr;
	}
	
	//proper one
	public static void printExp(LinkedList<Node> expanded) {
		//case goal not found
		for (int i = 0; i < expanded.size(); i++) {
			//printint doesn't work when using poll() with Queue
			int[] digt = expanded.get(i).getDigits(); 
			String digt_str = Arrays.toString(digt).replaceAll("\\[|\\]|,| |\\s", "");
			System.out.print(digt_str);
			if (i < expanded.size() -1) {
				System.out.print(",");
			}
		}
	}
	
	public static void printPath(Node node) {
		Stack<Node> path = new Stack<Node>();
		String node_str;
		Node node_temp = node;
		
		while (node_temp != null) {
			path.push(node_temp);
			node_temp = node_temp.getParent();
		}
		
		while (!path.isEmpty()) {
			node_str = Arrays.toString(path.pop().getDigits()).replaceAll("\\[|\\]|,| |\\s", "");
			System.out.print(node_str);
			if (path.size() > 0) {
				System.out.print(",");				
			}
		}
		System.out.println("");
		
		//silly
//		while (node.getParent() != null) {
//			node_str = Arrays.toString(node.getDigits()).replaceAll("\\[|\\]|,| |\\s", "");
//			System.out.print(node_str + ",");
//			node = node.getParent();
//		}
//		String root_str = Arrays.toString(root.getDigits()).replaceAll("\\[|\\]|,| |\\s", "");
//		System.out.println(root_str);
			
	}

	//for testing
	public static void printList(LinkedList<Node> fringe, char ch) {
		if (ch == 'f') {
			System.out.print("fringe: ");
		}
		else {
			System.out.print("expanded: ");
		}
		//case if goal not found?
		
		for (int i = 0; i < fringe.size(); i++) {
			int[] digt = fringe.get(i).getDigits(); 
			String digt_str = Arrays.toString(digt).replaceAll("\\[|\\]|,| |\\s", "");
			System.out.print(digt_str);
			if (i < fringe.size() -1) {
				System.out.print(",");
			}
		}
		System.out.println("");
	}
	
	
		
}
	//methods:
	//calculate Manhattan	
	//the different algorithm methods 

	//implementations:
	//all impls are special cases of PQ
	//BFS is queue
	//DFS is stack 
	//

//Note: How to generate and print children	
//ArrayList<Node> children =  generateChildren(root);
//ArrayList<Node> chil =  generateChildren(children.get(0));		
//
////want to print chil (test)
//for (int i = 0; i < chil.size(); i++) {
//	int[] digt = chil.get(i).getDigits(); 
//	String chil_str = Arrays.toString(digt).replaceAll("\\[|\\]|,| |\\s", "");
//	System.out.print(chil_str);
//	if (i < chil.size() -1) {
//	System.out.print(",");
//	}
//}

////Alternative (better) method, with inbuilt functions: How to generate and print children	
//generateChildren(root);
//ArrayList<Node> chil =  generateChildren(root.getChildren().get(0));		
//
////want to print chil (test)
//for (int i = 0; i < chil.size(); i++) {
//int[] digt = chil.get(i).getDigits(); 
//String chil_str = Arrays.toString(digt).replaceAll("\\[|\\]|,| |\\s", "");
//System.out.print(chil_str);
//if (i < chil.size() -1) {
//System.out.print(",");
//}
//}
	
