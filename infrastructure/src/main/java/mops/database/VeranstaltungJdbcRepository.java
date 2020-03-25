package mops.database;

import mops.database.dto.VeranstaltungDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeranstaltungJdbcRepository extends CrudRepository<VeranstaltungDto, Long> {

}
