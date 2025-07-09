package es.udc.tfg.app.model.Product;

import es.udc.tfg.app.model.genericDao.GenericDao;
import org.springframework.data.domain.Slice;

public interface ProductDao extends GenericDao<Product, Long> {

    public Slice<Product> findByKeywords(String keywords, Long categoryId, int page, int size);

    public Slice<Product> findByCreatorId(Long creatorId, int page, int size);

}
