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
     * Constructor for {@code Credentials}
     * @param userID The unique identifier for the user. Typically, it could be a username or email address.
     * @param hashedPassword The hashed version of the user's password for secure storage.
     * @param salt The salt used to hash the password, adding extra security to the password storage.
     * @param securityQuestion The security question associated with the user, used for account recovery.
     * @param hashedSecurityAnswer The hashed version of the user's answer to the security question, stored for secure verification.
     * @param loginAttempts The number of failed login attempts the user has made. If it reaches the maximum limit, the account can be locked.
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
     * @param hashedPassword The new hashed password to set for the user.
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
     * @param salt The new salt to be set for hashing the password.
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
     * @param securityQuestion The new security question to be associated with the user's account.
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
     * @param hashedSecurityAnswer The new hashed answer to the security question.
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
     * @param loginAttempts The new number of login attempts to be set for the user.
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
