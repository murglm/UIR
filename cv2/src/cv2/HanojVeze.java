package cv2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class HanojVeze {
	
	private static LinkedList<Node> list;
	
	public static void main(String [] args) {
		Stack<Integer> A = new Stack<Integer>();
		Stack<Integer> B = new Stack<Integer>();
		Stack<Integer> C = new Stack<Integer>();
		
		int prvni = 1;
		int druhy = 2;
		int treti = 3;
		
		A.push(treti);
		A.push(druhy);	
		A.push(prvni);	
		
		Node node = new Node(A, B, C);
		
		//dfs(node);
		bfs(node);
		
		list = new LinkedList<Node>();
		
		
		
	}

	
	public static void dfs(Node node) {
		if (node.A.isEmpty() && node.B.isEmpty()) {
			System.out.println("Nalezeno!");
		}
		else {
		if(isPossible(node.A, node.B)) {
			Node node1;
			node1 = node;
			node1.B.push(node1.A.pop());
			dfs(node1);
			//node.potomci.add(node1);
			//list.add(node1);
		}
		if(isPossible(node.A, node.C)) {
			Node node1;
			node1 = node;
			node1.C.push(node1.A.pop());
			dfs(node1);
			//node.potomci.add(node1);
			//list.add(node1);
		}
		if(isPossible(node.B, node.A)) {
			Node node1;
			node1 = node;
			node1.A.push(node1.B.pop());
			dfs(node1);
			//node.potomci.add(node1);
			//list.add(node1);
		}
		if(isPossible(node.B, node.C)) {
			Node node1;
			node1 = node;
			node1.C.push(node1.B.pop());
			dfs(node1);
			//node.potomci.add(node1);
			//list.add(node1);
		}
		if(isPossible(node.C, node.B)) {
			Node node1;
			node1 = node;
			node1.B.push(node1.C.pop());
			dfs(node1);
			//node.potomci.add(node1);
			//list.add(node1);
		}
		if(isPossible(node.C, node.A)) {
			Node node1;
			node1 = node;
			node1.A.push(node1.C.pop());
			dfs(node1);
			//node.potomci.add(node1);
			//list.add(node1);
		}
		}
		
	}
	
	public static void bfs(Node node) {
		if (node.A.isEmpty() && node.B.isEmpty()) {
			System.out.println("Nalezeno!");
		}
		else {
		if(isPossible(node.A, node.B)) {
			Node node1;
			node1 = node;
			node1.B.push(node1.A.pop());
			node.potomci.add(node1);
			//list.add(node1);
		}
		if(isPossible(node.A, node.C)) {
			Node node1;
			node1 = node;
			node1.C.push(node1.A.pop());
			node.potomci.add(node1);
			//list.add(node1);
		}
		if(isPossible(node.B, node.A)) {
			Node node1;
			node1 = node;
			node1.A.push(node1.B.pop());
			node.potomci.add(node1);
			//list.add(node1);
		}
		if(isPossible(node.B, node.C)) {
			Node node1;
			node1 = node;
			node1.C.push(node1.B.pop());
			node.potomci.add(node1);
			//list.add(node1);
		}
		if(isPossible(node.C, node.B)) {
			Node node1;
			node1 = node;
			node1.B.push(node1.C.pop());
			node.potomci.add(node1);
			//list.add(node1);
		}
		if(isPossible(node.C, node.A)) {
			Node node1;
			node1 = node;
			node1.A.push(node1.C.pop());
			node.potomci.add(node1);
			//list.add(node1);
		}
		for (int i = 0; i < node.potomci.size(); i++) {
			bfs(node.potomci.get(i));
		}
		}
		
	}
	static boolean isPossible(Stack<Integer> X, Stack<Integer> Y) {
		if (X.isEmpty()) return false;
		if (!Y.isEmpty()) {
		if (X.peek() > Y.peek()) return false;
		}
		return true;
	}
}

	class Node {
		
		Stack<Integer> A;
		Stack<Integer> B;
		Stack<Integer> C;
		ArrayList<Node> potomci = new ArrayList<>();
		
		public Node(Stack<Integer> A, Stack<Integer> B,Stack<Integer> C) {
			this.A = A;
			this.B = B;
			this.C = C;
		}
		
		
		
		}