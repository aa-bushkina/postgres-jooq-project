package ru.vk;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.vk.DAO.DAOModule;
import ru.vk.application.Application;
import ru.vk.application.ApplicationModule;

public abstract class AbstractTest {
  @NotNull
  final String[] args = {"jdbc:postgresql://localhost/", "testTask", "postgres", "postgres"};

  @NotNull
  final String path = "db/test";

  Injector injector;

  @BeforeEach
  public void beforeEach() {
    injector = Guice.createInjector(new ApplicationModule(args), new DAOModule(args));
    injector.injectMembers(this);
    injector.getInstance(Application.class).makeDB(path);
  }

  @AfterEach
  public void afterEach() {
    injector.getInstance(Application.class).cleanDB();
  }
}
