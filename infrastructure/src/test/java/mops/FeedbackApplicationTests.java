package mops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mops.database.DozentJdbcRepository;
import mops.database.EinheitJdbcRepository;
import mops.database.VorlesungJdbcRepository;
import mops.database.dto.DozentDto;
import mops.database.dto.EinheitDto;
import mops.database.dto.VorlesungDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class FeedbackApplicationTests {

  @Autowired
  private DozentJdbcRepository dozRepository;
  private VorlesungJdbcRepository vorRepository;
  private EinheitJdbcRepository einheitRepository;

  @Test
  void contextLoads() {
  }

  @Test
  void saveDozent(){
    DozentDto doz = new DozentDto(2762L, "Dozent1");
    DozentDto doz2 = new DozentDto(2332L, "Dozent2");
    dozRepository.save(doz);
    dozRepository.save(doz2);

    Iterable<DozentDto> dozenten = dozRepository.findAll();

    assertThat(dozenten).isNotEmpty();
  }

}
