package my_package;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class ThreeDigits {

	public static final int MAX_EXPANDED = 1000;

	//what's best? static, private, private static, protected....
	private static char alg;
	private static File doc;
	private static Scanner scan; 

	private static String start, goal;
	private static String[] forbidden;
	private ArrayList<Node> expanded;
	private DataStructure fringe;  //could have diff ones dep on algo
	private int num_expanded;
	
	private TreeStruct tree;


	public static void main(String[] args){
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
			start = scan.nextLine();
			goal = scan.nextLine();

			if (scan.hasNextLine()) {

				String forb = scan.nextLine();
				String[] forbidden = forb.split(",");
				//for loop to convert each string array element into number(digit)
			}
		} 
		catch (FileNotFoundException exc) {
			exc.printStackTrace();
		}
//...

		
	}

//could make method .stringToDigit() to convert start, goal and all forbidden
//	public int[] getDigits() {
//	int[] digits_num = new int[3];
//	String[] digits_str = data.split(""); 
//	//assumes 3 digits
//	for (int i = 0; i < 3; i++) {
//		digits[i] = Integer.parseInt(digits_str[i]);
//	}
//	return digits;
//}


	//methods

	//method that calculates the number things (?)
	//converts things to nums to calculate Manhattan
	//changes numbers around I guess 
	//just solver of everything in general

	//different algorithm methods 

	


}
