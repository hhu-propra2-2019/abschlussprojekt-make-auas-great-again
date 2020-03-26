package mops.database;

import mops.database.dto.FragebogenDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FragebogenJdbcRepository extends CrudRepository<FragebogenDto, Long> {

}
