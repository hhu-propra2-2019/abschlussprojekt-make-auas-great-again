package mops.database.repos;

import org.springframework.data.repository.CrudRepository;
import mops.database.dto.FragebogenDto;

public interface FragebogenRepository extends CrudRepository<FragebogenDto, Long> {

}
