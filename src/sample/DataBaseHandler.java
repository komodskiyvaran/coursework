package sample;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends Configs { // ����� �������� �� ����������� � �� --- ������/������
    // ���������� ������� ������ ��� ���� � ��
    Connection dbConnection; //

    public Connection getDbConnection()
            throws ClassNotFoundException, SQLException{
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?verifyServerCertificate=false"+
                "&useSSL=false"+        //jdbc ������ ��������� ������������ � ��
                "&requireSSL=false"+
                "&useLegacyDatetimeCode=false"+
                "&amp"+
                "&serverTimezone=UTC"; // ����� ������� ������� ��� ������������ � ��
        Class.forName("com.mysql.cj.jdbc.Driver"); // ����� �� ���������� �������

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass); //������� �������� �������� �� ����� � ������ �� ��

        return dbConnection;
    }

    public void singUpUser(User user){      // ���������� ����� � ��
        String insert = "INSERT INTO " + Const.USER_TABLE + " (" +  // sql zapros  - ������� ����� ������ � �������
            Const.USERS_FIRSTNAME + "," + Const.USERS_LASTNAME + "," +
            Const.USERS_USERNAME + "," + Const.USERS_PASSWORD + "," +
            Const.USERS_GROUPNAME + "," + Const.USERS_RESULT + "," +
            Const.USERS_DATETIME + ")" + " VALUES (?,?,?,?,?,?,?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert); // ����� ����� ��������� ������ "?"
            prSt.setString(1, user.getFirstName());
            prSt.setString(2, user.getLastName());
            prSt.setString(3, user.getUserName());
            prSt.setString(4, user.getPassword());
            prSt.setString(5, user.getGroup());
            prSt.setString(6, user.getUserResult());
            prSt.setString(7, user.getDateTime());

            prSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setUserNow(User user){ // ������������� ����� ������
        String insert = " "; //������
    }

    public ResultSet getUser(User user){
        ResultSet resSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE +" WHERE " +
                Const.USERS_USERNAME + "=? AND " +Const.USERS_PASSWORD + "=?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, user.getUserName());
            prSt.setString(2, user.getPassword());

            resSet = prSt.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public List<User> getAllUsers(){
        List<User> users = new ArrayList<>();

        String select = "SELECT * FROM " + Const.USER_TABLE;
               // +" WHERE " +Const.USERS_FIRSTNAME + "=? AND " + Const.USERS_LASTNAME + "=?" + Const.USERS_USERNAME + "=?" +
               // Const.USERS_PASSWORD + "=?" + Const.USERS_GROUPNAME + "=?" + Const.USERS_RESULT + "=?" + Const.USERS_DATETIME + "=?";

        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet rs = prSt.executeQuery();


            while (rs.next()){
                String fname = rs.getString(Const.USERS_FIRSTNAME);
                String lname = rs.getString(Const.USERS_LASTNAME);
                String uname= rs.getString(Const.USERS_USERNAME);
                String pass = rs.getString(Const.USERS_PASSWORD);
                String gr = rs.getString(Const.USERS_GROUPNAME);
                String result = rs.getString(Const.USERS_RESULT);
                String data = rs.getString(Const.USERS_DATETIME);

                users.add(new User(fname, lname, uname, pass, gr, result, data));
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }
}