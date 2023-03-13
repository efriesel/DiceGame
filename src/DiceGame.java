
import java.util.Arrays;
import java.util.Scanner;
public class DiceGame {
    //instance variables
    private int playersCount;
    private Player players[];
    private Round gameOne;
    private Round gameTwo;
    private Round gameThree;

    private DiceGameViewer window;
    //main function will play 3 games and print the result
    public static void main(String[] args)
    {
        DiceGame g = new DiceGame();
        Round.printInstructions();
        g.gameOne.playRound();
        g.resetPlayers();
        g.gameTwo.playRound();
        g.resetPlayers();
        g.gameThree.playRound();
        g.printResults();
    }
    //tournament constructor
    public DiceGame()
    {
        Scanner s = new Scanner(System.in);
        String currentName;
        //player count must be at least 2
        while (playersCount < 2)
        {
            System.out.println("Enter how many players you have");
            playersCount = s.nextInt();
        }
        s.nextLine();
        //initialize players as each individual dice classes
        players = new Player[playersCount];
        for (int i = 0; i < playersCount; i++)
        {
            System.out.println("player " + (i + 1) + "'s name is: ");
            currentName = s.nextLine();
            players[i] = new Player(currentName);
        }
        window = new DiceGameViewer(this);
        //game object creations
        gameOne = new Round(playersCount, players, window);
        gameTwo = new Round(playersCount, players, window);
        gameThree = new Round(playersCount, players, window);
    }
    //this function will print the results of the tournament
    public void printResults()
    {
        System.out.print("In this tournament, " + gameOne.getWinner() + " won Round 1 with " +
                gameOne.getWinningRolls() + " dice, " + gameTwo.getWinner() + " won Round 2 with " +
                gameTwo.getWinningRolls() + " dice, and " + gameThree.getWinner() + " won Round 3 with " +
                gameThree.getWinningRolls() + " dice. Overall");
        for (int i = 0; i < this.playersCount; i++)
        {
            if (i == (this.playersCount - 1))
            {
                System.out.print(" and ");
            }
            System.out.print(", " + players[i].getName() + " won " + players[i].getScore() + " games");

        }
        System.out.println(". What a Great Tournament!");
        window.setGameOver(true);
        window.repaint();
    }

    public int getPlayersCount() {
        return playersCount;
    }

    public Player[] getPlayers() {
        return players;
    }


    //this function will reset the roll count of each player
    public void resetPlayers()
    {
        for (int i = 0; i < playersCount; i++)
        {
            this.players[i].setRolls(6);
            this.players[i].setElim(false);

        }
    }

}

