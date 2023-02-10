import java.util.Arrays;
public class Die {
    /** Instance Variables **/
    private int sides;
    private int dice[];
    private int count = 6;
    private int rolls = 6;
    /** Constructors **/
    public Die()
    {
        sides = 6;
    }
    public Die(int numSides)
    {
        sides = numSides;
    }
    //getter and setter functions for rolls
    public void setRolls(int rolls)
    {
        this.rolls = rolls;
    }
    public int getRolls()
    {
        return rolls;
    }

    /** Methods **/
    public int getSides(String name)
    {
        return sides;
    }

    public int[] getCup(){
        return dice;
    }

    public String getDice()
    {
        return Arrays.toString(dice);
    }
    /**
     * Returns a random int between 1 and
     * the number of sides on the Die
     */
    public int roll()
    {
        int num = (int)(Math.random()*sides) + 1;
        return num;
    }
    //this function will roll a die a certain amount of times and assign it to a place value in an array of dice
    public void rolls()
    {
        dice = new int[count];
        int numRolled;
        for (int i = 0; i < rolls; i++)
        {
            numRolled = roll();
            dice[numRolled - 1]++;
        }
    }
    // this function will return the amount of dice with the number that is inputted
    public int countInt(int digit)
    {
        return dice[digit - 1];
    }


    public String toString()
    {
        return "This is a " + sides + " sided die.";
    }
}
