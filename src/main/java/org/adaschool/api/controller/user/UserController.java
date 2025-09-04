package org.adaschool.api.controller.user;

import jakarta.annotation.security.RolesAllowed;
import org.adaschool.api.data.user.RoleEnum;
import org.adaschool.api.data.user.UserEntity;
import org.adaschool.api.data.user.UserService;
import org.adaschool.api.exception.UserWithEmailAlreadyRegisteredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.adaschool.api.utils.Constants.ADMIN_ROLE;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        loadSampleUsers();
    }

    public void loadSampleUsers() {
        if (passwordEncoder != null) {
            UserEntity userEntity = new UserEntity("Ada Lovelace", "ada@mail.com", passwordEncoder.encode("passw0rd"));
            userService.save(userEntity);
            UserEntity adminUserEntity = new UserEntity("Ada Admin", "admin@mail.com", passwordEncoder.encode("passw0rd"));
            adminUserEntity.addRole(RoleEnum.ADMIN);
            userService.save(adminUserEntity);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable String id) {
        Optional<UserEntity> userOpt = userService.findById(id);
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserDto userDto) {
        // Verificar si ya existe un usuario con ese correo
        Optional<UserEntity> existingUser = userService.findByEmail(userDto.getEmail());
        if (existingUser.isPresent()) {
            throw new UserWithEmailAlreadyRegisteredException(userDto.getEmail());
        }

        // Crear y guardar nuevo usuario
        UserEntity newUser = new UserEntity(
                userDto.getName(),
                userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword())
        );

        UserEntity savedUser = userService.save(newUser);
        return ResponseEntity.ok(savedUser);
    }

    @RolesAllowed(ADMIN_ROLE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable String id) {
        Optional<UserEntity> userOpt = userService.findById(id);
        if (userOpt.isPresent()) {
            userService.delete(userOpt.get());
            return ResponseEntity.ok(Boolean.TRUE);
        } else {
            return ResponseEntity.ok(Boolean.FALSE);
        }
    }
}
