package Entity.Repository;

public class MedicationInventoryRepository implements IRepository{
    public final String path;

    public MedicationInventoryRepository(String path) {
        this.path = path;
    }

    public void createRecord(){}
    public void readRecord(){}
    public void updateRecord(){}
    public void deleteRecord(){}
}
