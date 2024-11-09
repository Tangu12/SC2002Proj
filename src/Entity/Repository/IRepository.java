package Entity.Repository;

import Entity.Medicine;

public interface IRepository<T,U> {
    void createRecord(Object... attributes);
    T readRecord(U identifier);  // This will return a generic type
    void updateRecord(T record);
    void deleteRecord(T record);
}
