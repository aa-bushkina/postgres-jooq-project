package ru.vk.application;

import com.google.inject.Inject;
import generated.tables.records.ProductsRecord;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import ru.vk.AbstractTest;
import ru.vk.DAO.OrganizationDAO;
import ru.vk.DAO.ProductDAO;
import ru.vk.application.utils.ProductInfo;
import ru.vk.entities.Organization;
import ru.vk.entities.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class QueriesTest extends AbstractTest {
  @Inject
  @NotNull
  private ProductDAO productDAO;

  @Inject
  @NotNull
  private OrganizationDAO organizationDAO;

  @Test
  void getTop10OrganizationsByQuantity() {
    final Map<Organization, Integer> map = new LinkedHashMap<>() {{
      put(new Organization(15, "organization15", "1023456811", "3434343434"), 356300);
      put(new Organization(3, "organization3", "7300450045", "3456789012"), 354800);
      put(new Organization(2, "organization2", "2500000021", "2345678901"), 349800);
      put(new Organization(10, "organization10", "1054407811", "3456384888"), 108774);
      put(new Organization(12, "organization12", "3567007811", "3456237474"), 103975);
      put(new Organization(6, "organization6", "1050343811", "4444444444"), 101333);
      put(new Organization(9, "organization9", "1052227811", "3749506837"), 10754);
      put(new Organization(1, "organization1", "2330123011", "1234567890"), 9000);
      put(new Organization(8, "organization8", "1050007811", "1452667778"), 7400);
      put(new Organization(5, "organization5", "1050337331", "5678901234"), 7000);
    }};

    assertThat(organizationDAO.getTop10OrganizationsByQuantity().size(), equalTo(10));
    assertThat(organizationDAO.getTop10OrganizationsByQuantity(), equalTo(map));
  }

  @Test
  @Deprecated
  void getOrganizationsWithDefiniteQuantity() {
    final Map<Organization, Integer> map = new LinkedHashMap<>() {{
      put(new Organization(6, "organization6", "1050343811", "4444444444"), 100000);
      put(new Organization(10, "organization10", "1054407811", "3456384888"), 100000);
      put(new Organization(12, "organization12", "3567007811", "3456237474"), 100000);
    }};
    assertThat(organizationDAO.getOrganizationsWithDefiniteQuantity(), equalTo(map));
  }

  @Test
  @Deprecated
  void getEverydayProductCharacteristics() {
    System.out.println(productDAO.getEverydayProductCharacteristics());
    final ArrayList<ProductInfo> list1 = new ArrayList<>() {{
      add(new ProductInfo(
        new Product(1, "product1", "0000000001"), 0, null));
      add(new ProductInfo(
        new Product(2, "product2", "0000000002"), 0, null));
      add(new ProductInfo(
        new Product(3, "product3", "1000000001"), 0, null));
      add(new ProductInfo(
        new Product(4, "product4", "1000000011"), 1000,
        BigDecimal.valueOf(13345850.00).setScale(2, RoundingMode.CEILING)));
      add(new ProductInfo(
        new Product(5, "product5", "1a00000032"), 348800,
        BigDecimal.valueOf(1198267520.00).setScale(2, RoundingMode.CEILING)));
      add(new ProductInfo(
        new Product(6, "product6", "1a00000053"), 0, null));
      add(new ProductInfo(
        new Product(7, "product7", "1a00dfg078"), 0, null));
      add(new ProductInfo(
        new Product(8, "product8", "1a0eg00422"), 0, null));
      add(new ProductInfo(
        new Product(9, "product9", "1a004t0032"), 0, null));
      add(new ProductInfo(
        new Product(10, "product10", "1a00ddrs10"), 0, null));
      add(new ProductInfo(
        new Product(11, "product11", "2d00000032"), 0, null));
      add(new ProductInfo(
        new Product(12, "product12", "1a05302436"), 555,
        BigDecimal.valueOf(1364106.75).setScale(2, RoundingMode.CEILING)));
      add(new ProductInfo(
        new Product(13, "product13", "1113400324"), 3400,
        BigDecimal.valueOf(45376366.00).setScale(2, RoundingMode.CEILING)));
      add(new ProductInfo(
        new Product(14, "product14", "1113324524"), 20,
        BigDecimal.valueOf(9135566.00).setScale(2, RoundingMode.CEILING)));
      add(new ProductInfo(
        new Product(15, "product15", "1a00fw4500"), 100000,
        BigDecimal.valueOf(3456750000.00).setScale(2, RoundingMode.CEILING)));
    }};

    final ArrayList<ProductInfo> list2 = new ArrayList<>() {{
      add(new ProductInfo(
        new Product(1, "product1", "0000000001"), 0, null));
      add(new ProductInfo(
        new Product(2, "product2", "0000000002"), 0, null));
      add(new ProductInfo(
        new Product(3, "product3", "1000000001"), 0, null));
      add(new ProductInfo(
        new Product(4, "product4", "1000000011"), 0, null));
      add(new ProductInfo(
        new Product(5, "product5", "1a00000032"), 0, null));
      add(new ProductInfo(
        new Product(6, "product6", "1a00000053"), 0, null));
      add(new ProductInfo(
        new Product(7, "product7", "1a00dfg078"), 2457,
        BigDecimal.valueOf(30327979.50).setScale(2, RoundingMode.CEILING)));
      add(new ProductInfo(
        new Product(8, "product8", "1a0eg00422"), 0, null));
      add(new ProductInfo(
        new Product(9, "product9", "1a004t0032"), 0, null));
      add(new ProductInfo(
        new Product(10, "product10", "1a00ddrs10"), 0, null));
      add(new ProductInfo(
        new Product(11, "product11", "2d00000032"), 0, null));
      add(new ProductInfo(
        new Product(12, "product12", "1a05302436"), 0, null));
      add(new ProductInfo(
        new Product(13, "product13", "1113400324"), 0, null));
      add(new ProductInfo(
        new Product(14, "product14", "1113324524"), 0, null));
      add(new ProductInfo(
        new Product(15, "product15", "1a00fw4500"), 0, null));
    }};
    final LinkedHashMap<Date, ArrayList<ProductInfo>> map = new LinkedHashMap<>() {{
      put(Date.valueOf("2022-11-03"), list1);
      put(Date.valueOf("2022-11-04"), list2);
    }};
    System.out.println(productDAO.getEverydayProductCharacteristics());
    assertThat(productDAO.getEverydayProductCharacteristics(), equalTo(map));
  }

  public BigDecimal makeBigDecimal(final double x) {
    return BigDecimal.valueOf(x).setScale(2, RoundingMode.CEILING);
  }

  @Test
  void getAverageOfProductPrice() {
    final Map<ProductsRecord, BigDecimal> map = new LinkedHashMap<>() {{
      put(new ProductsRecord(1, "product1", "0000000001"), makeBigDecimal(1242.32));
      put(new ProductsRecord(10, "product10", "1a00ddrs10"), makeBigDecimal(343.99));
      put(new ProductsRecord(11, "product11", "2d00000032"), makeBigDecimal(3747.00));
      put(new ProductsRecord(12, "product12", "1a05302436"), makeBigDecimal(2457.85));
      put(new ProductsRecord(13, "product13", "1113400324"), makeBigDecimal(13345.99));
      put(new ProductsRecord(14, "product14", "1113324524"), makeBigDecimal(456778.30));
      put(new ProductsRecord(15, "product15", "1a00fw4500"), makeBigDecimal(34567.50));
      put(new ProductsRecord(2, "product2", "0000000002"), makeBigDecimal(1314.96));
      put(new ProductsRecord(3, "product3", "1000000001"), makeBigDecimal(24524.64));
      put(new ProductsRecord(4, "product4", "1000000011"), makeBigDecimal(13345.85));
      put(new ProductsRecord(5, "product5", "1a00000032"), makeBigDecimal(3435.40));
      put(new ProductsRecord(6, "product6", "1a00000053"), makeBigDecimal(232343.00));
      put(new ProductsRecord(7, "product7", "1a00dfg078"), makeBigDecimal(12343.50));
      put(new ProductsRecord(8, "product8", "1a0eg00422"), makeBigDecimal(9875.50));

    }};
    final String startDate = "2022-11-01";
    final String endDate = "2022-11-06";
    assertThat(productDAO.getAverageOfProductPrice(startDate, endDate), equalTo(map));
  }

  @Test
  void getProductsListByOrganizations() {
    Map<Organization, List<Product>> map = new LinkedHashMap<>() {{
      put(new Organization(3, "organization3", "7300450045", "3456789012"),
        List.of(new Product(5, "product5", "1a00000032"),
          new Product(3, "product3", "1000000001"),
          new Product(1, "product1", "0000000001")));
      put(new Organization(6, "organization6", "1050343811", "4444444444"),
        List.of(new Product(4, "product4", "1000000011"),
          new Product(9, "product9", "1a004t0032"),
          new Product(15, "product15", "1a00fw4500")));
    }};
    assertThat(organizationDAO.getProductsListByOrganizations(), equalTo(map));
  }
}