package mops.database;

import mops.database.dto.FragebogenDto;
import org.springframework.data.repository.CrudRepository;

public interface FragebogenJdbcRepository extends CrudRepository<FragebogenDto, Long> {

}
