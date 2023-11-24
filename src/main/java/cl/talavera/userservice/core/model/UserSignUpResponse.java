package cl.talavera.userservice.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpResponse {
    private String id;
    private LocalDate created;
    private LocalDate modified;
    private LocalDate last_login;
    private String token;
    private Boolean isActive;
}
