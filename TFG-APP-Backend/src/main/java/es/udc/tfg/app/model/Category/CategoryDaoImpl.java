package es.udc.tfg.app.model.Category;

import es.udc.tfg.app.model.genericDao.GenericDaoImpl;
import jakarta.persistence.Query;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class CategoryDaoImpl extends GenericDaoImpl<Category, Long> implements CategoryDao{

    @Override
    public Slice<Category> findByName(String name, int page, int size) {
//        return (List<Category>) this.em.createQuery("SELECT c FROM Category c WHERE c.name like :name")
//                .setParameter("name", "%" +name + "%").getResultList();
        String queryString = "SELECT c FROM Category c";

        if (name != null && !name.isBlank()) {
            queryString += " WHERE c.name LIKE :name";
        }

        Query query = this.em.createQuery(queryString)
                .setFirstResult(page * size)
                .setMaxResults(size + 1);

        if (name != null && !name.isBlank()) {
            query.setParameter("name", "%" + name + "%");
        }

        List<Category> categories = query.getResultList();
        boolean hasNext = categories.size() > size;

        if (hasNext) {
            categories.remove(categories.size() - 1); // Eliminamos el extra usado para saber si hay más
        }

        return new SliceImpl<>(categories, PageRequest.of(page, size), hasNext);

    }

    @Override
    public List<Category> findByCreatorId(Long creatorId) {
        return (List<Category>) this.em.createQuery("SELECT c FROM Category c WHERE c.creator.id like :creatorId")
                .setParameter("creatorId", creatorId).getResultList();

    }

    @Override
    public List<Category> findAll() {
        return (List<Category>) this.em.createQuery("SELECT c FROM Category c ORDER BY c.id")
                .getResultList();
    }
}
