package es.udc.tfg.app.util.conversors;

import es.udc.tfg.app.model.user.User;
import es.udc.tfg.app.rest.dtos.AuthenticatedUserDto;
import es.udc.tfg.app.rest.dtos.UserDto;
import es.udc.tfg.app.util.exceptions.InputValidationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class UserConversor {


    public static UserDto toUserDto(User user) {
        String image = null;
        String userMediaPath = UserMediaHolder.getUserMediaPath();

        try {
            if (user.getImage() != null) {
                Path imagePath = Path.of(userMediaPath, user.getImage());
                if (Files.exists(imagePath)) {
                    String mimeType = Files.probeContentType(imagePath);
                    byte[] imageBytes = Files.readAllBytes(imagePath);
                    image = "data:" + mimeType + ";base64," + Base64.getEncoder().encodeToString(imageBytes);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler imaxe do usuario: " + e.getMessage());
        }

        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),
                user.getDni(), user.getLanguage().toString(), CalendarConversor.calendarToString(user.getBirthDate()), user.getRole().toString(), image, Boolean.toString(user.isActive()));
    }

    public static List<UserDto> toUserDtoList(List<User> userList) {

        return userList.stream().map(UserConversor::toUserDto).collect(Collectors.toList());
    }


    public static AuthenticatedUserDto toAuthenticatedUserDto(String serviceToken, User user) {

        return new AuthenticatedUserDto(serviceToken, toUserDto(user));
    }

}
