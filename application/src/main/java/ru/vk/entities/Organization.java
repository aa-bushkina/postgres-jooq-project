package ru.vk.entities;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class Organization {
  public final int id;
  @NotNull
  public final String name;
  @NotNull
  public final String inn;
  @NotNull
  public final String paymentAccount;


  public Organization(final int id, @NotNull final String name, @NotNull final String inn, @NotNull final String paymentAccount) {
    this.id = id;
    this.name = name;
    this.inn = inn;
    this.paymentAccount = paymentAccount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final Organization other = (Organization) o;
    return this.id == other.id
      && this.name.equals(other.name)
      && this.inn.equals(other.inn)
      && this.paymentAccount.equals(other.paymentAccount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, inn, paymentAccount);
  }

  @Override
  public @NotNull String toString() {
    return "\nOrganization{" +
      "id='" + id +
      "',\nname='" + name +
      "',\ninn='" + inn +
      "',\npayment account='" + paymentAccount +
      "'}\n";
  }
}