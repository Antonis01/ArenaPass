import java.util.Date;

public class Fan extends Account{
    int accState;

    public Fan(int accID,String username,String firstName, String lastName, String phoneNumber, String homeAddress, String city, int ID, String emailAddress, Date birthDate, int accState) {
        super(accID,username,firstName, lastName, phoneNumber, homeAddress, city, ID, emailAddress, birthDate);
        this.accState = accState;
    }


    public int getAccState() {
        return accState;
    }

    public String getUsername(){
        return super.username;
    }


}
