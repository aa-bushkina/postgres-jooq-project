package ru.vk;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jetbrains.annotations.NotNull;
import ru.vk.application.Application;
import ru.vk.application.ApplicationModule;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {
  public static void main(@NotNull String[] args) {
    final Injector injector = Guice.createInjector(new ApplicationModule(args));
    final var applicationInstance = injector.getInstance(Application.class);
    applicationInstance.makeDB("db");

    final int limit = 10;
    System.out.println("Выбрать первые 10 поставщиков по количеству поставленного товара\n");
    System.out.println(applicationInstance.getTop10OrganizationsByQuantity(limit));

    final int productId = 15;
    final BigDecimal quantity = BigDecimal.valueOf(1000).setScale(2, RoundingMode.CEILING);
    System.out.println("\nВыбрать поставщиков с количеством поставленного товара " +
      "выше указанного значения: 9000\n");
    System.out.println(applicationInstance.getOrganizationsWithDefiniteQuantity(productId, quantity));

    String startDate = "2022-11-03";
    String endDate = "2022-11-04";
    System.out.println("\nЗа каждый день для каждого товара рассчитать количество и " +
      "сумму полученного товара в указанном периоде\n");
    System.out.println(applicationInstance.getEverydayProductCharacteristics(startDate, endDate));

    startDate = "2022-11-01";
    endDate = "2022-11-06";
    System.out.println("\nРассчитать среднюю цену полученного товара за период\n");
    System.out.println(applicationInstance.getAverageOfProductPrice(startDate, endDate));

    startDate = "2022-11-05";
    endDate = "2022-11-09";
    System.out.println("\nВывести список товаров, поставленных организациями за период\n");
    System.out.println(applicationInstance.getProductsListByOrganizations(startDate, endDate));
  }
}
