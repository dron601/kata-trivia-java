package trivia;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
public class PlayerStats {

    public PlayerStats(String playerName) {
        this.playerName = playerName;
    }

    private int place;
    private int purses;
    private boolean penaltyBox;
    private boolean isGettingOut;
    private String playerName;


    public void updatePlace(int updateNumber) {
        this.place += updateNumber;
    }

    public void rollBackPlayer() {
        this.place -= 12;
    }

    public void updatePurses() {
        this.purses++;
    }

    public void removeFromPenaltyBox() {
        this.penaltyBox = false;
    }

    public void putToPenaltyBox() {
        this.penaltyBox = true;
    }

    public void gettingOutOfPenalty() {
        this.isGettingOut = true;
    }

    public void beingLeftInPenalty() {
        this.isGettingOut = false;
    }

    @Override
    public String toString() {
        return this.playerName;
    }

}
