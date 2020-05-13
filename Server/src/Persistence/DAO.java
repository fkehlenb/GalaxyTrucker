package Persistence;

public abstract class DAO<T> {

    public abstract void persist(T object);

    public abstract void update(T object);

    public abstract void remove(T object);
}
