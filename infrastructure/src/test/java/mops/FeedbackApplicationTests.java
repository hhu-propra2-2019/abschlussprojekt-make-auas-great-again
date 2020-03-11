package mops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import mops.database.DozentJdbcRepository;
import mops.database.dto.DozentDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FeedbackApplicationTests {

  @Autowired
  private transient DozentJdbcRepository dozRepository;

  @Test
  @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
  void contextLoads() {

  }

  @Test
  @Disabled
  void saveDozent() {
    DozentDto doz = new DozentDto("Dozent1", new HashSet<>());
    DozentDto doz2 = new DozentDto("Dozent2", new HashSet<>());
    dozRepository.save(doz);
    dozRepository.save(doz2);

    Iterable<DozentDto> dozenten = dozRepository.findAll();

    assertThat(dozenten).isNotEmpty();
  }

}
