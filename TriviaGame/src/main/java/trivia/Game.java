package trivia;

import java.util.ArrayList;
import java.util.List;

// REFACTORED: responsibilities moved to Player and QuestionDeck
public class Game implements IGame {
    private final List<Player> players = new ArrayList<>();
    private final QuestionDeck deck = new QuestionDeck();

    private int currentPlayer = 0;
    private boolean isGettingOutOfPenaltyBox;

    public Game() {
    }


    public boolean add(String playerName) {
        Player p = new Player(playerName);
        players.add(p);

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        Player p = players.get(currentPlayer);
        System.out.println(p.getName() + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (p.isInPenaltyBox()) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                System.out.println(p.getName() + " is getting out of the penalty box");
                p.moveBy(roll);

                System.out.println(p.getName() + "'s new location is " + p.getPlace());
                System.out.println("The category is " + currentCategory(p.getPlace()));
                askQuestion(p.getPlace());
            } else {
                System.out.println(p.getName() + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {
            p.moveBy(roll);

            System.out.println(p.getName() + "'s new location is " + p.getPlace());
            System.out.println("The category is " + currentCategory(p.getPlace()));
            askQuestion(p.getPlace());
        }

    }

    private void askQuestion(int place) {
        System.out.println(deck.next(currentCategory(place)));
    }

    private String currentCategory(int place) {
        int idx = place - 1;
        if (idx == 0 || idx == 4 || idx == 8) return "Pop";
        if (idx == 1 || idx == 5 || idx == 9) return "Science";
        if (idx == 2 || idx == 6 || idx == 10) return "Sports";
        return "Rock";
    }

    public boolean handleCorrectAnswer() {
        Player p = players.get(currentPlayer);
        if (p.isInPenaltyBox()) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println("Answer was correct!!!!");
                p.incrementPurse();
                System.out.println(p.getName() + " now has " + p.getPurse() + " Gold Coins.");

                boolean winner = didPlayerWin(p);
                nextPlayer();

                return winner;
            } else {
                nextPlayer();
                return true;
            }

        } else {

            System.out.println("Answer was correct!!!!");
            p.incrementPurse();
            System.out.println(p.getName() + " now has " + p.getPurse() + " Gold Coins.");

            boolean winner = didPlayerWin(p);
            nextPlayer();

            return winner;
        }
    }

    public boolean wrongAnswer() {
        Player p = players.get(currentPlayer);
        System.out.println("Question was incorrectly answered");
        System.out.println(p.getName() + " was sent to the penalty box");
        p.setInPenaltyBox(true);

        nextPlayer();
        return true;
    }

    private void nextPlayer() {
        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
    }

    private boolean didPlayerWin(Player p) {
        return !(p.getPurse() == 6);
    }
}
