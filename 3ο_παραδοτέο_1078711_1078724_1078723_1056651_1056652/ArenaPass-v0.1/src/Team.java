public class Team {
    String teamName;
    String teamCity;
    Integer stadiumID;
    String logopath;

    public Team(String teamName, String teamCity, Integer stadium, String logopath) {
        this.teamName = teamName;
        this.teamCity = teamCity;
        this.stadiumID = stadium;
        this.logopath= logopath;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamCity() {
        return teamCity;
    }

    public Integer getStadium() {
        return stadiumID;
    }
}
