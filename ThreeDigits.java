//Done: made heuristic(), changed generateChildren() to include heuristic, changed instances of pop in BFS to poll 
//(happened bc of copy paste), implemented Greedy Search, tested lightly with print statements
//ToDo:other algos, pass students test cases

package eper8035;
import java.io.*;
import java.util.*;
import java.lang.Math;


/**
 * @author elobe
 *
 */
//account for exceptions in incorrect input (eg no input in file, wrong things, etc.)
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
	private static int order;
	
	//private static TreeStruct tree;
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
		
		root = new Node(null, start, -1, heuristic(start), 0);
		
		
		solve(alg);
//		
//		if (goal_found) {
//			//print things
//		}
//		else {
//			//print other things 
//			//or could print same and change what it prints in methods)
//		}
		
		
		
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
		
		//make sure this is right constraint. <=?	
		while (num_expanded < MAX_EXPANDED) {
		
		//check if goal
		if (isGoal(curr)) {
			//print things? or later
			//save/make things for printing
			
			goal_found = true;
			expanded.add(curr);			
			num_expanded++;
			break;
		}
		//check if this node already expanded
		for (Node otherNode : expanded) {
			if (curr.equals(otherNode)) {
				//will delete from fringe, don't expand
				//need to continue from OUTER loop!!
				curr.setVisited(true);
			}				
		}
		//if already visited, remove from fringe, 
		//don't add to expanded and skip this iteration
		if (curr.visited()) {
			//exception
			try {
				//this said pop. changed to poll()
			curr = fringe.poll();
			}
			catch (Exception E) {
				//stack is empty
				break;
			}
			continue;
		}
		//maybe should make expanded and children same data structure?
		ArrayList<Node> ch = generateChildren(curr);
		expanded.add(curr);
		num_expanded++;
		//add children to fringe
		if (!ch.isEmpty()) {
		fringe.addAll(ch);//adds to tail
		}
		//don't even need this
		if (!fringe.isEmpty()) {
			curr = fringe.poll();
		} 
		else {//didn't find goal
			//print things, goal not found, etc.
			//something
			break;
		}
	}

		//could do expanded.last or sth to be on the safe side but probs ok
		//not working
		if (goal_found) {
		printPath(curr);
		}
		else {
			System.out.println("No solution found.");
		}
		printExp(expanded);
		
		}

	


	//^^^ BFS
	//
	//
	//
	//vvv DFS
	
	
	
	
	
	
	
	
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
		
		//make sure this is right constraint. <=?	
		while (num_expanded < MAX_EXPANDED) {
			//String str = Arrays.toString(curr.getDigits());
			//do later
			//printList(fringe, 'f');
			//printList(expanded, 'e');
		//check if goal
		if (isGoal(curr)) {
			//print things? or later
			//save/make things for printing
			//System.out.println("goal found");
			goal_found = true;
			expanded.add(curr);
			num_expanded++;
			break;
		}
		//check if this node already expanded
		for (Node otherNode : expanded) {
			if (curr.equals(otherNode)) {
				//will delete from fringe, don't expand
				//need to continue from OUTER loop
				curr.setVisited(true);
			}				
		}
		//if already visited, remove from fringe, 
		//don't add to expanded and skip this iteration
		if (curr.visited()) {
			//exception
			try {
			curr = fringe.pop();
			}
			catch (Exception E) {
				//stack is empty
				break;
			}
			continue;
		}
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
		if (!fringe.isEmpty()) {
			curr = fringe.pop();
		} 
		else {//didn't find goal
			//print things, goal not found, etc.
			//something
			//
			break;
		}
	}
		//could do expanded.last or sth to be on the safe side but probs ok
		//not working
		if (goal_found) {
		printPath(curr);
		}
		else {
			System.out.println("No solution found.");
		}
		printExp(expanded);				
	}
	
	
	
	
	//^^^ DFS
	//
	//
	//
	//vvv IDS	
	
	
	
	

	public static void solve_IDS() {
		//gen plan
		//this is just DFS at different depths:
		//could loop through children and not add them to fringe if depth higher than d
		//or have diff method for children
		
//		either
//		if child.depth() > depth 
//			then don't add 
//		
//		or 
//		loop through already gen children 
//		if child.depth() > depth 
//			then don't add to fringe
		
		//START
		LinkedList<Node> expanded = new LinkedList<Node> ();	
		goal_found = false;
		num_expanded = 0;

		//iterate through depths until we find the goal 
		for (int depth = 0; depth < 1001; depth++) {
			expanded.addAll(sub_DLS(depth));
			//actually can do all of the printing and stuff from there 
			if (goal_found) {
				break;
			}
		}		
		
		if (goal_found) {
			printPath(expanded.getLast());
			printExp(expanded);	
		} else {
			System.out.println("No path found.");
			printExp(expanded);	
		}
			
	}

	
	//^^^ IDS
	//
	//
	//
	//vvv DLS
	
	
	
	
	public static LinkedList<Node> sub_DLS(int max_depth) {
		Stack<Node> fringe = new Stack<Node> ();
		LinkedList<Node> expanded = new LinkedList<Node>();
		Node curr = root;
		
		//make sure this is right constraint. <=?	
		while (num_expanded < MAX_EXPANDED) {
			//String str = Arrays.toString(curr.getDigits());
			//do later
			//printList(fringe, 'f');
			//printList(expanded, 'e');
		//check if goal
		if (isGoal(curr)) {
			//print things? or later
			//save/make things for printing
			//System.out.println("goal found");
			goal_found = true;
			expanded.add(curr);
			num_expanded++;
			break;
		}
		//check if this node already expanded
		for (Node otherNode : expanded) {
			if (curr.equals(otherNode)) {
				//will delete from fringe, don't expand
				//need to continue from OUTER loop
				curr.setVisited(true);
			}				
		}
		//if already visited, remove from fringe, 
		//don't add to expanded and skip this iteration
		if (curr.visited()) {
			//exception
			try {
			curr = fringe.pop();
			}
			catch (Exception E) {
				//stack is empty
				break;
			}
			continue;
		}
		//maybe should make expanded and children same data structure?
		ArrayList<Node> ch = generateChildren(curr);
		expanded.add(curr);
		num_expanded++;
		//add children to fringe
		Stack<Node> reverse = new Stack<Node>();
		if (!ch.isEmpty()) {
			boolean omitted = false;
			for (Node child : ch) {
				if (child.getDepth() > max_depth) {
				omitted = true;
				}
				else {
					reverse.push(child);//adds to top of stack
				}
			}
			for (int i = 0; i < ch.size(); i++) {
				if (!omitted) {			
				fringe.push(reverse.pop());//adds to top of stack
				}
			}
		}
		
		if (!fringe.isEmpty()) {
			//exception when we did if fringe.pop() != null
			curr = fringe.pop();
		} 
		else {//didn't find goal
			//print things, goal not found, etc.
			//something
			//
			break;
		}
	}
		//could do expanded.last or sth to be on the safe side but probs ok
		//not working
					
		return expanded;
	}
	
	
	
	
	//^^^ DLS
	//
	//
	//
	//vvv GREEDY
		



	

	public static void solve_Greedy() {
		//what things to check for?
		//num of nodes, whether any of them are in expanded, whether forbidden...
		LinkedList<Node> expanded = new LinkedList<Node> ();
		Comparator<Node> comparator = new GreedyComparator();
		PriorityQueue<Node> fringe = new PriorityQueue<Node> (10, comparator);

		num_expanded = 0;
		goal_found = false;
		Node curr = root;
		order = 0;

		//make sure this is right constraint. <=?	
		while (num_expanded < MAX_EXPANDED) {
			//test//
			System.out.println("node heuristic is " + heuristic(curr.getDigits()));
			System.out.println("node order is " + curr.getOrder());
			System.out.println("Current node: " + Arrays.toString(curr.getDigits()));
			printPQ(fringe, 'f');
			printList(expanded, 'e');
			//

			//check if goal
			if (isGoal(curr)) {
				//print things? or later
				//save/make things for printing

				goal_found = true;
				expanded.add(curr);			
				num_expanded++;
				break;
			}
			//check if this node already expanded
			for (Node otherNode : expanded) {
				if (curr.equals(otherNode)) {
					//will delete from fringe, don't expand
					//need to continue from OUTER loop!!
					curr.setVisited(true);
				}				
			}
			//if already visited, remove from fringe, 
			//don't add to expanded and skip this iteration
			if (curr.visited()) {
				//exception
				try {
					//this said pop. changed to poll()
					curr = fringe.poll();
				}
				catch (Exception E) {
					//stack is empty
					break;
				}
				continue;
			}
			//maybe should make expanded and children same data structure?
			ArrayList<Node> ch = generateChildren(curr);
			expanded.add(curr);
			num_expanded++;
			//add children to fringe
			if (!ch.isEmpty()) {
				fringe.addAll(ch);//adds to tail
			}
			//don't even need this
			if (!fringe.isEmpty()) {
				curr = fringe.poll();
			} 
			else {//didn't find goal
				//print things, goal not found, etc.
				//something
				break;
			}
		}

		//could do expanded.last or sth to be on the safe side but probs ok
		//not working
		if (goal_found) {
			//testing//
			System.out.println("goal found");
			//
			printPath(curr);
		}
		else {
			System.out.println("No solution found.");
		}
		printExp(expanded);		
	}

	
	
	
	
	
	
	
	
	
	public static void solve_AStar() {
//		//what things to check for?
//		//num of nodes, whether any of them are in expanded, whether forbidden...
//		LinkedList<Node> expanded = new LinkedList<Node> ();
//		Comparator<Node> comparator = new GreedyComparator();
//		PriorityQueue<Node> fringe = new PriorityQueue<Node> (10, comparator);
//
//		num_expanded = 0;
//		goal_found = false;
//		Node curr = root;
//		order = 0;
//
//		//make sure this is right constraint. <=?	
//		while (num_expanded < MAX_EXPANDED) {
//			//test//
//			System.out.println("node heuristic is " + heuristic(curr.getDigits()));
//			System.out.println("node order is " + curr.getOrder());
//			System.out.println("Current node: " + Arrays.toString(curr.getDigits()));
//			printPQ(fringe, 'f');
//			printList(expanded, 'e');
//			//
//
//			//check if goal
//			if (isGoal(curr)) {
//				//print things? or later
//				//save/make things for printing
//
//				goal_found = true;
//				expanded.add(curr);			
//				num_expanded++;
//				break;
//			}
//			//check if this node already expanded
//			for (Node otherNode : expanded) {
//				if (curr.equals(otherNode)) {
//					//will delete from fringe, don't expand
//					//need to continue from OUTER loop!!
//					curr.setVisited(true);
//				}				
//			}
//			//if already visited, remove from fringe, 
//			//don't add to expanded and skip this iteration
//			if (curr.visited()) {
//				//exception
//				try {
//					//this said pop. changed to poll()
//					curr = fringe.poll();
//				}
//				catch (Exception E) {
//					//stack is empty
//					break;
//				}
//				continue;
//			}
//			//maybe should make expanded and children same data structure?
//			ArrayList<Node> ch = generateChildren(curr);
//			expanded.add(curr);
//			num_expanded++;
//			//add children to fringe
//			if (!ch.isEmpty()) {
//				fringe.addAll(ch);//adds to tail
//			}
//			//don't even need this
//			if (!fringe.isEmpty()) {
//				curr = fringe.poll();
//			} 
//			else {//didn't find goal
//				//print things, goal not found, etc.
//				//something
//				break;
//			}
//		}
//
//		//could do expanded.last or sth to be on the safe side but probs ok
//		//not working
//		if (goal_found) {
//			//testing//
//			System.out.println("goal found");
//			//
//			printPath(curr);
//		}
//		else {
//			System.out.println("No solution found.");
//		}
//		printExp(expanded);	
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
		
		//test
		System.out.println("generating children");
		//
		ArrayList<Node> children = new ArrayList<Node>();
		int[] dig_temp = new int[3];
		for (int x = 0; x < 3; x++) {
			if (x != last_changed) {
				dig_temp = digits.clone();
				if (dig_temp[x] != 0) {
					dig_temp[x] = dig_temp[x]-1;
					//for testing
					String a = Arrays.toString(dig_temp);
					//
					if (isForbidden(dig_temp)) {
						//do nothing(?)
						System.out.println(a + " is forbidden");
					} 
					else {
						order++;
						Node child = new Node(node, dig_temp, x, heuristic(dig_temp), order);
						children.add(child);
						//testing//
						System.out.println(child.getOrder() + a);
						System.out.println("");
						//
					}
				}
				dig_temp = digits.clone();
				if (dig_temp[x] != 9) {
					dig_temp[x] = dig_temp[x]+1;
					//for testing
					String a = Arrays.toString(dig_temp);
					//
					if (isForbidden(dig_temp)) {
						//do nothing(?)
						System.out.println(a+ " is forbidden");						
					} 
					else {
						order++;
						Node child = new Node(node, dig_temp, x, heuristic(dig_temp), order);
						children.add(child);
						//testing//
						System.out.println(child.getOrder() + a);
						System.out.println("");
						//
					}
				} 			
			}
		}
		//test//
		//System.out.println("end of children");
		//
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
	
	
	//for testing
	public static void printPQ(PriorityQueue<Node> fringe, char ch) {
		if (ch == 'f') {
			System.out.print("fringe: ");
		}
		else {
			System.out.print("expanded: ");
		}
		//case if goal not found?
		
		for (Node node : fringe) {
			int[] digt = node.getDigits(); 
			String digt_str = Arrays.toString(digt).replaceAll("\\[|\\]|,| |\\s", "");
			System.out.print(digt_str);
			System.out.print(",");
		}
		System.out.println("");
	}
	
	
	//better to return int or Integer? For ordering DS
	public static int heuristic (int[] digits) {
		Integer heuristic = 0;
		
		for (int i = 0; i < 3; i++) {
			heuristic += Math.abs(digits[i]-goal[i]); 
		}
		int h = heuristic.intValue();
		//node.setHeuristic(h);
		return h; 
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

	
