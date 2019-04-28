package my_package;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
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
	private ArrayList<Node> expanded;
	//could have diff data structs dep on algo
	//let's do queue for now (BFS)
	//Can do PQ for otherst
	private Queue<Node> fringe;  
	private int num_expanded;
	
	private TreeStruct tree;


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


	//methods

	//method that calculates the number things (?)
	//converts things to nums to calculate Manhattan
	//changes numbers around I guess 
	//just solver of everything in general

	//different algorithm methods 

	


}
