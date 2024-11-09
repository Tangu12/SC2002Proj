package Entity.Repository;

public class MedicalRecordRepository implements IRepository{
    public final String path;

    public MedicalRecordRepository(String path) {
        this.path = path;
    }

    public void createRecord(){}
    public void readRecord(){}
    public void updateRecord(){}
    public void deleteRecord(){}
}
