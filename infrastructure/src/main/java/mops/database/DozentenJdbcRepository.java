package mops.database;

import mops.database.dto.DozentDto;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DozentenJdbcRepository extends CrudRepository<DozentDto, Long> {

  @Query("select id from dozent where username = :dozent")
  Long findId(@Param("dozent") String dozent);

  @Query("select * from dozent where username = :name")
  DozentDto getDozentByUsername(@Param("name") String username);
}

