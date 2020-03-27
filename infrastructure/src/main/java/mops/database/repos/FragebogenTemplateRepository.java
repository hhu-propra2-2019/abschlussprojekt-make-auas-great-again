package mops.database.repos;

import org.springframework.data.repository.CrudRepository;
import mops.database.dto.FragebogenTemplateDto;

public interface FragebogenTemplateRepository extends CrudRepository<FragebogenTemplateDto, Long> {

}
