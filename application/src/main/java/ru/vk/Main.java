package ru.vk;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jetbrains.annotations.NotNull;
import ru.vk.application.Application;
import ru.vk.application.ApplicationModule;

public class Main
{
  public static void main(@NotNull String[] args)
  {
    final Injector injector = Guice.createInjector(new ApplicationModule(args));
    final var applicationInstance = injector.getInstance(Application.class);
    applicationInstance.makeDB("db");

    System.out.println("Выбрать первые 10 поставщиков по количеству поставленного товара\n");
    System.out.println(applicationInstance.getTop10OrganizationsByQuantity());

    System.out.println("\nВыбрать поставщиков с количеством поставленного товара " +
      "выше указанного значения: 9000\n");
    System.out.println(applicationInstance.getOrganizationsWithDefiniteQuantity());

    System.out.println("\nЗа каждый день для каждого товара рассчитать количество и " +
      "сумму полученного товара в указанном периоде\n");
    System.out.println(applicationInstance.getEverydayProductCharacteristics());

    System.out.println("\nРассчитать среднюю цену полученного товара за период\n");
    System.out.println(applicationInstance.getAverageOfProductPrice());

    System.out.println("\nВывести список товаров, поставленных организациями за период\n");
    System.out.println(applicationInstance.getProductsListByOrganizations());
  }
}
