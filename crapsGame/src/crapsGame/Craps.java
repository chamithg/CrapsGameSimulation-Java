package crapsGame;

import java.util.Random;

/**
 * This class simulates craps game for 1,000,000 times.
 * @author chamith
 * @since 09/05/2022
 * 
 */


public class Craps {
	// create random number generator for use in method rollDice 
	   private static final Random rand = new Random(); 
	   // enumeration with constants that represent the game status 
	   enum Status { CONTINUE, WON, LOST }; 
	 
	   // constants that represent common rolls of the dice 
	   private static final int SNAKE_EYES = 2; 
	   private static final int TREY = 3; 
	   private static final int SEVEN = 7; 
	   private static final int YO_LEVEN = 11; 
	   private static final int BOX_CARS = 12; 
	 
	   
	 public static void main( String[] args ) {
		 
		 int[] wonArray = new int[22];// each index of this array keeps the track of wins( index1= roll count 1) 
		 int[] lostArray = new int[22];// each index of this array keeps the track of losses( index1= roll count 1) 
		 
		 int wonCount =0; // keep the track of all wins
		 int lostCount = 0; // keep the track of all losses
		 int totalRollCount =0; // keep the track of the sum of all roll counts in each game.
		 
		 
		 for(int i = 0;i<1000000;i++) { // game is set to run 1,000,000 times
			 Result game = play();   // this will run the game(by calling game method)
			 totalRollCount+=game.getRollCount();
			 if(game.getGameStatus()=="won") {  // if wins updates the wonArray accordingly
				 wonCount ++;
				 
				 if(game.getRollCount()<21) {
					 wonArray[game.getRollCount()]+=1;
				 }else {
					 wonArray[21]+=1;   // every game that took more than 20 attempts counted in add to 21st index
				 }
			 }
			 if(game.getGameStatus()=="lost") {  // if lost updates the lostArray accordingly
				 lostCount++;
				 if(game.getRollCount()<21) {
					 lostArray[game.getRollCount()]+=1;
				 }else {
					 lostArray[21]+=1; // every game that took more than 20 attempts counted in add to 21st index
				  
				 }
			 }
			 
		 }
		 
		 
		 Double winPercentage = Double.valueOf(wonCount)*100/1000000;  // calculates win percentage.
		 Double avgRollsPerGame = Double.valueOf(totalRollCount/1000000); // calculates average number of rolls per game.
		 
		 
		printResult(wonArray,lostArray); // calls the print result method
		System.out.printf("The chances of winning are %d / 1000000 = %.2f \n\n",wonCount,winPercentage); // print win percentage

		System.out.printf("The average game length is %.2f rolls.  \n\n",avgRollsPerGame);
	
	 }
	 
	// plays one game of craps 
	 public static Result play() {
		 { 
		      int myPoint = 0; // point if no win or loss on first roll 
		      Status gameStatus; // can contain CONTINUE, WON or LOST 
		      int sumOfDice = rollDice(); // first roll of the dice		      
		      int rollCount = 1; // this will keep track of the roll counts made
		 
		 
		 
		      // determine game status and point based on first roll 
		      switch ( sumOfDice ) 
		      { 
		         case SEVEN: // win with 7 on first roll 
		         case YO_LEVEN: // win with 11 on first roll 
		            gameStatus = Status.WON; 
		            break; 
		         case SNAKE_EYES: // lose with 2 on first roll 
		         case TREY: // lose with 3 on first roll 
		         case BOX_CARS: // lose with 12 on first roll 
		            gameStatus = Status.LOST; 
		            break; 
		         default: // did not win or lose, so remember point 
		            gameStatus = Status.CONTINUE; // game is not over 
		            myPoint = sumOfDice; // remember the point 
		            System.out.printf( "Point is %d\n", myPoint ); 
		            break; // optional at end of switch 
		 
		      } // end switch 

		      // while game is not complete 
		      while ( gameStatus == Status.CONTINUE ) // not WON or LOST 
		      { 
		         sumOfDice = rollDice(); // roll dice again
		         rollCount += 1;
		 
		         // determine game status 
		         if ( sumOfDice == myPoint ) // win by making point 
		            gameStatus = Status.WON; 
		         else 
		            if ( sumOfDice == SEVEN ) // lose by rolling 7 before point 
		               gameStatus = Status.LOST; 
		      } // end while 
		      
		 
		      // display won or lost message and return result object containing roll count and final result
		      if ( gameStatus == Status.WON ) {
		         System.out.println( "Player wins, roll count is : "+ rollCount ); 
		         return new Result(rollCount, "won");// string input will be "won"
				 
		      }
		      else 
		         System.out.println( "Player loses, roll count is : "+ rollCount );
		      	 return new Result(rollCount, "lost"); // string input will be "loss"
		      }		 
		 
		 
		 
	 }
	 
	 
	 //rolls dice once
	 public static int rollDice() {
		 int die1Face = rand.nextInt( 6 ); // roll the first die
		 int die2Face = rand.nextInt( 6 ); // roll the second die		 
		 int sum = die1Face + die2Face; // get the sum of the roll		 
		 System.out.printf( "Player rolled %d + %d = %d\n", die1Face, die2Face, sum );// print the sum  		 
		 return sum;// return the result of the roll
		 
	 }
	 
	 // prints out the final results
	 public static void printResult(int[]wonArray,int[]lostArray ) {
		 
		 for(int i =1; i< 22; i++) {
			 if(i<21) {
				 
				 System.out.printf("%d  games won and %d games lost on roll #%d.\n",wonArray[i],lostArray[i],i);
			 }else {
				 System.out.printf("%d  games won and %d games lost after 20th roll \n\n",wonArray[i],lostArray[i]);
			 }
		 }
		 
	 }

}


//this will create a new object to hold the result of each game
final class Result { 

    private final int rollCount; // this holds the count of rolls
    private final String gameStatus; // this holds the final result of game

    public Result(int rollCount, String gameStatus) {  // constructor
        this.rollCount = rollCount;
        this.gameStatus = gameStatus;
    }

    public int getRollCount() {  // roll count getter
        return rollCount;
    }

    public String getGameStatus() { // game result getter
        return gameStatus;
    }
}
