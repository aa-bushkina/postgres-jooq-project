package ru.vk.application;

import org.flywaydb.core.Flyway;
import org.jetbrains.annotations.NotNull;
import ru.vk.application.utils.DBProperties;

public class FlywayInitializer {
  @NotNull
  final private DBProperties DBProperties;

  Flyway flyway;

  public FlywayInitializer(@NotNull final DBProperties dbProperties) {
    DBProperties = dbProperties;
  }

  public void initDB(@NotNull final String path) {
    flyway = Flyway
      .configure()
      .dataSource(DBProperties.connection() + DBProperties.name(),
        DBProperties.username(),
        DBProperties.password())
      .cleanDisabled(false)
      .locations(path)
      .load();
    flyway.migrate();
  }

  public void cleanDB() {
    flyway.clean();
  }

}
