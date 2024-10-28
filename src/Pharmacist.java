public class Pharmacist extends User {
    public Pharmacist(String name, String hospitalId, domain domain) {
        super(name, hospitalId, domain);
    }
    
    public void homePage() {
        int choice;
        System.out.println( "Choose the number of function:\n"
                + "(1) Pharmacist Placeholder1\n"
                + "(2) Pharmacist Placeholder2\n"  // Doctors should be able to do this too..?
                + "(3) Pharmacist Placeholder3\n"
                + "(4) Pharmacist Placeholder4\n"
                + "(5) Pharmacist Placeholder5\n"
                + "(6) Pharmacist Placeholder6\n"
                + "(7) Exit");
        choice = InputScanner.sc.nextInt();
        InputScanner.sc.nextLine();

        switch(choice) {

        }
    }
}
