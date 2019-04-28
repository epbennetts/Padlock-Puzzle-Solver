package my_package;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;


public class ThreeDigits {

	public static final int MAX_EXPANDED = 1000;

	//what's best? static, private, private static, protected....
	private static char alg;
	private static File doc;
	private static Scanner scan; 

	private static int[] start, goal;
	//2D array vv
	private static int[][] forbidden;
	private Queue<Node> expanded; //FIFO
	//could have diff data structs dep on algo
	//let's do queue for now (BFS)
	//Can do PQ for others
	private Queue<Node> fringe;  
	private static int num_expanded;
	
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
		
		
		
		solve(alg);
		//...
		
//		//Note: How to generate and print children	
//				ArrayList<Node> children =  generateChildren(root);
//				ArrayList<Node> chil =  generateChildren(children.get(0));		
//				
//				//want to print chil (test)
//				for (int i = 0; i < chil.size(); i++) {
//					int[] digt = chil.get(i).getDigits(); 
//					String chil_str = Arrays.toString(digt).replaceAll("\\[|\\]|,| |\\s", "");
//					System.out.print(chil_str);
//					if (i < chil.size() -1) {
//					System.out.print(",");
//					}
//				}
//		
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
	
	//public static? Also, not nec much point in having separate void method (could return sth to check if works) 
	public static void solve(char algo) {
		if (alg == 'B') {
			//if solve not void can equate it to var that we can use
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


	public static void solve_BFS() {
		while (num_expanded < MAX_EXPANDED) {
		
			num_expanded++;
		}

	}



	public static void solve_DFS() {
		while (num_expanded < MAX_EXPANDED) {
			
			num_expanded++;
		}
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


	//put here for now
	private static ArrayList<Node> generateChildren(Node node) {
		int[] digits = node.getDigits();
		int last_changed = node.lastChanged();

		//assumes parent null thing works		
		//i.e. if root (depth=0), then last_changed == -1

		//consider making the children in the main program and having a .add() function -why again?
		//issue: We're making an arraylist thing but we're not actually "assigning" children to Node
		ArrayList<Node> children = new ArrayList<Node>();
		int[] dig_temp = new int[3];
		for (int x = 0; x < 3; x++) {
			if (x != last_changed) {
				dig_temp = digits.clone();
				if (dig_temp[x] != 0) {
					dig_temp[x] = dig_temp[x]-1;
					Node child = new Node(node, dig_temp, x);
					children.add(child);
					for (int i = 0; i < 3; i++) {
						//System.out.println(dig_temp[i]);
					}
					//System.out.println("");
				}
				dig_temp = digits.clone();
				if (dig_temp[x] != 9) {
					dig_temp[x] = dig_temp[x]+1;
					Node child = new Node(node, dig_temp, x);
					children.add(child);
					for (int i = 0; i < 3; i++) {
						//System.out.println(dig_temp[i]);
					}
					//System.out.println("");
				} 			
			}
		}
		node.setChildren(children);
		return children;		
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
	
