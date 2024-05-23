import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Match {
    String homeTeam;
    String awayTeam;
    int stadiumID;

    public Match(String homeTeam, String awayTeam, int stadiumID) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.stadiumID = stadiumID;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getStadiumID() {
        return stadiumID;
    }
}
