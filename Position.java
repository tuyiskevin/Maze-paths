import java.util.*;
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

class Test{
        public static void main(String[] args) {
                Position start = new Position(1, 3, 'b');
		Position s = new Position(4, 4, 'x', start);
		Position w = new Position(4, 4, 'x', start);

		Stack <Position> e = new Stack<Position>();
		e.push(start);
		e.push(s);
		e.push(w);
		System.out.println(e.pop() instanceof Position);
		while(!e.isEmpty()){
			Position c = e.pop();

			System.out.println(c.val);
		}

	}
}