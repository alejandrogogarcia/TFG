package es.udc.tfg.app.util.conversors;

import es.udc.tfg.app.util.exceptions.InputValidationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class DataConversor {

    public static void saveBase64ToFile(String base64, String filePath) throws InputValidationException {
        try {
            if (base64.contains(",")) {
                base64 = base64.split(",")[1];
            }

            byte[] decodedBytes = Base64.getDecoder().decode(base64);
            Files.write(Paths.get(filePath), decodedBytes);
        } catch (IOException | IllegalArgumentException e) {
            throw new InputValidationException("saveBase64ToFile", "Erro ao escribir o ficheiro en Base64");
        }
    }

    public static void deleteFileIfExists(String filePath) {
        try {
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (IOException e) {
        }
    }


}
