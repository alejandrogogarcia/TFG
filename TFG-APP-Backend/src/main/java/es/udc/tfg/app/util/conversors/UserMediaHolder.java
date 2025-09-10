package es.udc.tfg.app.util.conversors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserMediaHolder {

    private static String userMediaPath;

    @Value("${app.user.path.final}")
    public void setUserMediaPath(String path) {
        UserMediaHolder.userMediaPath = path;
    }

    public static String getUserMediaPath() {
        return userMediaPath;
    }
}
