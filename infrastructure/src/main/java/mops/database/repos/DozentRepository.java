package mops.database.repos;

import org.springframework.data.repository.CrudRepository;
import mops.database.dto.DozentDto;

public interface DozentRepository extends CrudRepository<DozentDto, String> {

}
