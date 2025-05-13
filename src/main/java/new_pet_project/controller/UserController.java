package new_pet_project.controller;

import jakarta.validation.Valid;
import new_pet_project.DTO.UserCreatForm;
import new_pet_project.exception.NameBusyException;
import new_pet_project.security.SecurityUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import new_pet_project.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> addUser(
            @Valid @RequestBody UserCreatForm userCreatForm
    ) throws NameBusyException {
        userService.addUser(userCreatForm);
        return new ResponseEntity<>("Пользователь успешно зарегистрирован", HttpStatus.CREATED);
    }

    @PutMapping("/user/update")
    public ResponseEntity<?> updateUser(
            @Valid @RequestBody UserCreatForm userCreatForm,
            @AuthenticationPrincipal SecurityUser securityUser
    ) throws NameBusyException {
        userService.updateUser(securityUser.getId(), userCreatForm);
        return new ResponseEntity<>("Пользователь успешно изменен", HttpStatus.CREATED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/user/delete")
    public ResponseEntity<?> deleteUser(
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        userService.deleteUser(securityUser.getUsername());
        return new ResponseEntity<>("Пользователь успешно удален", HttpStatus.CREATED);
    }

    @ExceptionHandler(NameBusyException.class)
    public ResponseEntity<?> handleException(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
