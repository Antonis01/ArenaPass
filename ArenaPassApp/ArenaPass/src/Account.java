import java.util.Date;

public class Account {
    String firstName;
    String username;
    String lastName;
    String phoneNumber;
    String homeAddress;
    String city;
    String ID;
    String emailAddress;
    String password;
    Date birthDate;


    public Account(String username,String firstName, String lastName, String phoneNumber, String homeAddress, String city, String ID, String emailAddress, String password, Date birthDate) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.homeAddress = homeAddress;
        this.city = city;
        this.ID = ID;
        this.emailAddress = emailAddress;
        this.password = password;
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
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

    public String getID() {
        return ID;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }
}
