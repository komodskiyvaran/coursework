package sample;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends Configs { // Класс отвечает за подключение к БД --- запись/чтение
    // наследуюем конфигс потому что инфа о бд
    Connection dbConnection; // тут установится наш коннекшн

    public Connection getDbConnection()
            throws ClassNotFoundException, SQLException{
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?verifyServerCertificate=false"+
                "&useSSL=false"+        //jdbc плагин позволяет подключаться к бд
                "&requireSSL=false"+
                "&useLegacyDatetimeCode=false"+
                "&amp"+
                "&serverTimezone=UTC"; // ТЕКСТ КОТОРЫЙ ПОМОЖЕТ НАМ ПОДКЛЮЧИТЬСЯ К БД
        Class.forName("com.mysql.cj.jdbc.Driver"); // какой мы используем драйвер

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass); //КОНЕКШН ПЕРЕДАЕМ НАЗВАНИЕ БД ЛОГИН И ПАРОЛЬ ОТ БД

        return dbConnection;
    }

    public void singUpUser(User user){      // записываем юзера в бд
        String insert = "INSERT INTO " + Const.USER_TABLE + " (" +  // sql zapros  - Вставка новой строки в таблицу
            Const.USERS_FIRSTNAME + "," + Const.USERS_LASTNAME + "," +
            Const.USERS_USERNAME + "," + Const.USERS_PASSWORD + "," +
            Const.USERS_GROUPNAME + "," + Const.USERS_RESULT + "," +
            Const.USERS_DATETIME + ")" + " VALUES (?,?,?,?,?,?,?)";
            // USERT INTO users (firstname,lastname,username,password,groupname,userresult,datatime) + VALUES (?,?,?,?,?,?,?)
        try {
            //PREPARED STATEMENT - для отпарвки данных
            PreparedStatement prSt = getDbConnection().prepareStatement(insert); // чичас будем вставлять данные в "?"
            prSt.setString(1, user.getFirstName());
            prSt.setString(2, user.getLastName());
            prSt.setString(3, user.getUserName());
            prSt.setString(4, user.getPassword());
            prSt.setString(5, user.getGroup());
            prSt.setInt(6, user.getUserResult());
            prSt.setString(7, user.getDateTime());

            prSt.executeUpdate(); // внести изменения
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getUser(User user){
        ResultSet resSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE +" WHERE " +
                Const.USERS_USERNAME + "=? AND " +Const.USERS_PASSWORD + "=?";
            // SELECT * FROM user WHERE username =? AND password=?
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

    public void setUserNow(User user){ // устанавливаем юзера сейчас
        String select = "SELECT " + Const.USERS_FIRSTNAME + ", " + Const.USERS_LASTNAME + ", " +
                Const.USERS_GROUPNAME + ", " + Const.USERS_RESULT + ", " + Const.USERS_DATETIME +
                " FROM " + Const.USER_TABLE + " WHERE " + Const.USERS_USERNAME + "='" + user.getUserName() + "' AND " +
                Const.USERS_PASSWORD + "='" + user.getPassword() + "'";
          // SELECT firstname, lastname, groupname, userresult, datetime FROM user WHERE username ='user.getUserName()'
        // AND password='user.getPassword()''

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet rs = prSt.executeQuery();

            while (rs.next()){
                user.setFirstName(rs.getString(Const.USERS_FIRSTNAME));
                user.setLastName(rs.getString(Const.USERS_LASTNAME));
                user.setGroup(rs.getString(Const.USERS_GROUPNAME));
                user.setUserResult(rs.getInt(Const.USERS_RESULT));
                user.setDateTime(rs.getString(Const.USERS_DATETIME));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // Во временный объект user мы записали пользователя в данный моменты
        // Перепишим его данные в USER_NOW который хранится в классе Const
        Const.USER_NOW = new User(user.getFirstName(), user.getLastName(), user.getUserName(), user.getPassword(), user.getGroup(), user.getUserResult(), user.getDateTime());
        System.out.println(Const.USER_NOW.showUsersResult());
    }

    public List<User> getAllUsers(){
        List<User> users = new ArrayList<>();

        String select = "SELECT * FROM " + Const.USER_TABLE;

        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet rs = prSt.executeQuery();


            while (rs.next()){
                String fname = rs.getString(Const.USERS_FIRSTNAME);
                String lname = rs.getString(Const.USERS_LASTNAME);
                String uname= rs.getString(Const.USERS_USERNAME);
                String pass = rs.getString(Const.USERS_PASSWORD);
                String gr = rs.getString(Const.USERS_GROUPNAME);
                int result = rs.getInt(Const.USERS_RESULT);
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

    public void setNewResultAndDateForUser(String newDate, int newResult){

        String insert = "UPDATE " + Const.USER_TABLE + " SET " +  // sql zapros  - Вставка новой строки в таблицу
                Const.USERS_RESULT + "=?, " +
                Const.USERS_DATETIME + "=? " + " WHERE " + Const.USERS_USERNAME + "=? AND " +
                Const.USERS_PASSWORD + "=?";
        // UPDATE user SET userresult=? AND datetime=? WHERE username='Const.USER_NOW.getUserName' AND password='Const.USER_NOW.getPassword'

        try {
            //PREPARED STATEMENT - для отпарвки данных
            PreparedStatement updatePS = getDbConnection().prepareStatement(insert); // чичас будем вставлять данные в "?"


            updatePS.setInt(1, newResult);
            updatePS.setString(2, newDate);
            updatePS.setString(3, Const.USER_NOW.getUserName());
            updatePS.setString(4, Const.USER_NOW.getPassword());


            updatePS.executeUpdate(); // внести изменения
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}