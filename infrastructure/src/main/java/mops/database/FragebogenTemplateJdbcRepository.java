package mops.database;

import mops.database.dto.FragebogenTemplateDto;
import org.springframework.data.repository.CrudRepository;

public interface FragebogenTemplateJdbcRepository extends CrudRepository<FragebogenTemplateDto, Long> {
}
