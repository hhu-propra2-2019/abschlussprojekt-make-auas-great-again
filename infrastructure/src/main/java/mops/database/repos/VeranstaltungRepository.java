package mops.database.repos;

import org.springframework.data.repository.CrudRepository;
import mops.database.dto.VeranstaltungDto;

public interface VeranstaltungRepository extends CrudRepository<VeranstaltungDto, Long> {

}
