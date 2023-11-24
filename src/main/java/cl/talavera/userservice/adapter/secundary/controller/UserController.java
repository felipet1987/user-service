package cl.talavera.userservice.adapter.secundary.controller;

import cl.talavera.userservice.core.exception.EmailException;
import cl.talavera.userservice.core.exception.ExistedException;
import cl.talavera.userservice.core.exception.PasswordException;
import cl.talavera.userservice.core.model.UserSignUpRequest;
import cl.talavera.userservice.core.model.UserSignUpResponse;
import cl.talavera.userservice.core.port.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Slf4j
public class UserController {
    private IUserService userService;


    @PostMapping("/signup")
    public UserSignUpResponse signUp(@RequestBody UserSignUpRequest request){
        return userService.signup(request);
    }


    @ExceptionHandler({
            PasswordException.class,
            EmailException.class,
            ExistedException.class
    })
    public ResponseEntity<HttpError> handleException(RuntimeException e) {
        log.info(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(HttpError.builder()
                        .codigo(500)
                        .mensaje(e.getMessage())
                        .timestamo(Instant.now())
                        .build());
    }
    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<HttpError> handleNotFoundError(RuntimeException e) {

        log.info(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(HttpError.builder()
                        .codigo(400)
                        .mensaje(e.getMessage())
                        .timestamo(Instant.now())
                        .build());
    }

}
