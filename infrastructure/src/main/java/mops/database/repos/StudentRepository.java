package mops.database.repos;

import org.springframework.data.repository.CrudRepository;
import mops.database.dto.StudentDto;

public interface StudentRepository extends CrudRepository<StudentDto, String> {

}
