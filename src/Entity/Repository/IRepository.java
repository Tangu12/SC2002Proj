package Entity.Repository;

import Entity.Medicine;

public interface IRepository<T,U,V,X> {
    void createRecord(Object... attributes);
    V readRecord(U identifier);  // This will return a generic type
    void updateRecord(X record);
    void deleteRecord(T record);
}
