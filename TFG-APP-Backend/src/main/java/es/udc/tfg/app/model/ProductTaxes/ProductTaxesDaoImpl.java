package es.udc.tfg.app.model.ProductTaxes;

import es.udc.tfg.app.model.genericDao.GenericDaoImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ProductTaxesDaoImpl extends GenericDaoImpl<ProductTaxes, Long> implements ProductTaxesDao{

    @Override
    public List<ProductTaxes> getAllProductTaxes() {
        return (List<ProductTaxes>) this.em.createQuery("SELECT pt FROM ProductTaxes pt ORDER BY pt.id")
                .getResultList();
    }
}
