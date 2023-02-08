public class Player {
    //instance variables
    private String name;
    private Die dice;
    private boolean elim;
    private int score;
    //constructor
    public Player(String name)
    {
        Die in = new Die(6);
        this.name = name;
        elim = false;
        dice = in;
    }
    //getter and setter functions for each variable
    public int getScore()
    {
        return score;
    }
    public void setScore(int score)
    {
        this.score = score;
    }
    public String getName()
    {
        return name;
    }
    public boolean getElim()
    {
        return elim;
    }
    public void setElim(boolean elim)
    {
        this.elim = elim;
    }
    //layered getter and setter function for funcions in die class
    public int getRolls()
    {
        return dice.getRolls();
    }
    public void setRolls(int rolls)
    {
        dice.setRolls(rolls);
    }
    public String getDice()
    {
        return dice.getDice();
    }
    //reference to functions in die class
    public void roll()
    {
        dice.rolls();
    }
    public int countInt(int digit)
    {
        return dice.countInt(digit);
    }
    //to string function
    public String toString()
    {
        return name + ": " + score + " points";
    }

}
