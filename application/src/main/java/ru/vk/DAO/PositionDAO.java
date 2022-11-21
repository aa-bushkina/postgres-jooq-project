package ru.vk.DAO;

import com.google.inject.Inject;
import generated.tables.records.PositionsRecord;
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

import static generated.tables.Positions.POSITIONS;
import static generated.tables.Products.PRODUCTS;
import static org.jooq.impl.DSL.row;

@SuppressWarnings({"NotNullNullableValidation", "SqlNoDataSourceInspection", "SqlResolve"})
public final class PositionDAO implements Dao<PositionsRecord> {
  @NotNull
  final DBProperties dbProperties;

  @Inject
  public PositionDAO(@NotNull final DBProperties dbProperties) {
    this.dbProperties = dbProperties;
  }

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(
      dbProperties.connection() + dbProperties.name(),
      dbProperties.username(),
      dbProperties.password());
  }

  @Override
  public @NotNull PositionsRecord get(final int id) {
    try (Connection conn = getConnection()) {
      final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
      final Record record = context
        .select()
        .from(POSITIONS)
        .where(POSITIONS.ID.eq(id))
        .fetchOne();
      return (record == null) ? new PositionsRecord() : record.into(POSITIONS);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    throw new IllegalStateException("Record with id " + id + "not found");
  }

  @Override
  public @NotNull List<PositionsRecord> all() {
    try (Connection conn = getConnection()) {
      final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
      final @NotNull Result<Record> result = context
        .select()
        .from(POSITIONS)
        .fetch();
      ArrayList<PositionsRecord> list = new ArrayList<>();
      result.forEach(record -> list.add((PositionsRecord) record));
      return list;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }

  @Override
  public void save(@NotNull final PositionsRecord entity) {
    try (var conn = getConnection()) {
      final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
      context
        .insertInto(POSITIONS, POSITIONS.PRICE, POSITIONS.PRODUCT_ID, POSITIONS.QUANTITY)
        .values(entity.getPrice(), entity.getProductId(), entity.getQuantity())
        .execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void update(@NotNull final PositionsRecord entity) {
    try (var conn = getConnection()) {
      final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
      context.update(POSITIONS)
        .set(row(POSITIONS.PRICE, POSITIONS.PRODUCT_ID, POSITIONS.QUANTITY),
          row(entity.getPrice(), entity.getProductId(), entity.getQuantity()))
        .where(POSITIONS.ID.eq(entity.getId()))
        .execute();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void delete(@NotNull final PositionsRecord entity) {
    try (var conn = getConnection()) {
      final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
      context
        .delete(POSITIONS)
        .where(POSITIONS.ID.eq(entity.getId()))
        .execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
