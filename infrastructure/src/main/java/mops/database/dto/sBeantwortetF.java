package mops.database.dto;

import org.springframework.data.relational.core.mapping.Table;

@Table("studentBeantwortetFragebogen")
public class sBeantwortetF {
  private Long fragebogen;

  public sBeantwortetF(Long fragebogen) {
    this.fragebogen = fragebogen;
  }
}
