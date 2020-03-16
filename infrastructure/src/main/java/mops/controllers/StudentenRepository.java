package mops.controllers;

import java.util.List;
import mops.Fragebogen;
import mops.rollen.Student;

public interface StudentenRepository {
  List<Student> getAll();

  Student getStudentById(Long id);

  void addFragebogenTo(Long id, Fragebogen fragebogen);

  void save(Student neuerStudent);
}
