package ru.vk.DAO;

import com.google.inject.Inject;
import generated.tables.records.OrganizationsRecord;
import generated.tables.records.ProductsRecord;
import org.jetbrains.annotations.NotNull;
import org.jooq.*;
import org.jooq.impl.DSL;
import ru.vk.application.utils.DBProperties;
import ru.vk.entities.Organization;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static generated.tables.Invoices.INVOICES;
import static generated.tables.InvoicesPositions.INVOICES_POSITIONS;
import static generated.tables.Organizations.ORGANIZATIONS;
import static generated.tables.Positions.POSITIONS;
import static generated.tables.Products.PRODUCTS;
import static org.jooq.impl.DSL.coalesce;
import static org.jooq.impl.DSL.sum;

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

  public Map<OrganizationsRecord, BigDecimal> getTop10OrganizationsByQuantity(final int limit) {
    try (var conn = getConnection()) {
      final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
      final @NotNull Result<Record5<Integer, String, String, String, BigDecimal>> records = context
        .select(
          ORGANIZATIONS.ID,
          ORGANIZATIONS.NAME,
          ORGANIZATIONS.INN,
          ORGANIZATIONS.PAYMENT_ACCOUNT,
          sum(coalesce(POSITIONS.QUANTITY, 0)))
        .from(ORGANIZATIONS)
        .leftJoin(INVOICES)
        .on(INVOICES.ORGANIZATION_ID.eq(ORGANIZATIONS.ID))
        .leftJoin(INVOICES_POSITIONS)
        .on(INVOICES.ID.eq(INVOICES_POSITIONS.INVOICE_ID))
        .leftJoin(POSITIONS)
        .on(INVOICES_POSITIONS.POSITION_ID.eq(POSITIONS.ID))
        .groupBy(
          ORGANIZATIONS.NAME,
          ORGANIZATIONS.ID,
          ORGANIZATIONS.INN,
          ORGANIZATIONS.PAYMENT_ACCOUNT)
        .orderBy(sum(coalesce(POSITIONS.QUANTITY, 0)).desc())
        .limit(limit)
        .fetch();

      final LinkedHashMap<OrganizationsRecord, BigDecimal> map = new LinkedHashMap<>();
      for (Record5<Integer, String, String, String, BigDecimal> record : records) {
        map.put(new OrganizationsRecord((Integer) record.get(0),
            (String) record.get(1), (String) record.get(2), (String) record.get(3)),
          ((BigDecimal) record.get(4)).setScale(2, RoundingMode.CEILING));
      }
      return map;
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
    return new LinkedHashMap<>();
  }

  public Map<OrganizationsRecord, BigDecimal> getOrganizationsWithDefiniteQuantity(final int productId,
                                                                                   @NotNull final BigDecimal quantity) {
    try (var conn = getConnection()) {
      final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
      final @NotNull Result<Record5<Integer, String, String, String, BigDecimal>> records =
        context.select(
            ORGANIZATIONS.ID,
            ORGANIZATIONS.NAME,
            ORGANIZATIONS.INN,
            ORGANIZATIONS.PAYMENT_ACCOUNT,
            sum(coalesce(POSITIONS.QUANTITY, 0)))
          .from(ORGANIZATIONS)
          .leftJoin(INVOICES)
          .on(INVOICES.ORGANIZATION_ID.eq(ORGANIZATIONS.ID))
          .leftJoin(INVOICES_POSITIONS)
          .on(INVOICES.ID.eq(INVOICES_POSITIONS.INVOICE_ID))
          .leftJoin(POSITIONS)
          .on(INVOICES_POSITIONS.POSITION_ID.eq(POSITIONS.ID))
          .leftJoin(PRODUCTS)
          .on(POSITIONS.PRODUCT_ID.eq(PRODUCTS.ID))
          .where(PRODUCTS.ID.eq(productId))
          .groupBy(
            ORGANIZATIONS.NAME,
            ORGANIZATIONS.ID,
            ORGANIZATIONS.INN,
            ORGANIZATIONS.PAYMENT_ACCOUNT)
          .having(sum(coalesce(POSITIONS.QUANTITY, 0)).gt(quantity))
          .orderBy(sum(coalesce(POSITIONS.QUANTITY, 0)).desc())
          .fetch();

      final LinkedHashMap<OrganizationsRecord, BigDecimal> map = new LinkedHashMap<>();
      for (Record5<Integer, String, String, String, BigDecimal> record : records) {
        map.put(new OrganizationsRecord((Integer) record.get(0),
            (String) record.get(1), (String) record.get(2), (String) record.get(3)),
          ((BigDecimal) record.get(4)).setScale(2, RoundingMode.CEILING));
      }
      return map;
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
    return new LinkedHashMap<>();
  }

  public Map<OrganizationsRecord, List<ProductsRecord>> getProductsListByOrganizations(@NotNull final String startDate,
                                                                                       @NotNull final String endDate) {
    try (var conn = getConnection()) {
      final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);
      final @NotNull Result<Record7<Integer, String, String, String, Integer, String, String>> records =
        context.select(
            ORGANIZATIONS.ID,
            ORGANIZATIONS.NAME,
            ORGANIZATIONS.INN,
            ORGANIZATIONS.PAYMENT_ACCOUNT,
            PRODUCTS.ID,
            PRODUCTS.NAME,
            PRODUCTS.INTERNAL_CODE)
          .from(ORGANIZATIONS)
          .leftJoin(INVOICES)
          .on(INVOICES.ORGANIZATION_ID.eq(ORGANIZATIONS.ID))
          .leftJoin(INVOICES_POSITIONS)
          .on(INVOICES.ID.eq(INVOICES_POSITIONS.INVOICE_ID))
          .leftJoin(POSITIONS)
          .on(INVOICES_POSITIONS.POSITION_ID.eq(POSITIONS.ID))
          .leftJoin(PRODUCTS)
          .on(POSITIONS.PRODUCT_ID.eq(PRODUCTS.ID))
          .where(INVOICES.DATE.greaterOrEqual(LocalDate.parse(startDate))
            .and(INVOICES.DATE.lessOrEqual(LocalDate.parse(endDate))))
          .fetch();

      final LinkedHashMap<OrganizationsRecord, List<ProductsRecord>> map = new LinkedHashMap<>();
      final List<ProductsRecord> list = new ArrayList<>();
      Integer currentOrgId = (Integer) records.get(0).get(0);
      OrganizationsRecord organization = null;
      for (Record7<Integer, String, String, String, Integer, String, String> record : records) {
        if (record.get(0) != currentOrgId) {
          map.put(organization, new ArrayList<>(list));
          list.clear();
        }
        organization = new OrganizationsRecord((Integer) record.get(0),
          (String) record.get(1), (String) record.get(2), (String) record.get(3));
        list.add(new ProductsRecord((Integer) record.get(4), (String) record.get(5), (String) record.get(6)));
        currentOrgId = (Integer) record.get(0);
      }
      map.put(organization, new ArrayList<>(list));
      return map;
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
    return new LinkedHashMap<>();
  }
}
