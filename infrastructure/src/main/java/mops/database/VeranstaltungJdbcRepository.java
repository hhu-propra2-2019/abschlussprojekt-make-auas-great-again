package mops.database;

import mops.database.dto.VeranstaltungDto;
import org.springframework.data.repository.CrudRepository;

public interface VeranstaltungJdbcRepository extends CrudRepository<VeranstaltungDto, Long> {
}
