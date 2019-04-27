package my_package;
import java.io.*;
import java.util.Scanner;

public class ThreeDigits {

	public static final int MAX_EXPANDED = 1000;

	//what's best? static, private, private static, protected....
	private static char alg;
	private static File doc;
	private static Scanner scan; 

	private static String start, goal;
	private static String[] forbidden;


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

				//				Not nec, more useful to have this as String
				//				forbibbi = new int[forb_array.length];				
				//				for (int i=0; i < forb_array.length; i++) {
				//					forbidden[i] = Integer.parseInt(forb_array[i]);					
				//				}
			}
		} 
		catch (FileNotFoundException exc) {
			exc.printStackTrace();
		}


	}



	//methods

	//method that calculates the number things (?)
	//converts things to nums to calculate Manhattan
	//changes numbers around I guess 
	//just solver of everything in general

	//different algorithm methods 



}