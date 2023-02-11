/**
 * DiceGameViewer by Ethan Friesel
 */
import javax.swing.*;
import java.awt.*;
//https://www.dreamstime.com/set-dice-icon-traditional-die-six-faces-cube-marked-different-numbers-dots-pips-to-simple-flat-style-vector-image202612165#_
public class DiceGameViewer extends JFrame {
    private final Image[] diceImages;
    private static final Image CUP_IMAGE = new ImageIcon("Resources/Cup.png").getImage();
    private static final Image BACKGROUND_IMAGE = new ImageIcon("Resources/Background.jpeg").getImage();
    public final Font HEADER_FONT = new Font("Sans-Serif", 1, 36);
    public final Font ENDING_FONT = new Font("Sans-Serif", 1, 50);
    private final int HEADER_HEIGHT = 22;
    public final int WINDOW_WIDTH = 800;
    public final int WINDOW_HEIGHT = 822;
    public final int VISIBLE_HEIGHT = (WINDOW_HEIGHT - HEADER_HEIGHT) * 2 / 3;
    public final int STARTING_Y = VISIBLE_HEIGHT / 2;
    public final int PLAYER_SPACING;
    public final int IMAGE_SIZE = WINDOW_WIDTH / 10;
    DiceGame game;
    private int turn;
    private int currentCount;
    private int currentNum;

    private boolean win;
    private boolean gameOver;

    private Player winner;



    /**
     * Initially, the turn is set to zero for no index out of bounds, all images are initialized in addition to the PLAYER_SPACING in terms of the playerCount
     * @param game
     */
    public DiceGameViewer(DiceGame game) {
        turn = -1;
        win = false;
        gameOver = false;
        this.game = game;
        PLAYER_SPACING = VISIBLE_HEIGHT / ((game.getPlayersCount() + 2) / 2);
        diceImages = new Image[6];
        diceImages[0] = new ImageIcon("Resources/1.png").getImage();
        diceImages[1] = new ImageIcon("Resources/2.png").getImage();
        diceImages[2] = new ImageIcon("Resources/3.png").getImage();
        diceImages[3] = new ImageIcon("Resources/4.png").getImage();
        diceImages[4] = new ImageIcon("Resources/5.png").getImage();
        diceImages[5] = new ImageIcon("Resources/6.png").getImage();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Liar's Dice");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
    }

    /**
     *in the paint function, the Names will always be drawn, however, each time there is a bet or a player is up to bet or call, the drawBet() or drawCup() functions are called
     * in addition, the win string should be drawn in the event of a win
     * @param g the specified Graphics window
     */
    public void paint(Graphics g) {
        g.drawRect(0, 22, WINDOW_WIDTH, WINDOW_HEIGHT);
        drawBackground(g);
        drawNames(g);
        if (currentCount != -1) {
            drawBet(g);
        }
        if (turn != -1) {
            drawCup(g);
        }
        if (gameOver){
            g.drawString("Game Over", WINDOW_WIDTH / 2 - (ENDING_FONT.getSize() * 2), WINDOW_HEIGHT - ENDING_FONT.getSize());
        }
        if(win){
            drawEnding(g);
            win = false;
        }
    }

    /**
     * this function will draw the background of the pirates of the carribean movie and the Current bet string centered
     * @param g
     */
    public void drawBackground(Graphics g) {
        g.setFont(HEADER_FONT);
        g.drawImage(BACKGROUND_IMAGE, 0, HEADER_HEIGHT, WINDOW_WIDTH, WINDOW_HEIGHT / 3, this);
        g.setColor(Color.WHITE);
        g.fillRect(0, STARTING_Y, WINDOW_WIDTH, VISIBLE_HEIGHT);
        g.setColor(Color.BLACK);
        g.drawString("Current Bet", WINDOW_WIDTH / 2 - HEADER_FONT.getSize() * 4, STARTING_Y + VISIBLE_HEIGHT / 3);
        drawNames(g);
    }

