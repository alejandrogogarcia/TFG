package es.udc.tfg.app.rest.controller;

import es.udc.tfg.app.model.user.User;
import es.udc.tfg.app.rest.common.ErrorsDto;
import es.udc.tfg.app.rest.common.JwtGenerator;
import es.udc.tfg.app.rest.common.JwtInfo;
import es.udc.tfg.app.rest.dtos.AuthenticatedUserDto;
import es.udc.tfg.app.rest.dtos.UserDto;
import es.udc.tfg.app.service.Block;
import es.udc.tfg.app.service.userService.*;
import es.udc.tfg.app.util.conversors.UserConversor;
import es.udc.tfg.app.util.exceptions.*;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/")
public class UserController {

    private final static String INCORRECT_LOGIN_EXCEPTION_CODE = "project.exceptions.IncorrectLoginException";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtGenerator jwtGenerator;

    @ExceptionHandler(IncorrectLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorsDto handleIncorrectLoginException(IncorrectLoginException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(INCORRECT_LOGIN_EXCEPTION_CODE, null,
                INCORRECT_LOGIN_EXCEPTION_CODE, locale);

        return new ErrorsDto(errorMessage);
    }

    @PostMapping("/login")
    public AuthenticatedUserDto login(@RequestBody LoginData params) throws IncorrectLoginException, DisabledUserException {
        User user = userService.loginUser(params);
        return UserConversor.toAuthenticatedUserDto(generateToken(user), user);
    }

    @PostMapping("/loginFromServiceToken")
    public AuthenticatedUserDto loginFromServiceToken(@RequestAttribute Long userId, @RequestAttribute String serviceToken) throws InstanceNotFoundException {
             User user = userService.findUserById(userId);
        return UserConversor.toAuthenticatedUserDto(serviceToken, user);
    }


    @PostMapping("/users/create")
    public ResponseEntity<UserDto> createUser(@RequestBody RegisterData registerData) throws InputValidationException, DuplicateInstanceException, MessagingException {

        User user = userService.registerUser(registerData);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/").build().toUri();

        return ResponseEntity.created(location).body(UserConversor.toUserDto(user));
    }

    @PutMapping("/users/changePass")
    public AuthenticatedUserDto login(@RequestAttribute Long userId, @RequestBody PassChangeData params) throws IncorrectPasswordException, InstanceNotFoundException, InputValidationException {

        userService.changeUserPassword(userId, params.getOldPassword(), params.getNewPassword());

        User user = userService.findUserById(userId);

        return UserConversor.toAuthenticatedUserDto(generateToken(user), user);
    }
    @PutMapping("/users/{id}/resetPass")
    public void resetPassword(@PathVariable Long id) throws InstanceNotFoundException, MessagingException {
        userService.resetUserPassword(id);
    }

    @PostMapping("/users/setPass")
    public void setPassword(@RequestBody PassResetData params ) throws InstanceNotFoundException, IllegalArgumentException {
        userService.setUserPassword(params.getDni(), params.getToken(), params.getPassword());
    }


    @GetMapping("/users/find")
    public Block<UserDto> findUsers(
            @RequestParam (required=false) String keywords,
            @RequestParam(required=false) String role,
            @RequestParam(defaultValue="0") int page) throws InputValidationException {

        Block<User> userBlock = userService.findUsersByKeywords(keywords != null ? keywords.trim() : null, role != null ? role.trim() : null, page, 5 );

        return new Block<>(UserConversor.toUserDtoList(userBlock.getItems()),userBlock.getExistMoreItems());

    }

    @GetMapping("/users/{id}")
    public UserDto findUserById(@PathVariable Long id) throws InstanceNotFoundException {
        return UserConversor.toUserDto(userService.findUserById(id));

    }

    @PutMapping("/users/{id}/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable Long id, @RequestBody UserData userData, @RequestAttribute Long userId) throws InputValidationException, DuplicateInstanceException, InstanceNotFoundException, PermissionException {

        userService.updateUser(id,userData,userId);

    }

    private String generateToken(User user) {
        JwtInfo jwtInfo = new JwtInfo(user.getRole().toString(), user.getDni(), user.getId());
        return jwtGenerator.generate(jwtInfo);
    }

}
