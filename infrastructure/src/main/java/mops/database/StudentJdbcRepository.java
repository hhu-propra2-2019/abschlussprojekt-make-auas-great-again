package mops.database;

import org.springframework.data.repository.CrudRepository;
import mops.database.dto.StudentDto;

public interface StudentJdbcRepository extends CrudRepository<StudentDto, Long> {

}
