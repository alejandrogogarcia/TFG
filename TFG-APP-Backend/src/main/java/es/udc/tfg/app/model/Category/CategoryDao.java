package es.udc.tfg.app.model.Category;

import es.udc.tfg.app.model.genericDao.GenericDao;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface CategoryDao extends GenericDao<Category, Long> {

    public Slice<Category> findByName(String name, int page, int size);

    public Slice<Category> findByCreatorId(Long creatorId, int page, int size);

    public Slice<Category> findAll(int page, int size);
}
