import java.util.Date;

public class Fan extends Account{
    int accState;
    int fanPassID

    public Fan(int fanPassID,int accID,String username,String firstName, String lastName, String phoneNumber, String homeAddress, String city, int ID, String emailAddress, Date birthDate, int accState) {
        super(accID,username,firstName, lastName, phoneNumber, homeAddress, city, ID, emailAddress, birthDate);
        this.fanPassID=fanPassID;
        this.accState = accState;
    }

    public int getFanPassID() {
        return fanPassID;
    }

    public int getAccState() {
        return accState;
    }

    public String getUsername(){
        return super.username;
    }


}
