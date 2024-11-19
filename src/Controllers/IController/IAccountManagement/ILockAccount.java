package Controllers.IController.IAccountManagement;

import java.util.ArrayList;

public interface ILockAccount {
    ArrayList<String> getAllLockedUserIDs();
    ArrayList<String> getAllUserIDs();
    ArrayList<String> getAllUnlockedUserIDs();
    void lockAccount(String userID);
    void unlockAccount(String userID);
}
