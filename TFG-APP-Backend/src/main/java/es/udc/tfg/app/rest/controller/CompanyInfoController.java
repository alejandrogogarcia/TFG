package es.udc.tfg.app.rest.controller;

import es.udc.tfg.app.model.CompanyInfo.CompanyInfo;
import es.udc.tfg.app.rest.dtos.CompanyInfoDto;
import es.udc.tfg.app.util.conversors.CompanyInfoConversor;
import es.udc.tfg.app.service.companyInfoService.CompanyInfoData;
import es.udc.tfg.app.service.companyInfoService.CompanyInfoService;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/company")
public class CompanyInfoController {

    @Autowired
    private CompanyInfoService companyInfoService;

    @GetMapping
    public CompanyInfoDto getCompanyInfo() throws IOException {
        CompanyInfo info = companyInfoService.getCompanyInfo();
        return CompanyInfoConversor.toDto(info);

    }

    @PutMapping
    public ResponseEntity<Void> updateCompanyInfo(@RequestBody CompanyInfoDto companyInfoDto) throws IOException, InstanceNotFoundException {
        CompanyInfo companyInfo = CompanyInfoConversor.fromDto(companyInfoDto);
        companyInfoService.updateCompanyInfo(companyInfo);
        return ResponseEntity.noContent().build();
    }
}
