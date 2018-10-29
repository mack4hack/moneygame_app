package bidding.example.com.bidding.GetterSetter;

/**
 * Created by Gaurav on 29/03/16.
 */
public class CancelledGetSet {

    String trans_id, match_name, game, chips;

    public String getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(String trans_id) {
        this.trans_id = trans_id;
    }

    public String getMatch_name() {
        return match_name;
    }

    public void setMatch_name(String match_name) {
        this.match_name = match_name;
    }
    //get game object
    public String getGame() {
        return game;
    }
    //set game object
    public void setGame(String game) {
        this.game = game;
    }
    //get chips
    public String getChips() {
        return chips;
    }
    //set chips
    public void setChips(String chips) {
        this.chips = chips;
    }


}
