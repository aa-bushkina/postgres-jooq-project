package ru.vk.DAO;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.jetbrains.annotations.NotNull;
import ru.vk.application.utils.DBProperties;

public class DAOModule extends AbstractModule {
  @NotNull
  final String[] args;

  public DAOModule(@NotNull final String[] args) {
    if (!checkArgs(args)) {
      throw new RuntimeException("Incorrect args.");
    }
    this.args = args;
  }

  @Override
  protected void configure() {
    DBProperties properties = new DBProperties(args[0], args[1], args[2], args[3]);
    bind(Dao.class).annotatedWith(Names.named("Invoice")).toInstance(new InvoiceDAO(properties));
    bind(Dao.class).annotatedWith(Names.named("Organization")).toInstance(new OrganizationDAO(properties));
    bind(Dao.class).annotatedWith(Names.named("Position")).toInstance(new PositionDAO(properties));
    bind(Dao.class).annotatedWith(Names.named("Product")).toInstance(new ProductDAO(properties));
  }

  private boolean checkArgs(@NotNull final String[] args) {
    return args.length == 4;
  }
}
