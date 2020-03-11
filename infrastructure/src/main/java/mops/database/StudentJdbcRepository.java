package mops.database;

import mops.database.dto.StudentDto;
import org.springframework.data.repository.CrudRepository;

public interface StudentJdbcRepository extends CrudRepository<StudentDto, Long> {

}
