public class Team {
    String teamName;
    String teamCity;
    Stadium stadium;

    public Team(String teamName, String teamCity, Stadium stadium) {
        this.teamName = teamName;
        this.teamCity = teamCity;
        this.stadium = stadium;
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
