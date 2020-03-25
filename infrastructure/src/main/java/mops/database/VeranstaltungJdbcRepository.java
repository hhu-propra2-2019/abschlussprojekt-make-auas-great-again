package mops.database;

import java.util.Set;
import mops.database.dto.VeranstaltungDto;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface VeranstaltungJdbcRepository extends CrudRepository<VeranstaltungDto, Long> {

  @Query("select * from veranstaltung where upper(name) like '%'|| upper(:search) || '%'")
  Set<VeranstaltungDto> getAllContaining(@Param("search") String search);

  @Query("select * from veranstaltung where id in(" +
      "select veranstaltung from studentBelegtVeranstaltung where student= :tofind)")
  Set<VeranstaltungDto> getAllFromStudent(@Param("tofind") Long tofind);

  @Query("select * from veranstaltung where veranstaltung.dozent= :tofind")
  Set<VeranstaltungDto> getAllFromDozent(@Param("tofind") Long tofind);

  @Query("select * from veranstaltung where id in(" +
      "select veranstaltung from studentBelegtVeranstaltung where student= :tofind)" +
      "and upper(name) like '%'|| upper(:search) || '%' ")
  Set<VeranstaltungDto> getAllFromStudentContaining(@Param("tofind") Long tofind, @Param("search") String search);

  @Query("select * from veranstaltung where veranstaltung.dozent = :tofind " +
      "and upper(name) like '%'|| upper(:search) || '%' ")
  Set<VeranstaltungDto> getAllFromDozentContaining(@Param("tofind") Long tofind, @Param("search") String search);

}
