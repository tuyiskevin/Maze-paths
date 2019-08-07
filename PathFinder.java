/*THIS  CODE  WAS MY OWN WORK , IT WAS  WRITTEN  WITHOUT  CONSULTING  ANYSOURCES  
OUTSIDE  OF THOSE  APPROVED  BY THE  INSTRUCTOR. Kevin Tuyishime */

import java.io.*;
import java.util.*;
import java.util.Scanner;
import java.util.ArrayDeque;

/*
 * Recursive class to represent a position in a path
 */
class Position{
	public int i;     //row
	public int j;     //column
	public char val;  //1, 0, or 'X'
	
	// reference to the previous position (parent) that leads to this position on a path
	Position parent;
	
	Position(int x, int y, char v){
		i=x; j = y; val=v;
	}
	
	Position(int x, int y, char v, Position p){
		i=x; j = y; val=v;
		parent=p;
	}
	
}


public class PathFinder {
	/**
	 * 
	 * @param args -- accepts txt file with the maze
	 * @throws IOException -- throws an exception if the file has an issue
	 */
public static void main(String[] args) throws IOException {
		if(args.length<1){
			System.err.println("***Usage: java PathFinder maze_file");
			System.exit(-1);
		}
		
		char [][] maze;
		maze = readMaze(args[0]);
		printMaze(maze);
		Position [] path = stackSearch(maze);
		System.out.println("stackSearch Solution:");
		printPath(path);
		printMaze(maze);
		
		char [][] maze2 = readMaze(args[0]);
		path = queueSearch(maze2);
		System.out.println("queueSearch Solution:");
		printPath(path);
		printMaze(maze2);
	}

/**
 * VALIDATING POSITION FOR ArrayDeque METHOD
 * @param s takes the stack(searched)
 * @param p the next position you want to add
 * @return return false if p is not in s and true if otherwise
 */
public static boolean visitedA(ArrayDeque<Position> s, Position p){
		while(!s.isEmpty()){
			Position pos = s.remove();
			if (pos.i == p.i && pos.j == p.j){
				return true;
			}
		}
		return false;
	}

/**
 * VALIDATING POSITION FOR STACK METHOD
 * @param s takes the stack(searched)
 * @param p the next position you want to add
 * @return return false if p is not in s and true if otherwise
 */
public static boolean visitedS(Stack<Position> s, Position p){
		while(!s.isEmpty()){
			Position pos = s.pop();
			if (pos.i == p.i && pos.j == p.j){
				return true;
			}
		}
		return false;
}

/**
 * 
 * @param maze -input maze from a file
 * @param solution -stack for tracking the solution
 * @param searched - stack to track the visited 
 * @param row rows in the maze
 * @param col columns in the maze
 * @param current the position in a maze at a given moment
 * @param up,down,left,right position relative to @param current
 * @param path arrays of positions for the solution set
 * @return path if a solution is available and null if it'sn't
 * 
 */
	
