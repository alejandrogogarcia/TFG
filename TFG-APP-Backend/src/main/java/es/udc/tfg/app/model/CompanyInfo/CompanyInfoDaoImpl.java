package es.udc.tfg.app.model.CompanyInfo;

import es.udc.tfg.app.model.ProductTaxes.ProductTaxes;
import es.udc.tfg.app.model.genericDao.GenericDaoImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class CompanyInfoDaoImpl extends GenericDaoImpl<CompanyInfo, Long> implements CompanyInfoDao {

    @Override
    public List<CompanyInfo> getCompanyInfo() {
        return (List<CompanyInfo>) this.em.createQuery("SELECT ci FROM CompanyInfo ci").getResultList();
    }
}
