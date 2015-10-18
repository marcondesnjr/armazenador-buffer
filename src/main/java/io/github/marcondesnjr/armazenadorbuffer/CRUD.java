package io.github.marcondesnjr.armazenadorbuffer;

/**
 *
 * @author José Marcondes do Nascimento Junior
 */
public interface CRUD <T,R> {
    public void create(T obj) throws PersistenceException;
    public T read(R obj) throws PersistenceException;
    public void update(T obj) throws PersistenceException;
    public void delete(R obj) throws PersistenceException;
}
