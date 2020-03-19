package mops.database.dto;

import org.springframework.data.relational.core.mapping.Table;

@Table("studentBeantwortetFragebogen")
public class sBeantwortetF {
  private String student;

  public sBeantwortetF(String student) {
    this.student = student;
  }
}
