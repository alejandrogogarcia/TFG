package es.udc.tfg.app.service.companyInfoService;

import es.udc.tfg.app.model.CompanyInfo.CompanyInfo;
import es.udc.tfg.app.model.CompanyInfo.CompanyInfoDao;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
@Transactional
public class CompanyInfoServiceImpl implements CompanyInfoService{

    @Value("${app.logo.path.final}")
    private String logoFinal;

    @Value("${app.logo.path.temp}")
    private String logoTemp;

    @Autowired
    private CompanyInfoDao companyInfoDao;

    @Override
    public void updateCompanyInfo(CompanyInfo companyInfoModified) throws InstanceNotFoundException, IOException {
        CompanyInfo companyInfo = companyInfoDao.find(1L);
        companyInfo.setName(companyInfoModified.getName());
        companyInfo.setAddress(companyInfoModified.getAddress());
        companyInfo.setPostCode(companyInfoModified.getPostCode());
        companyInfo.setNif(companyInfoModified.getNif());
        companyInfo.setEmail(companyInfoModified.getEmail());
        companyInfo.setWeb(companyInfoModified.getWeb());

        if (Files.exists(Path.of(logoFinal))) {
            Files.delete(Path.of(logoFinal));
        }
        Files.move(Path.of(logoTemp), Path.of(logoFinal), StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public CompanyInfo getCompanyInfo() {
        Optional<CompanyInfo> optional = companyInfoDao.getCompanyInfo().stream().findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }
}
