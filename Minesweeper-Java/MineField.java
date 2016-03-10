import java.util.Random;

class MineField{
	private boolean[][] mines,visible;
	private boolean boom;
	private final int rowMax = 5;
	private final int colMax = 10;

	MineField(){
		mines = new boolean[rowMax][colMax];
		visible = new boolean[rowMax][colMax];
		int counter2=15;
		// generates random mines
		int randomRow,randomCol;
		Random RGenerator=new Random();
		while(counter2>0){
			randomRow=Math.abs(RGenerator.nextInt()%rowMax);
			randomCol=Math.abs(RGenerator.nextInt()%colMax);
			if(trymove(randomRow,randomCol)){
				counter2--;
			}
		}
	}

	// tries to set a new random mine on the minefield
	// returns false if mine already exists at specified location
	// else sets mine at specified location and returns true
	private boolean trymove(int randomRow, int randomCol){
		if(mines[randomRow][randomCol]){
			return false;
		}
		mines[randomRow][randomCol] = true;
		return true;
	}
	
	// shows all mines on field when losing
	private void boom(){
		for(int row=0;row<rowMax;row++){
			for(int col=0;col<colMax;col++){
				visible[row][col] = mines[row][col];
			}
		}
		boom=true;
		show();
	}

	private int countSurroundingMines(int row, int col){
		int count=0;
		for(int irow=row-1; irow<=row+1; irow++){
			for(int icol=col-1; icol<=col+1; icol++){
				if(icol>=0&&icol<colMax&&irow>=0&&irow<rowMax
				&& mines[irow][icol]){
					count++;
				}
			}
		}
		return count;
	}
	
	// determines what character should be drawn at a given location 
	// * = mine
	// - = cleared
	// ? = unknown
	// 0-8 = amount of surrounding mines
	private char drawChar(int row, int col){
		if(visible[row][col]){
			if(mines[row][col]){
				return '*';
			}
			return (char)(countSurroundingMines(row,col) + 48);
		}
		if(boom){
			return '-';
		}
		return '?';
	}

	public boolean getBoom(){
		return boom;
	}
	
	// parses and checks if valid location input
	public boolean legalMoveString(String input){
		String[] separated=input.split(" ");
		int row;
		int col;
		try{
			row=Integer.parseInt(separated[0]);
			col=Integer.parseInt(separated[1]);
			if(row<0||col<0||row>=rowMax||col>=colMax){
				throw new java.io.IOException();
			}
		}
		catch(Exception e){
			System.out.println("\nInvalid Input!");
			return false;
		}
		return legalMoveValue(row,col);
	}

	// performs the move
	private boolean legalMoveValue(int row, int col){
		if(visible[row][col]){
			System.out.println("You stepped in allready revealed area!");
			return false;
		}
		visible[row][col]=true;
		if(mines[row][col]){
			boom();
			return false;
		}
		return true;
	}

	// prints the board
	public void show(){
		System.out.println("\n    0 1 2 3 4 5 6 7 8 9 ");
		System.out.println("   ---------------------");
		for(int row=0; row<rowMax; row++){
			System.out.print(row+" |");
			for(int col=0; col<colMax; col++){
				System.out.print(" "+drawChar(row,col));
			}
			System.out.println(" |");
		}
		System.out.println("   ---------------------");
	}
}
