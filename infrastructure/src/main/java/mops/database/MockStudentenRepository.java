package mops.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mops.Fragebogen;
import mops.controllers.FragebogenRepository;
import mops.controllers.StudentenRepository;
import mops.rollen.Student;
import org.springframework.stereotype.Repository;

@Repository
public class MockStudentenRepository implements StudentenRepository {

  private transient Map<Long, Student> studenten;
  private transient long current_max_id = 10L;
  private transient FragebogenRepository frageboegen;

  public MockStudentenRepository() {
    frageboegen = new MockFragebogenRepository();
    studenten = new HashMap<>();
    for (long i = 0L; i < 10L; i++) {
      studenten.put(i, generateRandomStudent());
    }
  }

  private Student generateRandomStudent() {
    Student.StudentBuilder student = Student.builder();
    student = student
        .frageboegen(frageboegen.getAll())
        .nachname("Roßbach")
        .email("jaros105@hhu.de")
        .matnr(1234567)
        .vorname("Jan");
    return student.build();
  }

  @Override
  public List<Student> getAll() {
    return new ArrayList<>(studenten.values());
  }

  @Override
  public Student getStudentById(Long id) {
    return studenten.get(id);
  }

  @Override
  public void addFragebogenTo(Long id, Fragebogen fragebogen) {
    Student student = studenten.get(id);
    student.addFragebogen(fragebogen);
  }

  @Override
  public void save(Student neuerStudent) {
    studenten.put(current_max_id++, neuerStudent);
  }
}