 @SuppressWarnings("unchecked")

public static Position [] stackSearch(char [] [] maze){

		Stack <Position> solution  = new Stack<Position>();
		Stack <Position> searched  = new Stack<Position>();
		Position current;

		//add the entrance
		solution.push(new Position(0,0,'0'));
		searched.push(new Position(0,0,'0'));

		int row = 0;
		int	col = 0;

		Position up,down,left,right;

		while(!solution.isEmpty()){
				//pop the positon for checking
				current = solution.pop();
				row = current.i;
				col = current.j;
			//check if the popped postion is exit
			if(row == maze.length-1 && col == maze.length-1 &&  maze[row][col]=='0'){
				//clear the solution stack and push in position based on parent relationship
				solution.clear();
						while(current!=null){
							solution.push(current);
							current =current.parent;
						}
				int i=0;
				
				Position [] path = new Position[solution.size()];
					//add the potions in the path array
					//replace each position in the solution stack with an 'X'
						while(!solution.isEmpty()){
							path[i]=solution.pop();
							maze[path[i].i][path[i].j]='X';
							i++;
						
					}
				//return and exit the loop
				return path;
			}


			down = new Position(row+1,col,'0', current);
			up = new Position(row-1,col,'0', current);
			right = new Position(row,col+1,'0', current);
			left = new Position(row,col-1,'0', current);
			
			//check if downwards positions is valid
			//clone the stack to avoid deleting some element
			if(visitedS((Stack<Position>)searched.clone(), down)== false && row+1>=0 && row+1<maze.length && maze[row+1][col] =='0'){
				
				searched.push(down);
				solution.push(down);
			}

			//check rightward positions is valid
			if(visitedS((Stack<Position>)searched.clone(), right)== false && col+1>=0 && col+1 <maze.length && maze[row][col+1] =='0'){
				
				searched.push(right);
				solution.push(right);
			}

			//check leftward positions is valid
			if(visitedS((Stack<Position>)searched.clone(), left)== false && col-1>=0 && col-1<maze.length && maze[row][col-1] =='0'){
				
				searched.push(left);
				solution.push(left);
			}

			//check up positions is valid
			if(visitedS((Stack<Position>)searched.clone(), up)== false && row-1>=0 && row-1 <maze.length && maze[row-1][col] =='0'){
				
				
				searched.push(up);
				solution.push(up);
			}
	}

	return null;	
	}

/**
 * Similar imprementation but using Deque this time
 * 
 * @param maze -input maze from a file
 * @param solution - ArrayDeque for tracking the solution
 * @param searched - ArrayDeque to track the visited 
 * @param row rows in the maze
 * @param col columns in the maze
 * @param current the position in a maze at a given moment
 * @param up,down,left,right position relative to @param current
 * @param path arrays of positions for the solution set
 * @return path if a solution is available and null if it'sn't
 * 
 */

	
public static Position [] queueSearch(char [] [] maze){
	
	ArrayDeque <Position> solution  = new ArrayDeque <Position>();
	ArrayDeque <Position> searched  = new ArrayDeque<Position>();
	Position current;

	solution.add(new Position(0,0,'0'));
	searched.add(new Position(0,0,'0'));

	int row = 0;
	int	col = 0;

	Position up,down,left,right;

	while(!solution.isEmpty()){

			current = solution.remove();
			row = current.i;
			col = current.j;

		if(row == maze.length-1 && col == maze.length-1 &&  maze[row][col]=='0'){
			
		solution.clear();
					while(current!=null){
						solution.add(current);
						current =current.parent;
					}
			//print the array in reverse to make sure the entrance coordinates comes first
			//replace each position in the solution Deque with an 'X'
			int i=solution.size()-1;
			Position [] path = new Position[solution.size()];
					
					while(!solution.isEmpty()){
						path[i]=solution.remove();
						maze[path[i].i][path[i].j]='X';
						i--;
					
				}
			return path;
		}


		down = new Position(row+1,col,'0', current);
		up = new Position(row-1,col,'0', current);
		right = new Position(row,col+1,'0', current);
		left = new Position(row,col-1,'0', current);
		

		if(visitedA((ArrayDeque<Position>)searched.clone(), down)== false && row+1>=0 && row+1<maze.length && maze[row+1][col] =='0'){
			
			searched.add(down);
			solution.add(down);
		}

		if(visitedA((ArrayDeque<Position>)searched.clone(), right)== false && col+1>=0 && col+1 <maze.length && maze[row][col+1] =='0'){
			
			searched.add(right);
			solution.add(right);
		}

		
		if(visitedA((ArrayDeque<Position>)searched.clone(), left)== false && col-1>=0 && col-1<maze.length && maze[row][col-1] =='0'){
			
			searched.add(left);
			solution.add(left);
		}

		if(visitedA((ArrayDeque<Position>)searched.clone(), up)== false && row-1>=0 && row-1 <maze.length && maze[row-1][col] =='0'){
			
			
			searched.add(up);
			solution.add(up);
		}
	
}
return null;
}

/**
 * @param path the path returned by the previous method
 * prints out the cordinates of each Position element in the array
 */
public static void printPath(Position [] path){
	if(path==null){
		System.out.println("!!!---Empty path---!!!");
	}else{
		System.out.print("The Path: (");
		for(int i=0; i<path.length-1; i++){
			System.out.print("["+path[i].i+"]["+path[i].j+"]");
			System.out.print(",");
		}
		
		System.out.println("["+path[path.length-1].i +"]["+path[path.length-1].j+"])\n");
	}
}

	/**
	 * Reads maze file in format:
	 * N  -- size of maze
	 * 0 1 0 1 0 1 -- space-separated 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static char [][] readMaze(String filename) throws IOException{
		char [][] maze;
		Scanner scanner;
		try{
			scanner = new Scanner(new FileInputStream(filename));
		}
		catch(IOException ex){
			System.err.println("*** Invalid filename: " + filename);
			return null;
		}
	
		int N = scanner.nextInt();
		scanner.nextLine();
		maze = new char[N][N];
		int i=0;
		while(i < N && scanner.hasNext()){
			String line =  scanner.nextLine();
			String [] tokens = line.split("\\s+");
			int j = 0;
			for (; j< tokens.length; j++){
				maze[i][j] = tokens[j].charAt(0);
			}
			if(j!=N){
				System.err.println("*** Invalid line: " + i + " has wrong # columns: " + j);
				return null;
			}
			i++;
		}

		if(i!=N){
			System.err.println("*** Invalid file: has wrong number of rows: " + i);
			return null;
		}
		
		return maze;
	}
	
	public static void printMaze(char[][] maze){
		
		if(maze==null || maze[0] == null){
			System.err.println("*** Invalid maze array");
			return;
		}
		
		for(int i=0; i< maze.length; i++){
			for(int j = 0; j< maze[0].length; j++){
				System.out.print(maze[i][j] + " ");	
			}
			System.out.println();
		}
		
		System.out.println();
	}

}
