package mops.database;

import mops.database.dto.DozentDto;
import org.springframework.data.repository.CrudRepository;

public interface DozentenJdbcRepository extends CrudRepository<DozentDto, Long> {
}
