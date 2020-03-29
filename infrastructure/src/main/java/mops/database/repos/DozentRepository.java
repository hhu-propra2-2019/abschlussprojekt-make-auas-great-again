package mops.database.repos;

import mops.database.dto.DozentDto;
import org.springframework.data.repository.CrudRepository;

public interface DozentRepository extends CrudRepository<DozentDto, String> {

}
