package trivia;

import java.util.*;

// REFACTOR ME
public class GameBetter implements IGame {
    public static final int MAX_QUESTIONS_SIZE = 50;
    public static final int RESET_NUMBER = 11;
    int LOOSING_NUMBER = 6;
    private final Map<Integer, PlayerStats> players = new HashMap<>();

    private final Map<QuestionsType, LinkedList<String>> questions = new HashMap<>();

    int currentPlayer = 0;

    public GameBetter() {
        Arrays.stream(QuestionsType.values()).forEach(type -> {
            if (!questions.containsKey(type)) {
                questions.put(type, new LinkedList<>());
            }
            for (int i = 0; i < MAX_QUESTIONS_SIZE; i++) {
                questions.get(type).add(createQuestion(type.getPrintableName(), i));
            }
        });
    }

    public String createQuestion(String type, int index) {
        return type + " " + index;
    }

    public void addPlayer(String playerName) {
        PlayerStats player = new PlayerStats(playerName);
        players.put(players.size(), player);
        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
    }

    public void roll(int roll) {
        PlayerStats player = players.get(currentPlayer);
        System.out.println(player + " is the current player");
        System.out.println("They have rolled a " + roll);
        if (player.isPenaltyBox()) {
            if (roll % 2 != 0) {
                player.gettingOutOfPenalty();
                System.out.println(player.getPlayerName() + " is getting out of the penalty box");
                makeMove(roll, player);
            } else {
                player.beingLeftInPenalty();
                System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
            }
        } else {
            makeMove(roll, player);
        }
    }

    private void makeMove(int roll, PlayerStats player) {
        player.updatePlace(roll);
        if (player.getPlace() > RESET_NUMBER) {
            player.rollBackPlayer();
        }
        System.out.println(player.getPlayerName()
                + "'s new location is "
                + player.getPlace());
        System.out.println("The category is " + currentCategory().getName());
        askQuestion();
    }

    private void askQuestion() {
        System.out.println(questions.get(currentCategory()).removeFirst());
    }

    private QuestionsType currentCategory() {
        switch (players.get(currentPlayer).getPlace()) {
            case 0, 4, 8 -> {
                return QuestionsType.POP;
            }
            case 1, 5, 9 -> {
                return QuestionsType.SCIENCE;
            }
            case 2, 6, 10 -> {
                return QuestionsType.SPORTS;
            }
            default -> {
                return QuestionsType.ROCK;
            }
        }
    }

    public boolean wasCorrectlyAnswered() {
        PlayerStats player = players.get(currentPlayer);
        if (player.isPenaltyBox()) {
            if (player.isGettingOut()) {
                return correctAnswerGiven(player);
            } else {
                setNextPlayer();
                return true;
            }
        } else {
            return correctAnswerGiven(player);
        }
    }

    private void setNextPlayer() {
        currentPlayer++;
        if (currentPlayer == players.size()) {
            currentPlayer = 0;
        }
    }

    private boolean correctAnswerGiven(PlayerStats player) {
        System.out.println("Answer was correct!!!!");
        player.updatePurses();
        System.out.println(players.get(currentPlayer)
                + " now has "
                + player.getPurses()
                + " Gold Coins.");

        boolean winner = didPlayerWin();
        setNextPlayer();

        return winner;
    }

    public boolean wrongAnswer() {
        PlayerStats player = players.get(currentPlayer);
        System.out.println("Question was incorrectly answered");
        System.out.println(player + " was sent to the penalty box");
        player.putToPenaltyBox();
        setNextPlayer();
        return true;
    }


    private boolean didPlayerWin() {
        PlayerStats player = players.get(currentPlayer);
        return player.getPurses() != LOOSING_NUMBER;
    }
}
