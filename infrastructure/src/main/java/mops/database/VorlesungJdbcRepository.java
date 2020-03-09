package mops.database;

import org.springframework.data.repository.CrudRepository;
import mops.database.dto.VorlesungDto;

public interface VorlesungJdbcRepository extends CrudRepository<VorlesungDto, Long> {
}
