package ru.vk.entities;

import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.util.Objects;

public final class Invoice {
  public final int id;
  @NotNull
  public final String num;
  @NotNull
  public final Date date;
  public final int organization_id;

  public Invoice(final int id, @NotNull final String num, @NotNull final Date date, final int organization_id) {
    this.id = id;
    this.num = num;
    this.date = date;
    this.organization_id = organization_id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final Invoice other = (Invoice) o;
    return this.id == other.id
      && this.num.equals(other.num)
      && this.date.equals(other.date)
      && this.organization_id == other.organization_id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, num, date, organization_id);
  }

  @Override
  public @NotNull String toString() {
    return "\nInvoice{" +
      "\nid='" + id +
      "',\nnumber='" + num +
      "',\ndate='" + date +
      "',\nsender organization id='" + organization_id +
      "'}\n";
  }
}