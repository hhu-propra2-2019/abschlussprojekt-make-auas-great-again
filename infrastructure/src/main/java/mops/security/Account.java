package mops.security;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Account {
  private final String name;
  private final String email;
  private final String image;
  private final Set<String> roles;
}
