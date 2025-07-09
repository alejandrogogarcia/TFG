package es.udc.tfg.app.util.conversors;

import es.udc.tfg.app.model.user.User;
import es.udc.tfg.app.rest.dtos.AuthenticatedUserDto;
import es.udc.tfg.app.rest.dtos.UserDto;
import es.udc.tfg.app.util.exceptions.InputValidationException;

import java.util.List;
import java.util.stream.Collectors;

public class UserConversor {


    public static UserDto toUserDto(User user) {

        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),
                user.getDni(), user.getLanguage().toString(), CalendarConversor.calendarToString(user.getBirthDate()), user.getRole().toString(), user.getImage(), Boolean.toString(user.isActive()));
    }

    public static List<UserDto> toUserDtoList(List<User> userList) {

        return userList.stream().map(UserConversor::toUserDto).collect(Collectors.toList());
    }


    public static AuthenticatedUserDto toAuthenticatedUserDto(String serviceToken, User user) {

        return new AuthenticatedUserDto(serviceToken, toUserDto(user));
    }

}
