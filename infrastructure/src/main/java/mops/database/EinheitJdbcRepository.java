package mops.database;

import mops.database.dto.EinheitDto;
import mops.database.dto.VorlesungDto;
import org.springframework.data.repository.CrudRepository;

public interface EinheitJdbcRepository extends CrudRepository<EinheitDto, Long> {
}
