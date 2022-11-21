package ru.vk.application.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils {

  public static BigDecimal makeBigDecimal(final double x) {
    return BigDecimal.valueOf(x).setScale(2, RoundingMode.CEILING);
  }
}
