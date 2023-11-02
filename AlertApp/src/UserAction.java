public class UserAction {
    String fname, lname;
    boolean valid;

    UserAction() {
        System.out.println("Invalid Registration");
        this.valid = false;
    }

    UserAction(String fname, String lname) {
        this.fname = fname;
        this.lname = lname;
        this.valid = true;
    }

    public void showMenu() {
        if (valid) {
            System.out.println("Welcome to Alert App " + fname + " " + lname
                    + "!\nWhat would you like to do? Select an option:" +
                    "\nA. Add an emergency contact\nB. Send A Quick Alert\nC.Send An Alert With A Message\nE. Exit App");
        }
        else System.exit(-1);
    }

    public void addEmergencyContact() {

    }

    public void alertContact() {
        System.out.println("Enter Text: ");
        
    }
    public void quickAlert(){
        System.out.println("");
    }
}
