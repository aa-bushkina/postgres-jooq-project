package ru.vk.DAO;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import ru.vk.DAO.utils.Queries;
import ru.vk.application.utils.DBProperties;
import ru.vk.entities.Organization;
import ru.vk.entities.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"NotNullNullableValidation", "SqlNoDataSourceInspection", "SqlResolve"})
public final class OrganizationDAO implements Dao<Organization> {
  @NotNull
  final DBProperties dbProperties;

  @Inject
  public OrganizationDAO(@NotNull final DBProperties dbProperties) {
    this.dbProperties = dbProperties;
  }

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(
      dbProperties.connection() + dbProperties.name(),
      dbProperties.username(),
      dbProperties.password());
  }

  @Override
  public @NotNull Organization get(final int id) {
    try {
      var preparedStatement = getConnection()
        .prepareStatement("SELECT id, name, inn, payment_account FROM organizations WHERE id = ?");
      preparedStatement.setInt(1, id);
      preparedStatement.execute();
      ResultSet resultSet = preparedStatement.getResultSet();
      if (resultSet.next()) {
        return new Organization(resultSet.getInt("id"),
          resultSet.getString("name"),
          resultSet.getString("inn"),
          resultSet.getString("payment_account"));
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    throw new IllegalStateException("Record with id " + id + " not found");
  }

  @Override
  public @NotNull List<Organization> all() {
    final var result = new ArrayList<Organization>();
    try (var statement = getConnection().createStatement()) {
      try (var resultSet = statement.executeQuery("SELECT * FROM organizations")) {
        while (resultSet.next()) {
          result.add(new Organization(resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getString("inn"),
            resultSet.getString("payment_account")));
        }
        return result;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public void save(@NotNull final Organization entity) {
    try (var preparedStatement = getConnection()
      .prepareStatement("INSERT INTO organizations(id, name, inn, payment_account) VALUES(?,?,?,?)")) {
      preparedStatement.setInt(1, entity.id);
      preparedStatement.setString(2, entity.name);
      preparedStatement.setString(3, entity.inn);
      preparedStatement.setString(4, entity.paymentAccount);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void update(@NotNull final Organization entity) {
    try (var preparedStatement = getConnection()
      .prepareStatement("UPDATE organizations SET name = ?, inn = ?, payment_account = ? WHERE id = ?")) {
      preparedStatement.setString(1, entity.name);
      preparedStatement.setString(2, entity.inn);
      preparedStatement.setString(3, entity.paymentAccount);
      preparedStatement.setInt(4, entity.id);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void delete(@NotNull final Organization entity) {
    try (var preparedStatement = getConnection().prepareStatement("DELETE FROM organizations WHERE id = ?")) {
      preparedStatement.setInt(1, entity.id);
      if (preparedStatement.executeUpdate() == 0) {
        throw new IllegalStateException("Record with id = " + entity.id + " not found");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Map<Organization, Integer> getTop10OrganizationsByQuantity() {
    try (var statement = getConnection().prepareStatement(
      Queries.ORGANIZATIONS_BY_QUANTITY_QUERY)) {
      final int limitValue = 10;
      statement.setInt(1, limitValue);
      try (var resultSet = statement.executeQuery()) {
        LinkedHashMap<Organization, Integer> map = new LinkedHashMap<>();
        while (resultSet.next()) {
          map.put(new Organization(
              resultSet.getInt("id"),
              resultSet.getString("name"),
              resultSet.getString("inn"),
              resultSet.getString("payment_account")),
            resultSet.getInt("quantity"));
        }
        return map;
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
    return new LinkedHashMap<>();
  }

  public Map<Organization, Integer> getOrganizationsWithDefiniteQuantity() {
    try (var statement = getConnection().prepareStatement(
      Queries.ORGANIZATION_WITH_DEFINITE_QUANTITY_QUERY)) {
      final int productId = 15;
      final int quantityValue = 1000;
      statement.setInt(1, productId);
      statement.setInt(2, quantityValue);
      try (var resultSet = statement.executeQuery()) {
        LinkedHashMap<Organization, Integer> map = new LinkedHashMap<>();
        while (resultSet.next()) {
          map.put(new Organization(
              resultSet.getInt("org_id"),
              resultSet.getString("org_name"),
              resultSet.getString("inn"),
              resultSet.getString("payment_account")),
            resultSet.getInt("quantity"));
        }
        return map;
      }
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
    return new LinkedHashMap<>();
  }

  public Map<Organization, List<Product>> getProductsListByOrganizations() {
    try (var statement =
           getConnection().prepareStatement(
             Queries.PRODUCT_LIST_BY_ORGANIZATION_QUERY,
             ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
      final Date startDate = Date.valueOf("2022-11-05");
      final Date endDate = Date.valueOf("2022-11-09");
      statement.setDate(1, startDate);
      statement.setDate(2, endDate);
      try (var resultSet = statement.executeQuery()) {
        ArrayList<Product> productsList = new ArrayList<>();
        Map<Organization, List<Product>> map = new LinkedHashMap<>();
        int currentId = 0;
        int organizationId;
        boolean isFirstRow = true;
        while (resultSet.next()) {
          organizationId = resultSet.getInt("org_id");
          if (isFirstRow) {
            isFirstRow = false;
            currentId = organizationId;
          }
          if (organizationId == currentId) {
            productsList.add(new Product(
              resultSet.getInt("pr_id"),
              resultSet.getString("name"),
              resultSet.getString("internal_code")));
            if (!resultSet.isLast()) {
              continue;
            }
          }
          resultSet.previous();
          map.put(new Organization(
              resultSet.getInt("org_id"),
              resultSet.getString("org_name"),
              resultSet.getString("inn"),
              resultSet.getString("payment_account")),
            new ArrayList<>(productsList));
          productsList.clear();
          currentId = organizationId;
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
}
