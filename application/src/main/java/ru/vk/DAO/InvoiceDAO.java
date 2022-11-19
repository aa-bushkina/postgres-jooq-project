package ru.vk.DAO;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import ru.vk.application.utils.DBProperties;
import ru.vk.entities.Invoice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"NotNullNullableValidation", "SqlNoDataSourceInspection", "SqlResolve"})
public final class InvoiceDAO implements Dao<Invoice> {
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
  public @NotNull Invoice get(final int id) {
    try {
      var preparedStatement = getConnection()
        .prepareStatement("SELECT * FROM invoices WHERE id = ?");
      {
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        if (resultSet.next()) {
          return new Invoice(resultSet.getInt("id"),
            resultSet.getString("num"),
            resultSet.getDate("date"),
            resultSet.getInt("organization_id"));
        }
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    throw new IllegalStateException("Record with id " + id + "not found");
  }

  @Override
  public @NotNull List<Invoice> all() {
    final var result = new ArrayList<Invoice>();
    try (var statement = getConnection().createStatement()) {
      try (var resultSet = statement.executeQuery("SELECT * FROM invoices")) {
        while (resultSet.next()) {
          result.add(new Invoice(resultSet.getInt("id"),
            resultSet.getString("num"),
            resultSet.getDate("date"),
            resultSet.getInt("organization_id")));
        }
        return result;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public void save(@NotNull final Invoice entity) {
    try (var preparedStatement = getConnection()
      .prepareStatement("INSERT INTO invoices(id, num, date, organization_id) VALUES(?,?,?,?)")) {
      preparedStatement.setInt(1, entity.id);
      preparedStatement.setString(2, entity.num);
      preparedStatement.setDate(3, entity.date);
      preparedStatement.setInt(4, entity.organization_id);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void update(@NotNull final Invoice entity) {
    try (var preparedStatement = getConnection()
      .prepareStatement("UPDATE invoices SET num = ?, " +
        "date = ?, " +
        "organization_id = ? WHERE id = ?")) {
      preparedStatement.setString(1, entity.num);
      preparedStatement.setDate(2, entity.date);
      preparedStatement.setInt(3, entity.organization_id);
      preparedStatement.setInt(4, entity.id);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void delete(@NotNull final Invoice entity) {
    try (var preparedStatement = getConnection()
      .prepareStatement("DELETE FROM invoices WHERE id = ?")) {
      preparedStatement.setInt(1, entity.id);
      if (preparedStatement.executeUpdate() == 0) {
        throw new IllegalStateException("Record with id = " + entity.id + " not found");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
