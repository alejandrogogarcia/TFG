package es.udc.tfg.app.model.Product;

import es.udc.tfg.app.model.genericDao.GenericDaoImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.Query;
import es.udc.tfg.app.util.Slice.Tokens;

import java.util.List;

@Repository
@Transactional
public class ProductDaoImpl extends GenericDaoImpl<Product, Long> implements ProductDao{

    @Override
    public Slice<Product> findByKeywords(String keywords, Long categoryId, int page, int size) {

        String[] tokens = Tokens.getTokens(keywords);
        String queryString = "SELECT p FROM Product p ";

        if (tokens.length > 0 || categoryId != null) {
            queryString += "WHERE ";

            if (categoryId != null) {
                queryString += "p.category.id = :categoryId";
            }

            for (int i = 0; i < tokens.length; i++) {
                if (categoryId != null || i != 0) {
                    queryString += " AND ";
                }
                queryString += "(LOWER(p.reference) LIKE :token" + i +
                        " OR LOWER(p.name) LIKE :token" + i + ")";
            }
        }

        Query query = this.em.createQuery(queryString)
                .setFirstResult(page * size)
                .setMaxResults(size + 1);

        if (categoryId != null) {
            query.setParameter("categoryId", categoryId);
        }

        for (int i = 0; i < tokens.length; i++) {
            query.setParameter("token" + i, "%" + tokens[i].toLowerCase() + "%");
        }

        List<Product> products = query.getResultList();
        boolean hasNext = products.size() == (size + 1);

        if (hasNext) {
            products.remove(products.size() - 1);
        }

        return new SliceImpl<>(products, PageRequest.of(page, size), hasNext);
    }

    @Override
    public Slice<Product> findByCreatorId(Long creatorId, int page, int size) {
        Query query = this.em.createQuery("SELECT p FROM Product p WHERE p.creator.id = :creatorId")
                .setParameter("creatorId", creatorId)
                .setFirstResult(page * size)
                .setMaxResults(size + 1);

        List<Product> products = query.getResultList();
        boolean hasNext = products.size() == (size + 1);

        if (hasNext) {
            products.remove(products.size() - 1);
        }

        return new SliceImpl<>(products, PageRequest.of(page, size), hasNext);
    }
}
