package Entity.Repository;

public class PatientDataRepository implements IRepository{
    public final String path;

    public PatientDataRepository(String path) {
        this.path = path;
    }

    @Override
    public void createRecord(Object... attributes) {

    }

    @Override
    public Object readRecord(Object identifier) {
        return null;
    }

    @Override
    public void updateRecord(Object record) {

    }

    @Override
    public void deleteRecord(Object record) {

    }
}
