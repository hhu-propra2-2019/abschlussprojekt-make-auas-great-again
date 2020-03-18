package mops.rollen;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(of = "username")
public class Student {
  String username;
}
