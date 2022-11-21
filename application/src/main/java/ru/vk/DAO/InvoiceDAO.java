package ru.vk.DAO;

import com.google.inject.Inject;
import generated.tables.records.InvoicesRecord;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import ru.vk.application.utils.DBProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static generated.tables.Invoices.INVOICES;
import static org.jooq.impl.DSL.row;

@SuppressWarnings({"NotNullNullableValidation", "SqlNoDataSourceInspection", "SqlResolve"})
public final class InvoiceDAO implements Dao<InvoicesRecord> {
  @NotNull
  final DBProperties dbProperties;

  @Inject
  public InvoiceDAO(@NotNull final DBProperties dbProperties) {
    this.dbProperties = dbProperties;
  }

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(
      dbProperties.connection() + dbProperties.name(),
      dbProperties.username(),
      dbProperties.password());
  }

  @Override
  public @NotNull InvoicesRecord get(final int id) {
    try (Connection conn = getConnection()) {
      final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
      final Record record = context
        .select()
        .from(INVOICES)
        .where(INVOICES.ID.eq(id))
        .fetchOne();
      return (record == null) ? new InvoicesRecord() : record.into(INVOICES);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    throw new IllegalStateException("Record with id " + id + "not found");
  }

  @Override
  public @NotNull List<InvoicesRecord> all() {
    try (Connection conn = getConnection()) {
      final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
      final @NotNull Result<Record> result = context
        .select()
        .from(INVOICES)
        .fetch();
      ArrayList<InvoicesRecord> list = new ArrayList<>();
      result.forEach(record -> list.add((InvoicesRecord) record));
      return list;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }

  @Override
  public int save(@NotNull final InvoicesRecord entity) {
    try (var conn = getConnection()) {
      final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
      return context
        .insertInto(INVOICES, INVOICES.NUM, INVOICES.DATE, INVOICES.ORGANIZATION_ID)
        .values(entity.getNum(), entity.getDate(), entity.getOrganizationId())
        .returning(INVOICES.ID)
        .execute();

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  @Override
  public void update(@NotNull final InvoicesRecord entity) {
    try (var conn = getConnection()) {
      final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
      context.update(INVOICES)
        .set(row(INVOICES.NUM, INVOICES.DATE, INVOICES.ORGANIZATION_ID),
          row(entity.getNum(), entity.getDate(), entity.getOrganizationId()))
        .where(INVOICES.ID.eq(entity.getId()))
        .execute();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void delete(@NotNull final InvoicesRecord entity) {
    try (var conn = getConnection()) {
      final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
      context
        .delete(INVOICES)
        .where(INVOICES.ID.eq(entity.getId()))
        .execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
