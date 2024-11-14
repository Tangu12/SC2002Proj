package Services.UserAccount;

public interface IUserAccountService <T> {
    void createUserAccount(T object,String plainTextPassword,String securityQuestion,String plainTextSecurityAnswer);
    void deleteUserAccount(String userID);
    void updateUserData(T object);
    boolean verifyPassword(String UserID, String plainTextPassword);
    boolean verifySecurityQuestion(String userID,String plainTextSecurityAnswer);
    int getNumberOfTriesLeft(String userID);
    void changePassword(String userID,String newPassword);
    void incrementNumberOfTries(String userID);
    void unlockAccount(String userID);
    T getAccount(String userID);
}
