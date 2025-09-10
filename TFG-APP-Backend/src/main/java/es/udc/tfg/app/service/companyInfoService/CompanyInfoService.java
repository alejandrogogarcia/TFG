package es.udc.tfg.app.service.companyInfoService;

import es.udc.tfg.app.model.CompanyInfo.CompanyInfo;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;

import java.io.IOException;

public interface CompanyInfoService {

    public void updateCompanyInfo(CompanyInfo companyInfo) throws InstanceNotFoundException, IOException;

    public CompanyInfo getCompanyInfo();
}
