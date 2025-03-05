package com.github.brunomndantas.flashscore.api.dataAccess.hibernateRepository;

import com.github.brunomndantas.repository4j.IRepository;
import com.github.brunomndantas.repository4j.exception.DuplicatedEntityException;
import com.github.brunomndantas.repository4j.exception.NonExistentEntityException;
import com.github.brunomndantas.repository4j.exception.RepositoryException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.function.Function;

@Transactional
public class HibernateRepository<K, E> implements IRepository<K, E> {

    protected Class<E> entityClass;
    protected EntityManager entityManager;
    protected Function<E,K> keyExtractor;


    public HibernateRepository(Class<E> entityClass, EntityManager entityManager, Function<E,K> keyExtractor) {
        this.entityClass = entityClass;
        this.entityManager = entityManager;
        this.keyExtractor = keyExtractor;
    }


    @Override
    public Collection<E> getAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> cq = cb.createQuery(entityClass);
        Root<E> root = cq.from(entityClass);
        cq.select(root);

        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public E get(K key) {
        return entityManager.find(entityClass, key);
    }

    @Override
    public void insert(E entity) throws RepositoryException {
        K key = keyExtractor.apply(entity);

        if(entityManager.contains(entity)) {
            throw new DuplicatedEntityException("There is already a entity with key:" + key);
        }

        entityManager.persist(entity);
    }

    @Override
    public void update(E entity) throws RepositoryException {
        K key = keyExtractor.apply(entity);

        if(!entityManager.contains(entity)) {
            throw new NonExistentEntityException("There is no entity with key:" + key);
        }

        entityManager.merge(entity);
    }

    @Override
    public void delete(K key) {
        E entity = get(key);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

}