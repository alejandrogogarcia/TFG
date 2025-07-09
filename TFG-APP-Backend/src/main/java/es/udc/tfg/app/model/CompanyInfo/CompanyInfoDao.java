package es.udc.tfg.app.model.CompanyInfo;

import es.udc.tfg.app.model.genericDao.GenericDao;

import java.util.List;

public interface CompanyInfoDao extends GenericDao<CompanyInfo, Long> {

    public List<CompanyInfo> getCompanyInfo();

}
