package trivia;

import java.util.LinkedList;

public class QuestionDeck {
    private final LinkedList<String> popQuestions = new LinkedList<>();
    private final LinkedList<String> scienceQuestions = new LinkedList<>();
    private final LinkedList<String> sportsQuestions = new LinkedList<>();
    private final LinkedList<String> rockQuestions = new LinkedList<>();

    public QuestionDeck() {
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast("Science Question " + i);
            sportsQuestions.addLast("Sports Question " + i);
            rockQuestions.addLast("Rock Question " + i);
        }
    }

    public String next(String category) {
        if ("Pop".equals(category)) return popQuestions.removeFirst();
        if ("Science".equals(category)) return scienceQuestions.removeFirst();
        if ("Sports".equals(category)) return sportsQuestions.removeFirst();
        return rockQuestions.removeFirst();
    }
}
