package dhbw.datenbanken.ormexample.data.repository;

import dhbw.datenbanken.ormexample.connection.ConnectionHelper;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;


public abstract class GenericRepository<T, ID extends Serializable> {

    protected EntityManager em = ConnectionHelper.getConnection();

    protected final Class<T> entityClass;


    protected GenericRepository( Class<T> entityClass ) {
        this.entityClass = entityClass;
    }


    public T findById( ID id ) {
        return em.find( entityClass, id );
    }


    public List<T> findAll() {
        CriteriaQuery<T> c = em.getCriteriaBuilder().createQuery( entityClass );
        c.select( c.from( entityClass ) );
        return em.createQuery( c ).getResultList();
    }


    public void deleteEntity( T entity ) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        if ( em.contains( entity ) ) {
            em.remove( entity );
        } else {
            em.remove( em.merge( entity ) );
        }

        transaction.commit();
    }


    public T createEntity( T entity ) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist( entity );
        //em.refresh( entity );
        transaction.commit();

        return entity;
    }


    public T updateWithMerge( T entity ) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        T result = em.merge( entity );
        transaction.commit();

        return result;
    }


    public T updateWithoutMerge( T entity ) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        transaction.commit();

        return entity;
    }


    public EntityTransaction getEntityTransaction() {
        reconnectToDatabase();
        return em.getTransaction();
    }


    public void closeConnection() {
        em.close();
    }


    public EntityManager getEntityManager() {
        return em;
    }


    public void reconnectToDatabase() {
        if ( em == null || !em.isOpen() ) {
            em = ConnectionHelper.getConnection();
        }
    }

}
