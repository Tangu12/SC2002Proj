package Entity.Repository;

public class MedicalRecordRepository implements IRepository{
    public final String path;

    public MedicalRecordRepository(String path) {
        this.path = path;
    }

    @Override
    public void createRecord(Object record) {

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
