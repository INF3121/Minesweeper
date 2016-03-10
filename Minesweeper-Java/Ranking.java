import java.util.Scanner;

public class Ranking{
	private final int MAX_PEOPLE_LIMIT=5;
	private String[] name;
	private int[] record;
	private int last;

	Ranking(){
		name=new String[MAX_PEOPLE_LIMIT];
		record=new int[MAX_PEOPLE_LIMIT];
	}

	// requests player name, tries to add it to record list, sorts record list and shows the record list
	// At the end of game, records the result of the player and records it in the high score, then sorts in order.
	public void recordName(int result){
		System.out.print("\n Please enter your name -");
		Scanner in=new Scanner(System.in);
		String newName=in.nextLine();
		if((last==MAX_PEOPLE_LIMIT)&&record[MAX_PEOPLE_LIMIT-1]>result){
			System.out.println("\nSorry you cannot enter top "+(MAX_PEOPLE_LIMIT)+" players");
			return;
		}
		if(last==MAX_PEOPLE_LIMIT){
			name[MAX_PEOPLE_LIMIT-1]=newName;
			record[MAX_PEOPLE_LIMIT-1]=result;
		}else{
			name[last]=newName;
			record[last]=result;
			last++;
		}
		sort();
		show();
	}
	
	// Displays the high score board
	public void show(){
		if(last==0){
			System.out.println("Still no results");
			return;
		}
		System.out.println("N Name\t\tresult");
		for(int i=0;i<last;i++){
			System.out.println((i+1)+" "+name[i]+"\t"+record[i]);
		}
	}

	// swaps lower scores higher up on the list
	// returns true if something is changed, which might indicate that another call is required
	private boolean sortSwap(){
		boolean unsorted = false;
		for(int i=0; i<(last-1); i++){
			if(record[i+1]>record[i]){
				int swapR=record[i];
				record[i]=record[i+1];
				record[i+1]=swapR;
				String swapN=name[i];
				name[i]=name[i+1];
				name[i+1]=swapN;
				unsorted=true;
			}
		}
		return unsorted;
	}

	// sorts the record list
	private void sort(){
		if(last<2) return;
		while(sortSwap());
	}
}
