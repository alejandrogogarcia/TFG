package es.udc.tfg.app.rest.controller;

import es.udc.tfg.app.model.user.User;
import es.udc.tfg.app.rest.dtos.AuthenticatedUserDto;
import es.udc.tfg.app.rest.dtos.UserDto;
import es.udc.tfg.app.service.userservice.LoginData;
import es.udc.tfg.app.service.userservice.PassChangeData;
import es.udc.tfg.app.service.userservice.RegisterData;
import es.udc.tfg.app.service.userservice.UserService;
import es.udc.tfg.app.util.conversors.UserConversor;
import es.udc.tfg.app.util.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public AuthenticatedUserDto login(@RequestBody LoginData params) throws IncorrectPasswordException, InstanceNotFoundException, DisabledUserException {

        User user = userService.loginUser(params);
//        return UserConversor.toAuthenticatedUserDto(generateToken(user), user);
        return UserConversor.toAuthenticatedUserDto("Token de prueba", user);
    }


    @PostMapping("/user/create")
    public ResponseEntity<UserDto> createUser(@RequestBody RegisterData registerData) throws InputValidationException, DuplicateInstanceException {

        User user = userService.registerUser(registerData);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/").build().toUri();

        return ResponseEntity.created(location).body(UserConversor.toUserDto(user));
    }

    @PutMapping("/user/{id}/changePass")
    public AuthenticatedUserDto login(@PathVariable Long id, @RequestBody PassChangeData params) throws IncorrectPasswordException, InstanceNotFoundException, InputValidationException {

        userService.changeUserPassword(id, params.getOldPassword(), params.getNewPassword());

        User user = userService.findUserById(id);

        return UserConversor.toAuthenticatedUserDto("Token de prueba", user);

//        return UserConversor.toAuthenticatedUserDto(generateToken(user), user);

    }

    @GetMapping("/users")
    public List<UserDto> findAllUsers() {

        return UserConversor.toUserDtoList(userService.findAllUsers());

    }

//    private String generateToken(User user) {
//
//        JwtInfo jwtInfo = new JwtInfo(user.getId(), user.getDni(), user.getRole().toString());
//
//        return jwtGenerator.generate(jwtInfo);
//
//    }

}
