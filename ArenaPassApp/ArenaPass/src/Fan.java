import java.util.Date;

public class Fan extends Account{
    boolean isApproved;

    public Fan(String username,String firstName, String lastName, String phoneNumber, String homeAddress, String city, String ID, String emailAddress, String password, Date birthDate, boolean isApproved) {
        super(username,firstName, lastName, phoneNumber, homeAddress, city, ID, emailAddress, password, birthDate);
        this.isApproved = isApproved;
    }


    public boolean isApproved() {
        return isApproved;
    }
}
