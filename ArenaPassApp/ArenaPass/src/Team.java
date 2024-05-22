public class Team {
    String teamName;
    String teamCity;
    Stadium stadium;
    String logopath;

    public Team(String teamName, String teamCity, Stadium stadium, String logopath) {
        this.teamName = teamName;
        this.teamCity = teamCity;
        this.stadium = stadium;
        this.logopath= logopath;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamCity() {
        return teamCity;
    }

    public Stadium getStadium() {
        return stadium;
    }
}
