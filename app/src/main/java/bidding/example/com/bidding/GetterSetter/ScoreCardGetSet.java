package bidding.example.com.bidding.GetterSetter;

/**
 * Created by root on 3/11/16.
 */
public class ScoreCardGetSet {

    String player_id;
    String player_name;
    String runs;
    String balls;
    String strikerate;

    public String getStrikerate() {
        return strikerate;
    }

    public void setStrikerate(String strikerate) {
        this.strikerate = strikerate;
    }

    public String getBalls() {
        return balls;
    }

    public void setBalls(String balls) {
        this.balls = balls;
    }

    public String getRuns() {
        return runs;
    }

    public void setRuns(String runs) {
        this.runs = runs;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public String getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(String player_id) {
        this.player_id = player_id;
    }

}
