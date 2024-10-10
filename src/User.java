public class User {
    String name;
    String hospitalId;
    String password;
    String domain;

    public User(String name, String hospitalId, String password, String domain) {
        this.name = name;
        this.hospitalId = hospitalId;
        this.password = password;
        this.domain = domain;
    }

    public void changePassword(String hospitalId, String new_password) {

    }
}
