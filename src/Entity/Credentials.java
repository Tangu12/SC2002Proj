package Entity;

public class Credentials {
    private final String userID;
    private String hashedPassword;
    private String salt;
    private String securityQuestion;
    private String hashedSecurityAnswer;
    private int loginAttempts;

    public static final int maxLoginAttempts = 3;

    // Constructor that only accepts necessary fields
    public Credentials(String userID, String hashedPassword, String salt, String securityQuestion, String hashedSecurityAnswer,int loginAttempts) {
        this.userID = userID;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
        this.securityQuestion = securityQuestion;
        this.hashedSecurityAnswer = hashedSecurityAnswer;
        this.loginAttempts = loginAttempts;
    }

    public String getUserID() {
        return userID;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getHashedSecurityAnswer() {
        return hashedSecurityAnswer;
    }

    public void setHashedSecurityAnswer(String hashedSecurityAnswer) {
        this.hashedSecurityAnswer = hashedSecurityAnswer;
    }

    public int getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    public boolean isAccountLocked(){
        return loginAttempts == -1;
    }

    public void lockAccount(){
        this.loginAttempts = -1;
    }

    public void unlockAccount(){
        this.loginAttempts = 0;
    }


}
