package mops.database;

import mops.database.dto.StudentDto;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface StudentenJdbcRepository extends CrudRepository<StudentDto, Long> {

  @Query("select id from student where username = :student")
  Long findId(@Param("student") String student);
}
