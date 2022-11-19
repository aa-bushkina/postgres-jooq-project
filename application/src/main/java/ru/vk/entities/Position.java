package ru.vk.entities;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

public final class Position {
  public final int id;
  public final BigDecimal price;
  public final int product_id;
  public final int quantity;

  public Position(final int id, final BigDecimal price, final int product_id, final int quantity) {
    this.id = id;
    this.price = price;
    this.product_id = product_id;
    this.quantity = quantity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final Position other = (Position) o;
    return this.id == other.id
      && this.price.equals(other.price)
      && (this.product_id == other.product_id)
      && (this.quantity == other.quantity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, price, product_id, quantity);
  }

  @Override
  public @NotNull String toString() {
    return "\nPosition{" +
      "\nid='" + id +
      "',\nprice='" + price +
      "',\nproduct_id='" + product_id +
      "',\nquantity='" + quantity +
      "'}\n";
  }
}