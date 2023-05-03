package sample;

public class User {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String group;
    private String userResult;
    private String dateTime;

    public User(String firstName, String lastName, String userName, String password, String group, String userResult, String dateTime) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.group = group;
        this.userResult = userResult;
        this.dateTime = dateTime;
    }

    public User(String firstName, String lastName, String userName, String password, String group) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.group = group;
    }

    public User() {
    }

    public String getUserResult() {
        return userResult;
    }

    public void setUserResult(String userResult) {
        this.userResult = userResult;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String showUsersResult(){
        return getFirstName()+ "\t\t" + getLastName() + "\t\t" + getDateTime() + "\t" + "\t" + getGroup() + "\n";
    }
}
