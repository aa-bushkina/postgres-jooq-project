package ru.vk.application.utils;

import generated.tables.records.ProductsRecord;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

public record ProductInfo(@NotNull ProductsRecord product,
                          BigDecimal quantity,
                          BigDecimal sum) {
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ProductInfo info = (ProductInfo) o;
    return product.equals(info.product)
      && product.equals(((ProductInfo) o).product);
  }

  @Override
  public int hashCode() {
    return Objects.hash(product, quantity, sum.doubleValue());
  }
}
