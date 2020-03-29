package mops.database.repos;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import mops.database.dto.FragebogenDto;

public interface FragebogenRepository extends CrudRepository<FragebogenDto, Long> {
  @Modifying
  @Query("UPDATE frage SET oeffentlich = true WHERE id = :id")
  void markQuestionAsPublicById(@Param("id") Long id);

  @Modifying
  @Query("UPDATE frage SET oeffentlich = false WHERE id = :id")
  void markQuestionAsPrivateById(@Param("id") Long id);

  @Modifying
  @Query("UPDATE antwort SET textantwort = :antwort WHERE id = :id")
  void zensiereTextAntwortById(@Param("antwort") String antwort, @Param("id") Long id);
}
