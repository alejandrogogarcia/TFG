package es.udc.tfg.app.util.conversors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CompanyInfoMediaHolder {

    private static String logoPath;
    private static String logoTempPath;

    @Value("${app.logo.path.final}")
    public void setLogoPath(String path) {
        CompanyInfoMediaHolder.logoPath = path;
    }

    @Value("${app.logo.path.temp}")
    public void setLogoTempPath(String path) {
        CompanyInfoMediaHolder.logoTempPath = path;
    }

    public static String getLogoPath() {
        return logoPath;
    }

    public static String getLogoTempPath() {
        return logoTempPath;
    }
}
