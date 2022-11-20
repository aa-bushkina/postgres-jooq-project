package ru.vk.application.utils;

import org.jetbrains.annotations.NotNull;
import ru.vk.entities.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record ProductInfo(@NotNull Product product,
                          int quantity,
                          BigDecimal sum) {
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ProductInfo info = (ProductInfo) o;
    return quantity == info.quantity
      && product.equals(info.product)
      && product.equals(((ProductInfo) o).product);
  }

  @Override
  public int hashCode() {
    return Objects.hash(product, quantity, sum.doubleValue());
  }
}
