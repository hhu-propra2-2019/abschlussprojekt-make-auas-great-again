package mops.database.dto;

import org.springframework.data.relational.core.mapping.Table;

@Table("studentBeantwortetFragebogen")
public class sBeantwortetF {
  transient Long student;

  public sBeantwortetF(Long student) {
    this.student = student;
  }
}
