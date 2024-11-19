package Entity.Repository;

import Entity.Medicine;

/**
 * The interface of the Repositories that has all the methods to access and change our databases
 * @param <T>
 * @param <U>
 * @param <V>
 * @param <X>
 */
public interface IRepository<T,U,V,X> {
    void createRecord(Object... attributes);
    V readRecord(U identifier);  // This will return a generic type
    void updateRecord(X record);
    void deleteRecord(T record);
}