    /**
     * this function will draw all players' name, number of rolls, and number of wins next to an image of the cup in two uniform columns.
     * @param g
     */
    public void drawNames(Graphics g){
        g.setFont(HEADER_FONT);
        for (int i = 0; i < game.getPlayersCount(); i++){
            int y = STARTING_Y + IMAGE_SIZE * 2 + PLAYER_SPACING * (i / 2);
            //switches off using even and odd
            if (i % 2 == 0){
                g.drawImage(CUP_IMAGE, 0, STARTING_Y + PLAYER_SPACING * (i / 2), IMAGE_SIZE,IMAGE_SIZE, this);
                g.drawString(game.getPlayers()[i].getName(), IMAGE_SIZE, STARTING_Y + IMAGE_SIZE / 2 + PLAYER_SPACING * (i / 2));
                g.drawString("Dice: " + game.getPlayers()[i].getRolls(), IMAGE_SIZE, STARTING_Y + IMAGE_SIZE + PLAYER_SPACING * (i / 2));
                g.drawString("Wins: " + game.getPlayers()[i].getScore(),0, y);
            }
            else {
                g.drawImage(CUP_IMAGE, WINDOW_WIDTH - IMAGE_SIZE, STARTING_Y + PLAYER_SPACING * (i / 2), IMAGE_SIZE, IMAGE_SIZE, this);
                g.drawString(game.getPlayers()[i].getName(), WINDOW_WIDTH - IMAGE_SIZE - HEADER_FONT.getSize() * game.getPlayers()[i].getName().length() / 2, STARTING_Y + + IMAGE_SIZE / 2 + PLAYER_SPACING * (i / 2));
                g.drawString("Dice: " + game.getPlayers()[i].getRolls(), WINDOW_WIDTH - IMAGE_SIZE - HEADER_FONT.getSize() * 4, STARTING_Y + + IMAGE_SIZE + PLAYER_SPACING * (i / 2));
                g.drawString("Wins: " + game.getPlayers()[i].getScore(),WINDOW_WIDTH - HEADER_FONT.getSize() * 4, y);
            }

        }
    }
    /**
     * setter functions
     */
    public void setTurn(int turn){
        this.turn = turn;
    }
    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    public void setCurrentNum(int currentNum) {
        this.currentNum = currentNum;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    /**
     * This function will draw the bet in terms of the current count number of dice with the current num, centered
     * @param g
     */
    public void drawBet(Graphics g){
        int startingPoint = WINDOW_WIDTH / 2 - IMAGE_SIZE * currentCount / 2;
        for (int i = 0; i < currentCount; i++){
            g.drawImage(diceImages[currentNum - 1], startingPoint + IMAGE_SIZE * i, STARTING_Y + VISIBLE_HEIGHT / 3 + IMAGE_SIZE / 2, IMAGE_SIZE, IMAGE_SIZE, this);
        }
    }

    /**
     * This function will print the dice that are rolled from the player who's turn it is to either bet or call
     * @param g
     */
    public void drawCup(Graphics g){
        g.setFont(HEADER_FONT);
        g.drawString("Player: " + game.getPlayers()[turn].getName() + "'s Turn", WINDOW_WIDTH / 2 - (HEADER_FONT.getSize() * ((15 + game.getPlayers()[turn].getName().length()) / 2) / 2), STARTING_Y + VISIBLE_HEIGHT * 2 / 3);
        int order = 0;
        //starting point for the images so they are centered
        int startingPoint = WINDOW_WIDTH / 2 - IMAGE_SIZE * game.getPlayers()[turn].getRolls() / 2;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < game.getPlayers()[turn].getCup()[i]; j++){
                g.drawImage(diceImages[i], startingPoint + order * IMAGE_SIZE, STARTING_Y + VISIBLE_HEIGHT * 2 / 3 + IMAGE_SIZE / 2, IMAGE_SIZE, IMAGE_SIZE, this);
                order++;
            }
            if (order == game.getPlayers()[turn].getRolls()){
                return;
            }
        }
    }
    public void drawEnding(Graphics g){
        g.setFont(ENDING_FONT);
        g.drawString(winner.getName() + " Wins the Round", WINDOW_WIDTH / 2 - ENDING_FONT.getSize() * (((15 + winner.getName().length()) / 2) / 2), STARTING_Y + VISIBLE_HEIGHT / 3 - ENDING_FONT.getSize());
    }
}

