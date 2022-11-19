package ru.vk.application;

import com.google.inject.AbstractModule;
import org.jetbrains.annotations.NotNull;
import ru.vk.DAO.OrganizationDAO;
import ru.vk.DAO.ProductDAO;
import ru.vk.application.utils.DBProperties;

public class ApplicationModule extends AbstractModule {
  @NotNull
  final String[] args;

  public ApplicationModule(@NotNull final String[] args) {
    if (!checkArgs(args)) {
      throw new RuntimeException("Incorrect args.");
    }
    this.args = args;
  }

  @Override
  protected void configure() {
    DBProperties properties = new DBProperties(args[0], args[1], args[2], args[3]);
    bind(DBProperties.class).toInstance(properties);
    bind(FlywayInitializer.class).toInstance(new FlywayInitializer(properties));
    bind(ProductDAO.class).toInstance(new ProductDAO(properties));
    bind(OrganizationDAO.class).toInstance(new OrganizationDAO(properties));
  }

  private boolean checkArgs(@NotNull final String[] args) {
    return (args.length == 4) && args[0].contains("jdbc:postgresql://");
  }
}
