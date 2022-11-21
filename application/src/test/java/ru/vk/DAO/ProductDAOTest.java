package ru.vk.DAO;

import com.google.inject.Inject;
import generated.tables.records.ProductsRecord;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vk.AbstractTest;

import javax.inject.Named;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ProductDAOTest extends AbstractTest {
  @Inject
  @NotNull
  @Named("Product")
  private Dao productDAO;

  @Test
  @DisplayName("Получение товара из БД")
  void get() {
    final String uniqueInternalCode =
      String.valueOf((int) (Math.random() * 1000000000) + 1000000000);
    final ProductsRecord product =
      new ProductsRecord(0, "product", uniqueInternalCode);
    final int generatedId = productDAO.save(product);
    product.setId(generatedId);
    assertThat(productDAO.get(generatedId), equalTo(product));
    productDAO.delete(product);
  }

  @Test
  @DisplayName("Просмотр всех товаров в БД")
  void all() {
    List<ProductsRecord> list = List.of(
      new ProductsRecord(1, "0000000001", "product1"),
      new ProductsRecord(2, "0000000002", "product2"),
      new ProductsRecord(3, "1000000001", "product3"),
      new ProductsRecord(4, "1000000011", "product4"),
      new ProductsRecord(5, "1a00000032", "product5"),
      new ProductsRecord(6, "1a00000053", "product6"),
      new ProductsRecord(7, "1a00dfg078", "product7"),
      new ProductsRecord(8, "1a0eg00422", "product8"),
      new ProductsRecord(9, "1a004t0032", "product9"),
      new ProductsRecord(10, "1a00ddrs10", "product10"),
      new ProductsRecord(11, "2d00000032", "product11"),
      new ProductsRecord(12, "1a05302436", "product12"),
      new ProductsRecord(13, "1113400324", "product13"),
      new ProductsRecord(14, "1113324524", "product14"),
      new ProductsRecord(15, "1a00fw4500", "product15"));
    assertThat(productDAO.all(), equalTo(list));
  }

  @Test
  @DisplayName("Добавление нового товара в БД")
  void save() {
    final String uniqueInternalCode =
      String.valueOf((int) (Math.random() * 1000000000) + 1000000000);
    final ProductsRecord product =
      new ProductsRecord(0, "product", uniqueInternalCode);
    final int generatedId = productDAO.save(product);
    product.setId(generatedId);
    assertThat((List<ProductsRecord>) productDAO.all(), hasItem(product));
    productDAO.delete(product);
  }

  @Test
  @DisplayName("Обновление данных товара из БД")
  void update() {
    final String uniqueInternalCode =
      String.valueOf((int) (Math.random() * 999999999) + 1000000000);
    final ProductsRecord product =
      new ProductsRecord(0, uniqueInternalCode, "product");
    final int generatedId = productDAO.save(product);
    product.setId(generatedId);
    final ProductsRecord updatedProduct =
      new ProductsRecord(generatedId, uniqueInternalCode, "productUpdate");
    productDAO.update(updatedProduct);
    assertThat(productDAO.get(generatedId), equalTo(updatedProduct));
    productDAO.delete(product);
  }

  @Test
  @DisplayName("Удаление товара из БД")
  void delete() {
    final String uniqueInternalCode = String.valueOf((int) (Math.random() * 1000000000) + 1000000000);
    final ProductsRecord product = new ProductsRecord(0, "product", uniqueInternalCode);
    final int generatedId = productDAO.save(product);
    product.setId(generatedId);
    assertThat((List<ProductsRecord>) productDAO.all(), hasItem(product));
    productDAO.delete(product);
    assertThat((List<ProductsRecord>) productDAO.all(), not(hasItem(product)));
  }
}