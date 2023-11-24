package cl.talavera.userservice.core.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSignUpRequest {
    private List<PhoneRequest> phones;
    private String name;
    private String email;
    private String password;
}
