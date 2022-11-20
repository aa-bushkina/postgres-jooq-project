package ru.vk.DAO;

import com.google.inject.Inject;
import generated.tables.records.ProductsRecord;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.Record4;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import ru.vk.DAO.utils.Queries;
import ru.vk.application.utils.DBProperties;
import ru.vk.application.utils.ProductInfo;
import ru.vk.entities.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
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

@SuppressWarnings({"NotNullNullableValidation", "SqlNoDataSourceInspection", "SqlResolve"})
public final class ProductDAO implements Dao<Product> {
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
  public @NotNull Product get(final int id) {
    try {
      var preparedStatement = getConnection()
        .prepareStatement("SELECT id, name, internal_code FROM products WHERE id = ?");
      preparedStatement.setInt(1, id);
      preparedStatement.execute();
      ResultSet resultSet = preparedStatement.getResultSet();
      if (resultSet.next()) {
        return new Product(resultSet.getInt("id"),
          resultSet.getString("name"),
          resultSet.getString("internal_code"));
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    throw new IllegalStateException("Record with id " + id + "not found");
  }

  @Override
  public @NotNull List<Product> all() {
    final var result = new ArrayList<Product>();
    try (var statement = getConnection().createStatement()) {
      try (var resultSet = statement.executeQuery("SELECT * FROM products")) {
        while (resultSet.next()) {
          result.add(new Product(resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getString("internal_code")));
        }
        return result;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public void save(@NotNull final Product entity) {
    try (var preparedStatement = getConnection()
      .prepareStatement("INSERT INTO products(id, name, internal_code) VALUES(?,?,?)")) {
      preparedStatement.setInt(1, entity.id);
      preparedStatement.setString(2, entity.name);
      preparedStatement.setString(3, entity.internalCode);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void update(@NotNull final Product entity) {
    try (var preparedStatement = getConnection()
      .prepareStatement("UPDATE products SET name = ?, internal_code = ? WHERE id = ?")) {
      preparedStatement.setString(1, entity.name);
      preparedStatement.setString(2, entity.internalCode);
      preparedStatement.setInt(3, entity.id);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void delete(@NotNull final Product entity) {
    try (var preparedStatement = getConnection().prepareStatement("DELETE FROM products WHERE id = ?")) {
      preparedStatement.setInt(1, entity.id);
      if (preparedStatement.executeUpdate() == 0) {
        throw new IllegalStateException("Record with id = " + entity.id + " not found");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public LinkedHashMap<Date, ArrayList<ProductInfo>> getEverydayProductCharacteristics() {
    try (var statement = getConnection().prepareStatement(
      Queries.EVERYDAY_PRODUCT_CHARACTERISTICS_QUERY,
      ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
      final Date startDate = Date.valueOf("2022-11-03");
      final Date endDate = Date.valueOf("2022-11-04");
      statement.setDate(1, startDate);
      statement.setDate(2, endDate);

      try (var resultSet = statement.executeQuery()) {
        ArrayList<ProductInfo> productsInfoList = new ArrayList<>();
        LinkedHashMap<Date, ArrayList<ProductInfo>> map = new LinkedHashMap<>();
        Date date;
        Date currentDate = null;
        boolean isFirstRow = true;
        while (resultSet.next()) {
          date = resultSet.getDate("date");
          if (isFirstRow) {
            isFirstRow = false;
            currentDate = date;
          }
          if (date.equals(currentDate)) {
            productsInfoList.add(new ProductInfo(new Product(
              resultSet.getInt("prod_id"),
              resultSet.getString("name"),
              resultSet.getString("internal_code")),
              resultSet.getInt("quantity"),
              resultSet.getBigDecimal("sum")));
            if (!resultSet.isLast()) {
              continue;
            }
          }
          resultSet.previous();
          map.put(currentDate, new ArrayList<>(productsInfoList));
          productsInfoList.clear();
          currentDate = date;
          resultSet.next();
          if (!resultSet.isLast()) {
            resultSet.previous();
          }
        }
        return map;
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
    return new LinkedHashMap<>();
  }

  public Map<ProductsRecord, BigDecimal> getAverageOfProductPrice(@NotNull final String startDate,
                                                                  @NotNull final String endDate) {
    try (var conn = getConnection()) {
      final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
      final Result<Record4<Integer, String, String, BigDecimal>> records = context
        .select(PRODUCTS.ID, PRODUCTS.NAME, PRODUCTS.INTERNAL_CODE, avg(POSITIONS.PRICE))
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

      LinkedHashMap<ProductsRecord, BigDecimal> map = new LinkedHashMap<>();
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
