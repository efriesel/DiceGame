import java.util.Arrays;
import java.util.Scanner;
public class Round {
    //Instance variables
    private String gameWinner;
    private int winnerRolls;
    private final Scanner s = new Scanner(System.in);
    private int count;
    private int num;
    private boolean winner = false;
    private int currentCount;
    private int currentNum;
    private int winningPlayer;
    private final int playersCount;
    private boolean win;
    private final Player[] players;

    private DiceGameViewer window;
    //constructor with players and player count variables
    public Round(int playersCount, Player[] players, DiceGameViewer window)
    {
        this.window = window;
        this.players = players;
        this.playersCount = playersCount;
    }
    //getter functions for winning player name and roll count
    public String getWinner()
    {
        return gameWinner;
    }
    public int getWinningRolls()
    {
        return winnerRolls;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public int getCurrentNum() {
        return currentNum;
    }

    //this function will play a round and select a winner for the given round
    public void playRound()
    {
        String input;
        char inv;
        boolean win = false;
        int remainingPlayers = playersCount;
        while (!winner)
        {
            for (int i = 0; i < playersCount; i++)
            {
                if (players[i].getRolls() == 0)
                {
                    players[i].setElim(true);
                    remainingPlayers--;
                }
                else
                {
                    //only needed if there is a winner
                    winningPlayer = i;
                    players[i].roll();
                    checkRolls(players[i]);
                }
            }
            if (remainingPlayers == 1)
            {
                //print out the statement for the winner
                winner = true;
                System.out.println(players[winningPlayer].getName() + " wins this round with " +
                        players[winningPlayer].getRolls() + " rolls!");
                gameWinner = players[winningPlayer].getName();
                winnerRolls = players[winningPlayer].getRolls();
                players[winningPlayer].setScore((players[winningPlayer].getScore()) + 1);
            }
            else
            {
                //this will stimulate a round
                playMatch(players);
            }

        }
    }
    //this function will print the instructions for the program
    public static void printInstructions()
    {
        System.out.println("This is a liars dice game, starting with 6 dice each on 6-sided dice." +
                "Each pass, the player who's turn it is will make a bet of how many dice of" +
                "1 number there are based on the given amount of dice. The next player may call this as true or false," +
                "and if they call false, whoever was lying loses a die until 1 player has no dice. Each bet much either have" +
                "more dice than the previous bet or the same amount of dice but a higher number. Good luck");
        Scanner s = new Scanner(System.in);
        s.nextLine();
    }

    //this program will check the rolls in a specific players' cup
    public void checkRolls(Player p)
    {
        System.out.println(p.getName() + ", look at your cup, when finished, type f");
        System.out.println(p.getDice());
        char input = s.next().charAt(0);
        while (input != 'f')
        {
            System.out.println(p.getName() + ", look at your cup, when finished, type f");
            input = s.next().charAt(0);
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
        s.nextLine();
    }
    //this function will input a bet from a player and alter the current variables
    public void inputBet(Player in)
    {
        System.out.println(in.getName() + ", enter your amount of dice followed by your number or enter i for inventory");
        String input = s.nextLine();
        if (input.charAt(0) == 'i')
        {
            checkRolls(in);
            System.out.println("now print your input");
            input = s.nextLine();
        }
        count = ((int) input.charAt(0)) - 48;
        num = ((int) input.charAt(1)) - 48;
        while ((count < currentCount) || (count == currentCount && num == currentNum) || (count == currentCount && num < currentNum))
        {
            System.out.println("please enter a bet higher than the last bet");
            input = s.nextLine();
            count = ((int) input.charAt(0)) - 48;
            num = ((int) input.charAt(1)) - 48;
            s.nextLine();
        }
        currentNum = num;
        currentCount = count;
    }
    //this function will input a guess from a user in comparison with a player and an acuser given dice.
    public void inputGuess(Player p, Player a, Player in[])
    {
        System.out.println(a.getName() + " , do you think the current bet is correct?" +
                "enter t if true and make a new bet, i to see your inventory" +
                "or f to guess that the answer is false");
        String input = s.nextLine();
        int inv = input.charAt(0);
        //allow users to check inventory
        while (inv == 'i')
        {
            checkRolls(a);
            System.out.println("now print your input");
            input = s.nextLine();
            inv = input.charAt(0);
        }
        if (inv == 'f')
        {
            //if ther are the correct or more amount of dice, the accuser loses a dice,
            //if not, the player who made the bet loses a die
            if (sumNum(num, in) >= count)
            {
                System.out.println(p.getName() + " was telling the truth!!!  " + a.getName() + " loses a die");
                a.setRolls(a.getRolls() - 1);
            }
            else
            {
                System.out.println(p.getName() + " was lying, way to call them out. " + p.getName() +  " loses a die");
                p.setRolls(p.getRolls() - 1);
            }
            win = true;
        }
        s.nextLine();
    }
    //this function simulates a round, that will play until a players' dice count is altered
    public void playMatch(Player[] in)
    {
        currentCount = 0;
        currentNum = 0;
        win = false;
        int turn = 0;
        int next = 1;
        while (!win)
        {
            if (next == playersCount)
            {
                next = 0;
            }
            if (turn == playersCount)
            {
                turn = 0;
            }
            //players may only bet or call if they are not eliminated
            if (!in[turn].getElim())
            {
                inputBet(players[turn]);
                if (!in[next].getElim())
                {
                    //gives the current bet to remind players
                    System.out.println("Current bet, " + in[turn].getName() + " says there are " + currentCount + " dice with the number " + currentNum);
                    inputGuess(in[turn], in[next], in);
                }
                turn++;
                next++;
            }
            else
            {
                turn++;
                next++;
            }
        }
    }
    //this function will return a number of dice for a given # based
    //on all the dice at that given number for all players
    public int sumNum(int num, Player in[])
    {
        int sum = 0;
        for (int i = 0; i < playersCount; i++)
        {
            if (!in[i].getElim())
            {
                sum += in[i].countInt(num);
            }
        }
        return sum;
    }
}
