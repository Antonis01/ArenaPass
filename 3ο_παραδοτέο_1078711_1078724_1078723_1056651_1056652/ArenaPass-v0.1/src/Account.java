import java.util.Date;

public class Account {
    int accID;
    String firstName;
    String username;
    String lastName;
    String phoneNumber;
    String homeAddress;
    String city;
    int ID;
    String emailAddress;
    String password=null;
    Date birthDate;


    public Account(int accID,String username,String firstName, String lastName, String phoneNumber, String homeAddress, String city, int ID, String emailAddress,Date birthDate) {
        this.accID = accID;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.homeAddress = homeAddress;
        this.city = city;
        this.ID = ID;
        this.emailAddress = emailAddress;
        this.birthDate = birthDate;
    }

    public int getAccID() {
        return accID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getUsername() {
        return username;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public String getCity() {
        return city;
    }

    public int getID() {
        return ID;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public Date getBirthDate() {
        return birthDate;
    }
}
