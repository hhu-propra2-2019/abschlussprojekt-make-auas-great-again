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
      "select veranstaltung from studentBelegtVeranstaltung where student= :studentid)")
  Set<VeranstaltungDto> getAllFromStudent(@Param("studentid") Long studentid);

  @Query("select * from veranstaltung where id in(" +
      "select veranstaltung from dozentOrganisiertVeranstaltung where dozent= :dozentid)")
  Set<VeranstaltungDto> getAllFromDozent(@Param("dozentid") Long dozentid);

  @Query("select * from veranstaltung where upper(name) like '%'|| upper(:search) || '%' and id in(" +
      "select veranstaltung from studentBelegtVeranstaltung where student= :studentid)")
  Set<VeranstaltungDto> getAllFromStudentContaining(@Param("studentid") Long studentid, @Param("search") String search);

  @Query("select * from veranstaltung where upper(name) like '%'|| upper(:search) || '%' and id in(" +
      "select veranstaltung from dozentOrganisiertVeranstaltung where dozent= :dozentid)")
  Set<VeranstaltungDto> getAllFromDozentContaining(@Param("dozentid") Long dozentid, @Param("search") String search);

}
