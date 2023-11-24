package cl.talavera.userservice.core.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private LocalDate created;
    private LocalDate modified;
    private LocalDate last_login;
    private String token;
    private Boolean isActive;
}
