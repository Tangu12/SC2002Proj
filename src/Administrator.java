public class Administrator extends User {
    public Administrator(String name, String hospitalId, Domain domain) {
        super(name, hospitalId, domain);
    }
    public void homePage() {
        int choice;
        System.out.println( "Choose the number of function:\n"
                + "(1) Admin Placeholder1\n"
                + "(2) Admin Placeholder2\n"  // Doctors should be able to do this too..?
                + "(3) Admin Placeholder3\n"
                + "(4) Admin Placeholder4\n"
                + "(5) Admin Placeholder5\n"
                + "(6) Admin Placeholder6\n"
                + "(7) Exit");
        choice = InputScanner.sc.nextInt();

        switch(choice) {

        }
    }
}
