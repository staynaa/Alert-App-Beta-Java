import java.sql.*;
//import java.util.Scanner;
public class UserAction {
    String fname, lname;
    int userID;
    Connection uacon;
    boolean valid;

    UserAction() {
        System.out.println("Invalid Registration");
        this.valid = false;
    }

    UserAction(String fname, String lname, Connection con, int usid) {
        this.fname = fname;
        this.lname = lname;
        this.uacon=con;
        this.userID=usid;
        this.valid = true;
        System.out.println("USER ID:"+userID);
    }

    public void showMenu() {
        if (valid) {
            System.out.println("\n|------------------------|\nWelcome to Alert App " + fname + " " + lname
            + "!\nWhat would you like to do? Select an option:\nA. Add an emergency contact\nB."+
            " Send A Quick Alert\nC.Send An Alert With A Message\nE. Exit App\n|------------------------|\n");
        }
        else System.exit(-1);
    }

    public void alertContact() {
        System.out.println("Type your Alert Message\nEnter Text: ");
        
    }
    public void quickAlert(){
        System.out.println("We will send a quick alert");
    }
}
