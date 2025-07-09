package es.udc.tfg.app.model.Category;

import es.udc.tfg.app.model.genericDao.GenericDao;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface CategoryDao extends GenericDao<Category, Long> {

    public Slice<Category> findByName(String name, int page, int size);

    public List<Category> findByCreatorId(Long creatorId);

    public List<Category> findAll();
}
