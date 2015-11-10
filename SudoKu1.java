import java.util.*;
public class SudoKu1{

	public static void main(String[] args) {
		int [][] puzzle = new int [9][9];
		int[][] userPuzzle = new int[9][9];
		Scanner scan = new Scanner(System.in);

		key(puzzle); //Will set the first value to a random key
		populate(0,0,puzzle); // Will populate the puzzle based off the key
		randomize(puzzle); //Will truly randomize the puzzle
		equalize(puzzle, userPuzzle); //Will create two similar arrays to use an answer key. 
		
		System.out.print("Choose your difficulty (Hard/Medium/Easy): "); //Will ask for a difficulty.
		String level = scan.next(); 

		difficulty(level , userPuzzle); //This will eliminate a set amount of values to set the difficulty.
		print(userPuzzle);//Will simply print the puzzle out.

		while (!gameOver(puzzle,userPuzzle)){
			gameLoop(puzzle , userPuzzle);
		}
	}

	//The following will print out the puzzle with a format to make it look more appealing.
	static void print(int[][] print){
		for (int columns = 0; columns < print.length ; columns++){
			for (int rows = 0; rows<print[columns].length ; rows++){
				if (print[columns][rows] == 0){
					System.out.print(" " + " ");
				}
				else{
					System.out.print(print[columns][rows] + " ");
				}
				if (rows == 2 || rows == 5){
					//This will print the vertical breaks.
					System.out.print("| ");
				}
			}
			System.out.println();
			if (columns == 2 || columns == 5){
				for (int line = 0 ; line < 21 ; line++){
					//This will print the horizontal breaks.
					System.out.print("-");
				}
				System.out.println();
			}
		}
	}

	static void equalize(int[][] first , int[][] second){
		for (int columns = 0; columns < first.length ; columns++){
			for (int rows = 0; rows< first[columns].length ; rows++){
				second[columns][rows] = first[columns][rows];
			}
		}
	}

	static int[][] populate (int x , int y, int [][] populate){
		//The loop will continue to run while the last square is still 0 or the last square returns a false when checked through checker.
		while (!checker(8,8,populate) || populate[8][8] == 0){
			//If the number is less than 9, it will add one to it and check if its valid through valid through 'checker'.
			if (populate[x][y] < 9){
				populate[x][y]++;

				//If the new number is valid, it will continue to populate the grid and check the next number.
				if (checker(x,y,populate)){
					int xx,yy;
					if (x == 8) {
						xx = 0;
						yy = y + 1;
					}
					else {
						yy = y;
						xx = x+1;
					}
					populate(xx,yy,populate);
				}
			}
			//If the number is greater than 9, it will reset it to 0 and run it through 'checker' and validate it.
			else{
				populate[x][y] = 0;
				break;
			}
		}

		return populate;
	}

	static boolean checker(int x, int y , int [][] array){
		String code = "";
		for (int i = 0; i < array.length ; i++){
			code += Integer.toString(array[x][i]); //This will check all the numbers in x row
			code += Integer.toString(array[i][y]); // This will check all the numbers in y column.
			code += Integer.toString(array[(x/3) * 3 + i/3][(y/3) * 3 + i % 3]); //This will check the box.
		}
		int count =0 , index = 0;

		//The following lines of code will check the string i made which consists of all 
		//the numbers in the row, column and box.
		//If the count is greater than 4, that means that the number also exists in the same column or row.
		//If the number does not exist again, the Indexof will return -1.
		while((index = code.indexOf(Integer.toString(array[x][y]),index)) != -1){
			count++; index++;
		}
		return count == 3;
	}

	//By changing the first value, it will change the rest of the puzzle.
	static void key(int[][] key){
		Random gen = new Random();
		key[0][0] = gen.nextInt(9) + 1;
	}

	//The following will truly randomize the puzzle
	static void randomize(int[][] random){
		Random gen = new Random();
		int[] values = {1,2,3,4,5,6,7,8,9};
		
		//The following is an implementation of the Fischer Yates shuffle.
		for (int i = 0; i < values.length; i++){
			int index = gen.nextInt(i + 1);
			int number = values[index];
			values[index] = values[i];
			values[i] = number;
		}

		//The following will set each value to a random value.
		for (int rows = 0 ; rows < random.length ; rows++){
			for (int columns = 0; columns < random[rows].length ; columns++){
				if (random[rows][columns] == 1){
					random[rows][columns] = values[0];
				}
				else if (random[rows][columns] == 2){
					random[rows][columns] = values[1];
				}
				else if (random[rows][columns] == 3){
					random[rows][columns] = values[2];
				}
				else if (random[rows][columns] == 4){
					random[rows][columns] = values[3];
				}
				else if (random[rows][columns] == 5){
					random[rows][columns] = values[4];
				}
				else if (random[rows][columns] == 6){
					random[rows][columns] = values[5];
				}
				else if (random[rows][columns] == 7){
					random[rows][columns] = values[6];
				}
				else if (random[rows][columns] == 8){
					random[rows][columns] = values[7];
				}
				else 
					random[rows][columns] = values[8];
			}
		}
	}


	// The difficulty is changed by eliminating more or less values that the player can see.
	static void difficulty(String level , int[][] array){
		Random gen = new Random();
		int delete;

		//Depending on what the user chooses, this method will delete more or less values.
		if (level.equalsIgnoreCase("Hard"))
			delete = 56; //25 values
		else if (level.equalsIgnoreCase("Medium"))
			delete = 46; //35 values
		else 
			delete = 36; //45 values

		//Random values are eliminated based on what difficulty the player chooses.
		for (int columns = 0; columns < delete  ; columns++){
			int x = gen.nextInt(9);
			int y = gen.nextInt(9);
			while (array[x][y] == 0){
				x = gen.nextInt(9);
				y = gen.nextInt(9);
			}
			array[x][y] = 0;
		}
	}

	//Game will continue to ask player for the X position, Y position and the new value until all 81 values are correct
	static void gameLoop( int[][] puzzle , int[][] array){

		Scanner scan = new Scanner(System.in);
		System.out.println();
		System.out.print("Choose your X coordinate (Starting at 1): ");
		int xCoordinate = scan.nextInt();
		System.out.print("Chooose your Y coordinate (Starting at 1): ");
		int yCoordinate = scan.nextInt();
		System.out.print("Choose your new value: ");
		int value = scan.nextInt();

		if (value == puzzle[yCoordinate - 1][xCoordinate - 1]){
			array[yCoordinate - 1][xCoordinate - 1] = value;
		}
		else
			System.out.println("\nThat value is Incorrect!!!");
		System.out.println();
		print(array);
	}

	//Game will end if the userPuzzle matches the initial puzzle
	static boolean gameOver(int[][] puzzle , int[][] user){
		int count = 0;
		for (int columns = 0; columns < puzzle.length ; columns++){
			for (int rows = 0; rows< puzzle[columns].length ; rows++){
				if (user[columns][rows] == puzzle[columns][rows]){
					count++;
				}
			}
		}
		if (count == 81){
			System.out.println("\nGame Over You win!!!");
			return true;
		}
		return false;
	}
}


