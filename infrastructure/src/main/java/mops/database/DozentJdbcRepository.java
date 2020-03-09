package mops.database;

import org.springframework.data.repository.CrudRepository;
import mops.database.dto.DozentDto;

public interface DozentJdbcRepository extends CrudRepository<DozentDto, Long> {

}
