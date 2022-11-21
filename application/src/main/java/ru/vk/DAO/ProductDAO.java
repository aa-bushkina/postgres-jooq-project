package ru.vk.DAO;

import com.google.inject.Inject;
import generated.tables.records.ProductsRecord;
import org.jetbrains.annotations.NotNull;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;
import ru.vk.application.utils.DBProperties;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static generated.tables.Invoices.INVOICES;
import static generated.tables.InvoicesPositions.INVOICES_POSITIONS;
import static generated.tables.Positions.POSITIONS;
import static generated.tables.Products.PRODUCTS;
import static org.jooq.impl.DSL.avg;
import static org.jooq.impl.DSL.row;

@SuppressWarnings({"NotNullNullableValidation", "SqlNoDataSourceInspection", "SqlResolve"})
public final class ProductDAO implements Dao<ProductsRecord> {
  @NotNull
  final DBProperties dbProperties;

  @Inject
  public ProductDAO(@NotNull final DBProperties dbProperties) {
    this.dbProperties = dbProperties;
  }

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(
      dbProperties.connection() + dbProperties.name(),
      dbProperties.username(),
      dbProperties.password());
  }

  @Override
  public @NotNull ProductsRecord get(final int id) {
    try (Connection conn = getConnection()) {
      final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
      final Record record = context
        .select()
        .from(PRODUCTS)
        .where(PRODUCTS.ID.eq(id))
        .fetchOne();
      return (record == null) ? new ProductsRecord() : record.into(PRODUCTS);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    throw new IllegalStateException("Record with id " + id + "not found");
  }

  @Override
  public @NotNull List<ProductsRecord> all() {
    try (Connection conn = getConnection()) {
      final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
      final @NotNull Result<Record> result = context
        .select()
        .from(PRODUCTS)
        .fetch();
      ArrayList<ProductsRecord> list = new ArrayList<>();
      result.forEach(record -> list.add((ProductsRecord) record));
      return list;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }

  @Override
  public int save(@NotNull final ProductsRecord entity) {
    try (var conn = getConnection()) {
      final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
      context
        .insertInto(PRODUCTS, PRODUCTS.NAME, PRODUCTS.INTERNAL_CODE)
        .values(entity.getName(), entity.getInternalCode())
        .execute();
      return context.lastID().intValue();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  @Override
  public void update(@NotNull final ProductsRecord entity) {
    try (var conn = getConnection()) {
      final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
      context.update(PRODUCTS)
        .set(row(PRODUCTS.NAME, PRODUCTS.NAME, PRODUCTS.INTERNAL_CODE),
          row(entity.getName(), entity.getName(), entity.getInternalCode()))
        .where(PRODUCTS.ID.eq(entity.getId()))
        .execute();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void delete(@NotNull final ProductsRecord entity) {
    try (var conn = getConnection()) {
      final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
      context
        .delete(PRODUCTS)
        .where(PRODUCTS.ID.eq(entity.getId()))
        .execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Map<ProductsRecord, BigDecimal> getAverageOfProductPrice(@NotNull final String startDate,
                                                                  @NotNull final String endDate) {
    try (var conn = getConnection()) {
      final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
      final Result<Record4<Integer, String, String, BigDecimal>> records = context
        .select(
          PRODUCTS.ID,
          PRODUCTS.NAME,
          PRODUCTS.INTERNAL_CODE,
          avg(POSITIONS.PRICE))
        .from(POSITIONS)
        .join(INVOICES_POSITIONS)
        .on(POSITIONS.ID.eq(INVOICES_POSITIONS.POSITION_ID))
        .join(INVOICES)
        .on(INVOICES.ID.eq(INVOICES_POSITIONS.INVOICE_ID))
        .join(PRODUCTS)
        .on(PRODUCTS.ID.eq(POSITIONS.PRODUCT_ID))
        .where(INVOICES.DATE.greaterOrEqual(LocalDate.parse(startDate)))
        .and(INVOICES.DATE.lessOrEqual(LocalDate.parse(endDate)))
        .groupBy(PRODUCTS.ID, PRODUCTS.NAME)
        .orderBy(PRODUCTS.NAME).fetch();

      final LinkedHashMap<ProductsRecord, BigDecimal> map = new LinkedHashMap<>();
      for (Record4<Integer, String, String, BigDecimal> record : records) {
        map.put(new ProductsRecord((Integer) record.get(0),
            (String) record.get(1), (String) record.get(2)),
          ((BigDecimal) record.get(3)).setScale(2, RoundingMode.CEILING));
      }

      return map;
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
    return new LinkedHashMap<>();
  }
}
