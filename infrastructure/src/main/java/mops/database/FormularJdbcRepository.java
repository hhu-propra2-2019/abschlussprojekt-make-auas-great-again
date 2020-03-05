package mops.database;

import mops.database.dto.FormularDto;
import org.springframework.data.repository.CrudRepository;

public interface FormularJdbcRepository extends CrudRepository<FormularDto, Long> {

}
