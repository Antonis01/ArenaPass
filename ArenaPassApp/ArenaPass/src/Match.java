import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Match {
    String homeTeam;
    String awayTeam;
    int stadiumID;
    Date matchDate;
    Time matchTime;

    public Match(String homeTeam, String awayTeam, int stadiumID, Date matchDate, Time matchTime) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.stadiumID = stadiumID;
        this.matchDate = matchDate;
        this.matchTime = matchTime;
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

    public Date getMatchDate() {
        return matchDate;
    }

    public Time getMatchTime() {
        return matchTime;
    }
}
