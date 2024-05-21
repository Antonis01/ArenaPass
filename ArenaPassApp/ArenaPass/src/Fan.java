public class Fan extends Account{
    boolean isApproved;
    public Fan(String firstName, String lastName, String phoneNumber, String homeAddress, String city, String ID, String emailAddres, String password, boolean isApproved) {
        super(firstName, lastName, phoneNumber, homeAddress, city, ID, emailAddres, password);
        this.isApproved=isApproved;
    }

    public boolean isApproved() {
        return isApproved;
    }
}
