package es.udc.tfg.app.service.companyInfoService;

import es.udc.tfg.app.model.CompanyInfo.CompanyInfo;

public interface CompanyInfoService {

    public void updateCompanyInfo(CompanyInfoData companyInfoData);

    public CompanyInfoData getCompanyInfo();
}
