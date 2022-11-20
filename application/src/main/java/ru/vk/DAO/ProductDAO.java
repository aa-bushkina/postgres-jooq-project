package ru.vk.DAO;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import ru.vk.DAO.utils.Queries;
import ru.vk.application.utils.DBProperties;
import ru.vk.application.utils.ProductInfo;
import ru.vk.entities.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

  public Map<Product, Double> getAverageOfProductPrice() {
    try (var statement = getConnection().prepareStatement(
      Queries.AVG_OF_PRODUCT_PRICE_QUERY)) {
      final Date startDate = Date.valueOf("2022-11-01");
      final Date endDate = Date.valueOf("2022-11-06");
      statement.setDate(1, startDate);
      statement.setDate(2, endDate);
      try (var resultSet = statement.executeQuery()) {
        LinkedHashMap<Product, Double> map = new LinkedHashMap<>();
        while (resultSet.next()) {
          map.put(new Product(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getString("internal_code")), resultSet.getDouble("avg"));
        }
        return map;
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
    return new LinkedHashMap<>();
  }
}
