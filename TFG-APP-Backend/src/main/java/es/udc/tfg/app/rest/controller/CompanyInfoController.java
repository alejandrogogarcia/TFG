package es.udc.tfg.app.rest.controller;

import es.udc.tfg.app.service.companyInfoService.CompanyInfoData;
import es.udc.tfg.app.service.companyInfoService.CompanyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
public class CompanyInfoController {

    @Autowired
    private CompanyInfoService companyInfoService;

    @GetMapping
    public ResponseEntity<CompanyInfoData> getCompanyInfo() {
        CompanyInfoData infoData = companyInfoService.getCompanyInfo();
        if (infoData != null) {
            return ResponseEntity.ok(infoData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<Void> updateCompanyInfo(@RequestBody CompanyInfoData companyInfoData) {
        companyInfoService.updateCompanyInfo(companyInfoData);
        return ResponseEntity.noContent().build();
    }
}
