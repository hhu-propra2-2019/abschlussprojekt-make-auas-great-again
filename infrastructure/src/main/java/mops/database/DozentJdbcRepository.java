package mops.database;

import mops.database.dto.DozentDto;
import org.springframework.data.repository.CrudRepository;

public interface DozentJdbcRepository extends CrudRepository<DozentDto, Long> {

}
