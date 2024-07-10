package es.udc.tfg.app.model.genericDao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public abstract class GenericDaoImpl<E, PK extends Serializable> implements GenericDao<E, PK> {
	
	@PersistenceContext
	protected EntityManager em;

	protected Class<? extends E> type;

	public GenericDaoImpl() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		type = (Class) pt.getActualTypeArguments()[0];
	}

	public void save(E entity) {
		this.em.persist(entity);
	}

	public void remove(PK id) {
		//this.em.remove(this.em.getReference(type, id));
		E entity = em.find(type, id);
        if (entity != null) {
        	em.remove(entity);
        }
	}

	public E find(PK id)  throws InstanceNotFoundException {
		E entity = (E) this.em.find(type, id);
		if (entity == null) {
			throw new InstanceNotFoundException(id, type.getName());
		}
		return entity;
	}

	@Override
	public void update(E entity) {
		this.em.merge(entity);
	}
	
}
