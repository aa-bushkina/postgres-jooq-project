package ru.vk.entities;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class Product {
  public final int id;
  @NotNull
  public final String name;
  @NotNull
  public final String internalCode;

  public Product(final int id, @NotNull final String name, @NotNull final String internalCode) {
    this.id = id;
    this.name = name;
    this.internalCode = internalCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final Product other = (Product) o;
    return this.id == other.id
      && this.internalCode.equals(other.internalCode)
      && this.name.equals(other.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, internalCode, name);
  }

  @Override
  public @NotNull String toString() {
    return "\nProduct{" +
      "\nid='" + id +
      "',\nname='" + name +
      "',\ninternal code='" + internalCode +
      "'}\n";
  }
}