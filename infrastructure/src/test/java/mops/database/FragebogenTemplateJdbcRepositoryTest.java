package mops.database;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import mops.database.dto.DozentDto;
import mops.database.dto.FrageDto;
import mops.database.dto.FragebogenTemplateDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

@Disabled
@DataJdbcTest
public class FragebogenTemplateJdbcRepositoryTest {

  @Autowired
  private transient FragebogenTemplateJdbcRepository repository;
  @Autowired
  private transient DozentenJdbcRepository dozentenRepo;

  @Test
  void findDozentenByUsernameTest() {
    DozentDto dozent = DozentDto.create("user1", "chris", "ru");
    dozentenRepo.save(dozent);
    DozentDto found = dozentenRepo.getDozentByUsername("user1");
    assertThat(found).isEqualTo(dozent);
  }

  @Test
  void createNewTemplateTest() {
    DozentDto dozent = DozentDto.create("orga1", "jens", "ben");
    FragebogenTemplateDto template = FragebogenTemplateDto.create("Testtemplate");
    template.addFrage(FrageDto.createTextfrage("Warum?"));
    dozent.addFragebogenTemplate(template);
    dozentenRepo.save(dozent);
    repository.save(template);
    List<Long> list = new ArrayList<>();
    Iterable<FragebogenTemplateDto> templates = repository.findAll();
    templates.forEach(t -> list.add(t.getId()));
    assertThat(list).contains(template.getId());
  }
}
