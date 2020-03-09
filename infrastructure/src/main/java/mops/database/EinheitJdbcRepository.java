package mops.database;

import org.springframework.data.repository.CrudRepository;
import mops.database.dto.EinheitDto;

public interface EinheitJdbcRepository extends CrudRepository<EinheitDto, Long> {
}
