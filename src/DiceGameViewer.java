import javax.swing.*;
import java.awt.*;
import java.text.AttributedCharacterIterator;

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


    public DiceGameViewer(DiceGame game) {
        turn = -1;
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


    public void paint(Graphics g) {
        g.drawRect(0, 22, WINDOW_WIDTH, WINDOW_HEIGHT);
        drawBackground(g);
        drawNames(g);
        if (currentCount != -1){
            drawBet(g);
        }
        if (turn != -1){
            drawCup(g);
        }
//        for (Square[] row  : game.getBoard()){
//            for (Square s : row){
//                s.Draw(this, MARKER_SIZE, g);
//            }
//        }
//        if (game.getGameOver()){
//            g.setFont(ENDING_FONT);
//            g.setColor(Color.BLACK);
//            if (game.checkTie()){
//                g.drawString("It's a Tie!", WINDOW_WIDTH / 15 * 5,(WINDOW_HEIGHT - HEADER_HEIGHT) / 15 * 14 + HEADER_HEIGHT);
//            }
//            else {
//                g.drawString(game.getWinner() + " Wins", WINDOW_WIDTH / 15 * 6,(WINDOW_HEIGHT - HEADER_HEIGHT) / 15 * 14 + HEADER_HEIGHT);
//            }
//        }
    }

    public void drawBackground(Graphics g) {
        g.setFont(HEADER_FONT);
        g.drawImage(BACKGROUND_IMAGE, 0, HEADER_HEIGHT, WINDOW_WIDTH, WINDOW_HEIGHT / 3, this);
        g.setColor(Color.WHITE);
        g.fillRect(0, STARTING_Y, WINDOW_WIDTH, VISIBLE_HEIGHT);
        g.setColor(Color.BLACK);
        g.drawString("Current Bet", WINDOW_WIDTH / 3, STARTING_Y + VISIBLE_HEIGHT / 3);
        drawNames(g);
    }

    public void drawNames(Graphics g){
        g.setFont(HEADER_FONT);
        for (int i = 0; i < game.getPlayersCount(); i++){
            if (i % 2 == 0){
                g.drawImage(CUP_IMAGE, 0, STARTING_Y + PLAYER_SPACING * (i / 2), IMAGE_SIZE,IMAGE_SIZE, this);
                g.drawString(game.getPlayers()[i].getName(), IMAGE_SIZE, STARTING_Y + IMAGE_SIZE / 2 + PLAYER_SPACING * (i / 2));
                g.drawString("Dice: " + game.getPlayers()[i].getRolls(), IMAGE_SIZE, STARTING_Y + IMAGE_SIZE + PLAYER_SPACING * (i / 2));
                g.drawString("Wins: " + game.getPlayers()[i].getScore(),0, STARTING_Y + + IMAGE_SIZE * 2 + PLAYER_SPACING * (i / 2));
            }
            else {
                g.drawImage(CUP_IMAGE, WINDOW_WIDTH - IMAGE_SIZE, STARTING_Y + PLAYER_SPACING * (i / 2), IMAGE_SIZE, IMAGE_SIZE, this);
                g.drawString(game.getPlayers()[i].getName(), WINDOW_WIDTH - IMAGE_SIZE - HEADER_FONT.getSize() * game.getPlayers()[i].getName().length() / 2, STARTING_Y + + IMAGE_SIZE / 2 + PLAYER_SPACING * (i / 2));
                g.drawString("Dice: " + game.getPlayers()[i].getRolls(), WINDOW_WIDTH - IMAGE_SIZE - HEADER_FONT.getSize() * 4, STARTING_Y + + IMAGE_SIZE + PLAYER_SPACING * (i / 2));
                g.drawString("Wins: " + game.getPlayers()[i].getScore(),WINDOW_WIDTH - HEADER_FONT.getSize() * 4, STARTING_Y + + IMAGE_SIZE * 2 + PLAYER_SPACING * (i / 2));
            }

        }
    }
    public void setTurn(int turn){
        this.turn = turn;
    }
    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    public void setCurrentNum(int currentNum) {
        this.currentNum = currentNum;
    }

    public void drawBet(Graphics g){
        int startingPoint = WINDOW_WIDTH / 2 - IMAGE_SIZE * currentCount / 2;
        for (int i = 0; i < currentCount; i++){
            g.drawImage(diceImages[currentNum - 1], startingPoint + IMAGE_SIZE * i, STARTING_Y + VISIBLE_HEIGHT / 3 + IMAGE_SIZE / 2, IMAGE_SIZE, IMAGE_SIZE, this);
        }
    }
    public void drawCup(Graphics g){
        g.setFont(HEADER_FONT);
        g.drawString("Player: " + game.getPlayers()[turn].getName() + "'s Turn", WINDOW_WIDTH / 2 - (HEADER_FONT.getSize() * ((15 + game.getPlayers()[turn].getName().length()) / 2) / 2), STARTING_Y + VISIBLE_HEIGHT * 2 / 3);
        int order = 0;
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
}

