public class Ticket {
    int ticketNumber;
    Match match;

    public Ticket(int ticketNumber, Match match) {
        this.ticketNumber = ticketNumber;
        this.match = match;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public Match getMatch() {
        return match;
    }
}
