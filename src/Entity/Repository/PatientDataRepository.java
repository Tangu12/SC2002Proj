package Entity.Repository;

public class PatientDataRepository implements IRepository{
    public final String path;

    public PatientDataRepository(String path) {
        this.path = path;
    }
    public void createRecord(){}
    public void readRecord(){}
    public void updateRecord(){}
    public void deleteRecord(){}
}
