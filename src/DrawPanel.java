import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.Font;

class DrawPanel extends JPanel implements MouseListener {
    private Deck d;
    private Card currdentCard;
    private Card[][] grid;
    private ArrayList<Card> selectedCards;
    private Rectangle replaceCardsButton;
    private Rectangle resetGameButton;
    private Boolean gameStatus;

    private ArrayList<Animation> animations = new ArrayList<>();

    public DrawPanel() {
        grid = new Card[3][3];
        d = new Deck();
        selectedCards = new ArrayList<>();
        replaceCardsButton = new Rectangle(280, 50, 150, 50);
        resetGameButton = new Rectangle(280, 120, 150, 50);
        gameStatus = true;

        refreshGrid();
        //currdentCard = d.getRandomCard();

        this.addMouseListener(this);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 50;
        int y = 10;
        int numOfShownCards = 0;
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 3; k++) {
                if (grid[j][k] != null) {
                    numOfShownCards += 1;
                }
            }
        }

        boolean winnable = false;
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 3; k++) {
                Card c1 = grid[j][k];

                for (int o = 0; o < 3; o++) {
                    for (int p = 0; p < 3; p++) {

                        if (j != o || k != p) {
                            Card c2 = grid[o][p];
                            if (!(c1 == null) && !(c2 == null)) {
                                if (c1.getValue() + c2.getValue() == 11) {
                                    winnable = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        boolean containsJack = false;
        boolean containsQueen = false;
        boolean containsKing = false;
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 3; k++) {
                if (grid[j][k] != null) {
                    if (grid[j][k].getValue() == 15) {
                        containsJack = true;
                    }
                    if (grid[j][k].getValue() == 16) {
                        containsQueen = true;
                    }
                    if (grid[j][k].getValue() == 17) {
                        containsKing = true;
                    }
                }
            }
        }
        if (containsJack && containsQueen && containsKing) {
            winnable = true;
        }

        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 3; k++) {
                if (grid[j][k] != null) {
                    g.drawImage(grid[j][k].getImage(), x, y, null);
                    grid[j][k].setHitbox(new Rectangle(x, y, grid[j][k].getHitbox().width,  grid[j][k].getHitbox().height));

                    if (grid[j][k].getShowHitbox()) {
                        g.drawRect(x, y, grid[j][k].getHitbox().width, grid[j][k].getHitbox().height);
                    }
                }
                x += 80;
            }
            y += 80;
            x = 50;
        }

        for (int i = animations.size() - 1; i >= 0; i--) {
            if (animations.get(i).isFinished()) {
                if (animations.get(i).isGrid()) {
                    int gridX = (int) (animations.get(i).getCurrentX() - 50) / 80;
                    int gridY = (int) (animations.get(i).getCurrentY() - 10) / 80;
                    grid[gridY][gridX] = animations.get(i).getCard();
                }
                animations.remove(i);
            }
        }
        for (Animation a : animations) {
            g.drawImage(a.getCard().getImage(), (int) a.getCurrentX(), (int) a.getCurrentY(), null);
        }

        if (animations.isEmpty()) {
            if ((gameStatus == false || winnable == false) && numOfShownCards != 0) {
                g.drawString("YOU LOSE, CLICK TO PLAY AGAIN", 100, 258);
            }
        }
        if (numOfShownCards == 0) {
            g.drawString("CONGRATS YOU WON", 100, 280);
        }
        g.drawRect(replaceCardsButton.x, replaceCardsButton.y, replaceCardsButton.width, replaceCardsButton.height);
        g.drawString("REPLACE CARDS", 300, 75);
        g.drawRect(resetGameButton.x, resetGameButton.y, resetGameButton.width, resetGameButton.height);
        g.drawString("PLAY AGAIN", 300, 150);
        g.drawString("Cards Left: " + d.getDeck().size(), 100, 300);

        int displayX = 0;
        int displayY = 330;
        for (Card c : d.getDeck()) {
            g.drawImage(c.getImage(), displayX, displayY, null);
            displayX += 20;
            if (displayX > 430 & displayY == 330) {
                displayX = 0;
                displayY += 40;
            }
        }
    }

    public void refreshGrid() {
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 3; k++) {
                grid[j][k] = d.getRandomCard();
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        Point p = e.getPoint();
        int button = e.getButton();

        if (button == 1) {
            if (selectedCards.size() != 0 && replaceCardsButton.contains(p)) {
                int sum = 0;
                if (selectedCards.size() < 3) {
                    for (Card c : selectedCards) {
//                        System.out.println(c);
//                        System.out.println(c.getValue());
                        sum += c.getValue();
                    }
                    System.out.println(sum);
                }

                boolean containsJack = false;
                boolean containsQueen = false;
                boolean containsKing = false;
                boolean selectedTriple = false;
                for (Card c : selectedCards) {
                    if (c.getValue() == 15) {
                        containsJack = true;
                    }
                    if (c.getValue() == 16) {
                        containsQueen = true;
                    }
                    if (c.getValue() == 17) {
                        containsKing = true;
                    }
                }
                System.out.println(containsJack + " " + containsQueen + " " + containsKing);
                if (containsJack && containsQueen && containsKing) {
                    selectedTriple = true;
                }
                if (sum == 11 || selectedTriple) {
                    for (Card c : selectedCards) {

                        for (int j = 0; j < 3; j++) {
                            for (int k = 0; k < 3; k++) {
                                if (grid[j][k] != null) {
                                    if (c.equals(grid[j][k])) {
                                        animations.add(new Animation(c, grid[j][k].getHitbox().x, grid[j][k].getHitbox().y, 460, 430, 500, false));

                                        if (d.getDeck().size() > 0) {
                                            int n = (int) (Math.random() * d.getDeck().size());
                                            Card randomCard = d.getCard(n);
                                            int endX = k * 80 + 50;
                                            int endY = j * 80 + 10;
                                            animations.add(new Animation(randomCard, (n * 20) % 430, 330, endX, endY, 500, true));
                                            d.removeCard(n);
                                        }
                                        grid[j][k] = null;
                                    }
                                }
                            }
                        }
                    }
                    selectedCards = new ArrayList<>();
                }
            }
            if (resetGameButton.contains(p)) {
                animations.clear();
                gameStatus = true;
                d = new Deck();
                selectedCards = new ArrayList<>();
                refreshGrid();
            }
        }

        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 3; k++) {
                if (button == 3) {
                    if (grid[j][k] != null) {
                        if (grid[j][k].getHitbox().contains(p)) {
                            if (selectedCards.contains(grid[j][k])) {
                                selectedCards.remove(grid[j][k]);
                            } else {
                                selectedCards.add(grid[j][k]);
                            }
                            grid[j][k].setShowHitbox(!grid[j][k].getShowHitbox());
                        }
                    }
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}