package cl.talavera.userservice.adapter.secundary.repository;

import cl.talavera.userservice.adapter.secundary.repository.entity.PhoneDao;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PhoneRepository extends CrudRepository<PhoneDao, UUID> {
}
