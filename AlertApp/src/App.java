import java.util.Scanner;
import java.sql.*;
//this is the main file, inherits from the User Action
public class App extends UserAction{
    //environment variables that consist of database
    static final String  user=System.getenv("mySQL_user");
    static final String password=System.getenv("mySQL_pw");
    static final String url="jdbc:mysql://localhost/SMSAlertDB";
    public static void main (String[] args){
        Scanner input = new Scanner(System.in);
        try { //load the jdbc mysql driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("COMPLETE: Driver Loaded");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error: unable to load driver class!");
            System.exit(1);
        }
        UserAction curruser;
        System.out.println("Welcome To Alert App\nSelect Option\nA. Sign Up\nB. Log In\nAny Key To Exit App.");
        switch(input.next()){
            case("A"):
            curruser=signUp(input);
            curruser.showMenu(); 
            break;
            case("B"):
            curruser=logIn(input);
            curruser.showMenu();
            break;
            default:
            System.out.println("App Exited. Good Bye");
            break;
        }
        //curruser.showMenu();
        // 
        input.close();
    }
    public static UserAction signUp(Scanner input){
        String ufn, uln, uem, upn, SQL; // user info 
        try (Connection con = DriverManager.getConnection(url,user,password)) { //create connection with databse
            System.out.println("Sign Up Window\nEnter information for following fields\nFirst Name: ");
            ufn = input.next();
            System.out.println("Last Name: ");
            uln = input.next();
            System.out.println("Email: ");
            uem = input.next();
            System.out.println("Phone Number: ");
            upn = input.next();
            System.out.println("Welcome To AlertMe");

            Statement stmt = con.createStatement();
            SQL = "INSERT INTO userinfo VALUES (NULL, '" + ufn + "', '" + uln + "','" + uem + "', '+1" + upn + "')"; //query to insert new account
            stmt.executeUpdate(SQL); //executes the query
            UserAction ua= new UserAction(ufn, uln);
            System.out.println("Registration Complete!");
            return ua;
        } catch (SQLException e) {
            // e.printStackTrace();
            System.out.println("SQL EXCEPTION");
        }
        //if the first return didnt work
        UserAction invaliduser =new UserAction();
        return invaliduser;
    }
    public static UserAction logIn(Scanner input){
        String ufn, uln, uem, upn, SQL;
        try (Connection con = DriverManager.getConnection(url,user,password)) { //create connection with databse
            System.out.println("Log In Window\nEnter your information for following fields\nEmail: ");
            uem= input.next();
            System.out.println("Phone Number: ");
            upn= input.next();
            // SQL = "SELECT fname, lname FROM userinfo WHERE email= ? AND userphone= ?"; //query to find existing account and log in
            SQL = "SELECT `fname`, `lname` FROM `userinfo` WHERE `email`= ? AND `userphone`= ?";
            PreparedStatement stmt = con.prepareStatement(SQL);
            stmt.setString(1, uem);
            stmt.setString(2, "+1"+upn);
            // DEBUG System.out.println("STATEMENT WORKED");
            ResultSet rs= stmt.executeQuery(); //executes the query
            // DEBUG System.out.println("QUERY WORKED");
            while(rs.next()){
                // DEBUG System.out.println("IN WHILE LOOP");
                ufn = rs.getString("fname");
                uln = rs.getString("lname");
                UserAction ua= new UserAction(ufn,uln);
                System.out.println("Log In Complete!");
                return ua;
            }
        } catch (SQLException e) {
            // e.printStackTrace();
            System.out.println("SQL EXCEPTION");
        }
        //if the first return didnt work
        UserAction invaliduser =new UserAction();
        return invaliduser;
    }
}
