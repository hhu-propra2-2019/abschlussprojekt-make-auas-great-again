package mops.database;

import org.springframework.data.repository.CrudRepository;
import mops.database.dto.FragebogenDto;

public interface FragebogenJdbcRepository extends CrudRepository<FragebogenDto, Long> {

}
