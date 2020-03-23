package mops.database;

import mops.database.dto.StudentDto;
import org.springframework.data.repository.CrudRepository;

public interface StudentenJdbcRepository extends CrudRepository<StudentDto, Long> {
}
