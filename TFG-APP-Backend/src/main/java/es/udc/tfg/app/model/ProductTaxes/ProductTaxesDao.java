package es.udc.tfg.app.model.ProductTaxes;

import es.udc.tfg.app.model.genericDao.GenericDao;

import java.util.List;

public interface ProductTaxesDao extends GenericDao<ProductTaxes, Long> {

    public List<ProductTaxes> getAllProductTaxes();
}
