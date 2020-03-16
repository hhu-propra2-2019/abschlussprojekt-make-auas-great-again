package mops.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mops.Fragebogen;
import mops.controllers.StudentenRepository;
import mops.rollen.Student;

public class MockStudentenRepository implements StudentenRepository {

  private Map<Long, Student> studenten = new HashMap<>();
  private long current_id = 0L;

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
    studenten.put(current_id++, neuerStudent);
  }
}
