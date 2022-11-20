package ru.vk.application;

import com.google.inject.Inject;
import generated.tables.records.OrganizationsRecord;
import generated.tables.records.ProductsRecord;
import org.jetbrains.annotations.NotNull;
import ru.vk.DAO.OrganizationDAO;
import ru.vk.DAO.ProductDAO;
import ru.vk.application.utils.ProductInfo;
import ru.vk.entities.Organization;
import ru.vk.entities.Product;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Application {
  @NotNull
  final private FlywayInitializer initializer;

  @NotNull
  final private OrganizationDAO organizationDAO;

  @NotNull
  final private ProductDAO productDAO;

  @Inject
  public Application(@NotNull FlywayInitializer initializer, @NotNull OrganizationDAO organizationDAO, @NotNull ProductDAO productDAO) {
    this.initializer = initializer;
    this.organizationDAO = organizationDAO;
    this.productDAO = productDAO;
  }

  public void makeDB(@NotNull final String path) {
    initializer.initDB(path);
  }

  public void cleanDB() {
    initializer.cleanDB();
  }

  public Map<OrganizationsRecord, BigDecimal> getTop10OrganizationsByQuantity(final int limit) {
    return organizationDAO.getTop10OrganizationsByQuantity(limit);
  }

  public Map<OrganizationsRecord, BigDecimal> getOrganizationsWithDefiniteQuantity(final int productId,
                                                                                   @NotNull final BigDecimal quantity) {
    return organizationDAO.getOrganizationsWithDefiniteQuantity(productId, quantity);
  }

  public Map<ProductsRecord, BigDecimal> getAverageOfProductPrice(@NotNull final String startDate,
                                                                  @NotNull final String endDate) {
    return productDAO.getAverageOfProductPrice(startDate, endDate);
  }

  public Map<Date, ArrayList<ProductInfo>> getEverydayProductCharacteristics(@NotNull final String startDate,
                                                                             @NotNull final String endDate) {
    return productDAO.getEverydayProductCharacteristics(startDate, endDate);
  }

  public Map<Organization, List<Product>> getProductsListByOrganizations() {
    return organizationDAO.getProductsListByOrganizations();
  }
}
