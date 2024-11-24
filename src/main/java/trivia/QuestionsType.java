package trivia;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QuestionsType {

    POP("Pop", "Pop Question"),
    SCIENCE("Science", "Science Question"),
    SPORTS("Sports", "Sports Question"),
    ROCK("Rock", "Rock Question");

    private final String name;
    private final String printableName;

}
