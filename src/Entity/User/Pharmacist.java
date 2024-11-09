package Entity.User;

import Entity.Enums.Domain;
import Entity.Enums.Gender;

public class Pharmacist implements IUser{
    @Override
    public String getUserId() {
        return "";
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public int getAge() {
        return 0;
    }

    @Override
    public Gender getGender() {
        return null;
    }

    @Override
    public Domain getDomain() {
        return null;
    }

    @Override
    public void setUserId(String userId) {

    }

    @Override
    public void setName(String name) {

    }

    @Override
    public void setAge(int age) {

    }

    @Override
    public void setGender(Gender gender) {

    }

    @Override
    public void setDomain(Domain domain) {

    }
}
