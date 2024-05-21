import java.time.format.DateTimeFormatter;

public class Match {
    Team homeTeam;
    Team awayTeam;
    Stadium stadium;
    DateTimeFormatter matchDateTime;

    public Match(Team homeTeam, Team awayTeam, Stadium stadium, DateTimeFormatter matchDateTime) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.stadium = stadium;
        this.matchDateTime = matchDateTime;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public Stadium getStadium() {
        return stadium;
    }

    public DateTimeFormatter getMatchDateTime() {
        return matchDateTime;
    }
}
