//Did: Created comparator

package eper8035;
import java.util.*;

public class GreedyComparator implements Comparator<Node>{
	@Override
	public int compare(Node n1, Node n2) {
		//compares heuristic first
		//if heuristic is higher, put after
		//if heuristic is lower, put before
		//if heuristic is the same, compare order of exp
		//if expanded after (order higher), then put before
		
		if (n1.getHeuristic() < n2.getHeuristic()) {
			return -1;
		}
		else if (n1.getHeuristic() > n2.getHeuristic()) {
			return 1;
		} 
		else if (n1.getHeuristic() == n2.getHeuristic()) {
			
			if (n1.getOrder() < n2.getOrder()) {
				return 1;
			}
			else if (n1.getOrder() > n2.getOrder()) {
				return -1;
			}
			
		}
			return 0;
	}
	

}
