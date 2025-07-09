package es.udc.tfg.app.service.companyInfoService;

import es.udc.tfg.app.model.CompanyInfo.CompanyInfo;
import es.udc.tfg.app.model.CompanyInfo.CompanyInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CompanyInfoServiceImpl implements CompanyInfoService{

    @Autowired
    private CompanyInfoDao companyInfoDao;

    @Override
    public void updateCompanyInfo(CompanyInfoData companyInfoData) {
        Optional<CompanyInfo> optional = companyInfoDao.getCompanyInfo().stream().findFirst();
        CompanyInfo companyInfo;
        if (optional.isPresent()) {
            companyInfo = optional.get();
        } else {
            companyInfo = new CompanyInfo();
        }
        companyInfo.setName(companyInfoData.getName());
        companyInfo.setAddress(companyInfoData.getAddress());
        companyInfo.setPostCode(companyInfoData.getPostCode());
        companyInfo.setNif(companyInfoData.getNif());
        companyInfo.setEmail(companyInfoData.getEmail());
        companyInfo.setWeb(companyInfoData.getWeb());
        companyInfo.setLogo(companyInfoData.getLogo());

    }

    @Override
    public CompanyInfoData getCompanyInfo() {
        Optional<CompanyInfo> optional = companyInfoDao.getCompanyInfo().stream().findFirst();
        if (optional.isPresent()) {
            CompanyInfo companyInfo = optional.get();
            return new CompanyInfoData(companyInfo.getName(),companyInfo.getAddress(),companyInfo.getPostCode(),companyInfo.getNif(),companyInfo.getEmail(),companyInfo.getWeb(),companyInfo.getLogo());
        }
        return null;
    }
}
