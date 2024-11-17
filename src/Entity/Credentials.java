package Entity;

public class Credentials {
    private final String userID;
    private String hashedPassword;
    private String salt;
    private String securityQuestion;
    private String hashedSecurityAnswer;
    private int loginAttempts;

    /**
     * Maximum number of login attempts
     */
    public static final int maxLoginAttempts = 3;

    /**
     * Constructor for {@code Credentials}
     * @param userID
     * @param hashedPassword
     * @param salt
     * @param securityQuestion
     * @param hashedSecurityAnswer
     * @param loginAttempts
     */
    public Credentials(String userID, String hashedPassword, String salt, String securityQuestion, String hashedSecurityAnswer,int loginAttempts) {
        this.userID = userID;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
        this.securityQuestion = securityQuestion;
        this.hashedSecurityAnswer = hashedSecurityAnswer;
        this.loginAttempts = loginAttempts;
    }

    /**
     * The getter method of a {@code Credentials}'s {@code HospitalID}
     * @return The {@code HospitalID} of a User's {@code Credentials}
     */
    public String getUserID() {
        return userID;
    }

    /**
     * The getter method of a {@code Credentials}'s hashed password
     * @return The hashed password of a User's {@code Credentials}
     */
    public String getHashedPassword() {
        return hashedPassword;
    }

    /**
     * The setter method of a {@code Credentials}'s hashed password
     * @param hashedPassword
     */
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    /**
     * The getter method of a {@code Credentials}'s salt
     * @return The salt of a User's {@code Credentials}
     */
    public String getSalt() {
        return salt;
    }

    /**
     * The setter method of a {@code Credentials}'s salt
     * @param salt
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * The getter method of a {@code Credentials}'s security question
     * @return The User's security question inside {@code Credentials}
     */
    public String getSecurityQuestion() {
        return securityQuestion;
    }

    /**
     * The setter method of a {@code Credentials}'s security question
     * @param securityQuestion
     */
    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    /**
     * The getter method of a {@code Credentials}'s hashed security answer
     * @return The User's hashed security question
     */
    public String getHashedSecurityAnswer() {
        return hashedSecurityAnswer;
    }

    /**
     *  The setter method of a {@code Credentials}'s hashed security answer
     * @param hashedSecurityAnswer
     */
    public void setHashedSecurityAnswer(String hashedSecurityAnswer) {
        this.hashedSecurityAnswer = hashedSecurityAnswer;
    }

    /**
     * The getter method of a {@code Credentials}'s login attempts
     * @return The number of login attempts of a User
     */
    public int getLoginAttempts() {
        return loginAttempts;
    }

    /**
     * The setter method of a {@code Credentials}'s login attempts
     * @param loginAttempts
     */
    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    /**
     * Checks whether a User account is locked
     * @return True if the account is not locked, login attempts is -1, false otherwise
     */
    public boolean isAccountLocked(){
        return loginAttempts == -1;
    }

    /**
     * Locks the Users account
     */
    public void lockAccount(){
        this.loginAttempts = -1;
    }

    /**
     * Unlocks the Users account
     */
    public void unlockAccount(){
        this.loginAttempts = 0;
    }


}
