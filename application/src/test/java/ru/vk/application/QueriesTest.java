package ru.vk.application;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import ru.vk.AbstractTest;
import ru.vk.DAO.OrganizationDAO;
import ru.vk.DAO.ProductDAO;
import ru.vk.entities.Organization;
import ru.vk.entities.Product;

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
    //System.out.println(productDAO.getEverydayProductCharacteristics());
    //Create result map
    //assertThat(productDAO.getEverydayProductCharacteristics(), equalTo(map));
  }

  @Test
  void getAverageOfProductPrice() {
    final Map<Product, Double> map = new LinkedHashMap<>() {{
      put(new Product(1, "product1", "0000000001"), 1242.32);
      put(new Product(10, "product10", "1a00ddrs10"), 343.99);
      put(new Product(11, "product11", "2d00000032"), 3747.0);
      put(new Product(12, "product12", "1a05302436"), 2457.85);
      put(new Product(13, "product13", "1113400324"), 13345.99);
      put(new Product(14, "product14", "1113324524"), 456778.3);
      put(new Product(15, "product15", "1a00fw4500"), 34567.5);
      put(new Product(2, "product2", "0000000002"), 1314.96);
      put(new Product(3, "product3", "1000000001"), 24524.64);
      put(new Product(4, "product4", "1000000011"), 13345.85);
      put(new Product(5, "product5", "1a00000032"), 3435.4);
      put(new Product(6, "product6", "1a00000053"), 232343.0);
      put(new Product(7, "product7", "1a00dfg078"), 12343.5);
      put(new Product(8, "product8", "1a0eg00422"), 9875.5);

    }};
    assertThat(productDAO.getAverageOfProductPrice(), equalTo(map));
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