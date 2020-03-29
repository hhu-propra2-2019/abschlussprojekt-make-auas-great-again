package mops.database.repos;

import mops.database.dto.StudentDto;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<StudentDto, String> {

}
