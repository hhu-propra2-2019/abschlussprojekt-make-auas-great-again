package mops.database;

import mops.database.dto.StudentDto;
import mops.database.dto.VorlesungDto;
import org.springframework.data.repository.CrudRepository;

public interface VorlesungJdbcRepository extends CrudRepository<VorlesungDto, Long> {
}
