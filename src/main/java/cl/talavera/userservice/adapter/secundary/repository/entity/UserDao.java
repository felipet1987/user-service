package cl.talavera.userservice.adapter.secundary.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;


@Entity(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDao {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
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
