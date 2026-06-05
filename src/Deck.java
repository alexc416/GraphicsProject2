import java.util.ArrayList;
import java.util.Arrays;

public class Deck {
    private ArrayList<Card> deck = new ArrayList<>();

    public Deck() {
        String[] suits = {"clubs", "diamonds", "hearts", "spades"};
        String[] vals = {"A", "02", "03", "04", "05", "06", "07", "08", "09", "10", "J", "Q", "K"};
        //String[] vals = {"A", "10", "06", "05", "J", "Q", "K"};
        for (String s : suits) {
            for (String v : vals) {
                deck.add(new Card(s, v));
            }
        }
    }

    public Card getCard(int n) {
        return deck.get(n);
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void removeCard(int n) {
        deck.remove(n);
    }

    public Card getRandomCard() {
        Card out;
        if (deck.size() > 0) {
            int n = (int) (Math.random() * deck.size());
            out = deck.get(n);
            deck.remove(n);
        } else {
            out = null;
        }
        return out;
    }

}
