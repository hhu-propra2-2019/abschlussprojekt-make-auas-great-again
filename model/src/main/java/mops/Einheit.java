package mops;

import java.security.SecureRandom;

public enum Einheit {
  VORLESUNG,
  UEBUNG;

  private static final Einheit[] VALUES = values();
  private static final int SIZE = VALUES.length;
  private static final SecureRandom RANDOM = new SecureRandom();

  public static Einheit getRandomEinheit() {
    return VALUES[RANDOM.nextInt(SIZE)];
  }
}
