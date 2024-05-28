public class Ticket {
    int ticketNumber;
    Integer matchID;

    public Ticket(int ticketNumber, Integer matchID) {
        this.ticketNumber = ticketNumber;
        this.matchID = matchID;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public Integer getMatch() {
        return matchID;
    }
}
