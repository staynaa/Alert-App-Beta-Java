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
        String uaopt;
        System.out.println("Welcome To Alert App\nSelect Option\nA. Sign Up\nB. Log In\nAny Key To Exit App.");
        switch(input.next()){
            case("A"):
            curruser=signUp(input);
            curruser.showMenu(); 
            uaopt=option(input);
            while(!uaopt.equals("E")){
                choice(curruser,uaopt);
                curruser.showMenu(); 
                uaopt=option(input);
            }
            break;
            case("B"):
            curruser=logIn(input);
            curruser.showMenu(); 
            uaopt=option(input);
            while(!uaopt.equals("E")){
                choice(curruser,uaopt);
                curruser.showMenu(); 
                uaopt=option(input);
            }
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
        String ufn, uln, uem, upn, SQL, SQL2; // user info 
        int usID;
        try (Connection con = DriverManager.getConnection(url,user,password)) { //create connection with databse
            System.out.println("Sign Up Window\nEnter information for following fields\nFirst Name: ");
            ufn = input.next();
            System.out.println("Last Name: ");
            uln = input.next();
            System.out.println("Email: ");
            uem = input.next();
            System.out.println("Phone Number: ");
            upn = input.next();
            // System.out.println("Welcome To AlertMe");

            Statement stmt = con.createStatement();
            SQL = "INSERT INTO userinfo VALUES (NULL, '" + ufn + "', '" + uln + "','" + uem + "', '+1" + upn + "')"; //query to insert new account
            stmt.executeUpdate(SQL); //executes the query insert
            //System.out.println("------------>first query done ");
            SQL2 = "SELECT `usid` FROM `userinfo` WHERE `email`=?"; //query to get id
            
            PreparedStatement stmt2 = con.prepareStatement(SQL2);
            stmt2.setString(1, uem);
            ResultSet rs= stmt2.executeQuery(); //executes the query
            // DEBUG System.out.println("QUERY WORKED");
            //System.out.println("------------>second query done ");
            while(rs.next()){
                //System.out.println("IN WHILE LOOP");
                usID= rs.getInt("usid");
                UserAction ua= new UserAction(ufn, uln, con, usID);
                System.out.println("Registration Complete!");
                return ua;
            }
        } catch (SQLException e) {
            // e.printStackTrace();
            System.out.println("SQL EXCEPTION in sign up");
        }
        //if the first return didnt work
        UserAction invaliduser =new UserAction();
        return invaliduser;
    }
    public static UserAction logIn(Scanner input){
        String ufn, uln, uem, upn, SQL;
        int usID;
        try (Connection con = DriverManager.getConnection(url,user,password)) { //create connection with databse
            System.out.println("Log In Window\nEnter your information for following fields\nEmail: ");
            uem= input.next();
            System.out.println("Phone Number: ");
            upn= input.next();
            // SQL = "SELECT fname, lname FROM userinfo WHERE email= ? AND userphone= ?"; //query to find existing account and log in
            SQL = "SELECT `fname`, `lname`, `usid` FROM `userinfo` WHERE `email`= ? AND `userphone`= ?";
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
                usID= rs.getInt("usid");
                UserAction ua= new UserAction(ufn,uln, con, usID);
                System.out.println("Log In Complete!");
                return ua;
            }
        } catch (SQLException e) {
            // e.printStackTrace();
            System.out.println("SQL EXCEPTION in log in");
        }
        //if the first return didnt work
        UserAction invaliduser =new UserAction();
        return invaliduser;
    }
    public static String option(Scanner input){
        String opt= input.next();
        return opt;
    }
    public static void choice(UserAction ua, String opt){
        switch(opt){
            case("A"):
                addEmergencyContact(ua.userID);
                break;
            case("B"):
                ua.quickAlert();
                break;
            case ("C"):
                ua.alertContact();
                break;
        }
    }

    public static void addEmergencyContact(int usid) {
        System.out.println("checking if id works......id:"+usid);
        Scanner in= new Scanner(System.in);
        String cfname,clname,contphone, SQL;
        System.out.println("This is where you add an emergency contact");
        try(Connection con = DriverManager.getConnection(url,user,password)){
            System.out.println("Enter information for following fields\nContact's First Name: ");
            cfname = in.next();
            System.out.println("Contact's Last Name: ");
            clname = in.next();
            System.out.println("Contact's Phone Number: ");
            contphone = in.next();
            System.out.println("Registering Emergency Contact.....");
            //in.close();
            SQL= "INSERT INTO `emergencycont`(cid,usid,cfname,clname,contphone,active) VALUES(NULL,?,?,?,?,1)";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            System.out.println("DEBUG 1");
            //SQL = "INSERT INTO `emergencycont` VALUES (NULL, "+usid+",'" + cfname + "', '" + clname + "','" + "'+1" + contphone + "', 1)"; //query to insert new contact
            pstmt.setInt(1, usid);
            pstmt.setString(2,cfname );
            pstmt.setString(3,clname );
            pstmt.setString(4,contphone );
            System.out.println("DEBUG 2");
            pstmt.executeUpdate(); //executes the query
            System.out.println("DEBUG 3");
        } catch(SQLException e){
            System.out.println("SQL EXCEPTION in add emergency");
        }
    }
}