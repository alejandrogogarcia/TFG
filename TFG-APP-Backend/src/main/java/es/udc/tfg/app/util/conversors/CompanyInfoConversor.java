package es.udc.tfg.app.util.conversors;
import es.udc.tfg.app.model.CompanyInfo.CompanyInfo;
import es.udc.tfg.app.rest.dtos.CompanyInfoDto;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class CompanyInfoConversor {

    public static CompanyInfoDto toDto(CompanyInfo c) throws IOException {
        String logoBase64 = null;
        Path imagePath = Path.of(CompanyInfoMediaHolder.getLogoPath());

        if (Files.exists(imagePath)) {
            byte[] imageBytes = Files.readAllBytes(imagePath);
            String mimeType = Files.probeContentType(imagePath);
            logoBase64 = "data:" + mimeType + ";base64,"
                    + Base64.getEncoder().encodeToString(imageBytes);
        }

        return new CompanyInfoDto(
                c.getId(), c.getName(), c.getAddress(), c.getPostCode(),
                c.getNif(), c.getEmail(), c.getWeb(), logoBase64
        );
    }

    public static CompanyInfo fromDto(CompanyInfoDto dto) throws IOException {
        String base64Logo = dto.getLogo();
        if (base64Logo != null && !base64Logo.isEmpty()) {
            String[] parts = base64Logo.split(",", 2);
            byte[] imageBytes = Base64.getDecoder().decode(parts[1]);
            ByteArrayInputStream in = new ByteArrayInputStream(imageBytes);

            Path tempPath = Path.of(CompanyInfoMediaHolder.getLogoTempPath());
            Files.createDirectories(tempPath.getParent());
            Files.copy(in, tempPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }

        return new CompanyInfo(
                dto.getId(), dto.getName(), dto.getAddress(),
                dto.getPostCode(), dto.getNif(), dto.getEmail(),
                dto.getWeb()
        );
    }
}
