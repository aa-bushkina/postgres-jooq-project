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

class ProductDAOTest extends AbstractTest {
  @Inject
  @NotNull
  @Named("Product")
  private Dao productDAO;

  @Test
  @DisplayName("Получение товара из БД")
  void get() {
    final int uniqueId = (int) (Math.random() * 1000) + 20;
    final String uniqueInternalCode =
      String.valueOf((int) (Math.random() * 1000000000) + 1000000000);
    final ProductsRecord product =
      new ProductsRecord(uniqueId, "product", uniqueInternalCode);
    productDAO.save(product);
    assertThat(productDAO.get(uniqueId), equalTo(product));
    productDAO.delete(product);
  }

  @Test
  @DisplayName("Просмотр всех товаров в БД")
  void all() {
    List<ProductsRecord> list = List.of(
      new ProductsRecord(1, "product1", "0000000001"),
      new ProductsRecord(2, "product2", "0000000002"),
      new ProductsRecord(3, "product3", "1000000001"),
      new ProductsRecord(4, "product4", "1000000011"),
      new ProductsRecord(5, "product5", "1a00000032"),
      new ProductsRecord(6, "product6", "1a00000053"),
      new ProductsRecord(7, "product7", "1a00dfg078"),
      new ProductsRecord(8, "product8", "1a0eg00422"),
      new ProductsRecord(9, "product9", "1a004t0032"),
      new ProductsRecord(10, "product10", "1a00ddrs10"),
      new ProductsRecord(11, "product11", "2d00000032"),
      new ProductsRecord(12, "product12", "1a05302436"),
      new ProductsRecord(13, "product13", "1113400324"),
      new ProductsRecord(14, "product14", "1113324524"),
      new ProductsRecord(15, "product15", "1a00fw4500"));
    assertThat(productDAO.all(), equalTo(list));
  }

  @Test
  @DisplayName("Добавление нового товара в БД")
  void save() {
    final int uniqueId = (int) (Math.random() * 1000) + 20;
    final String uniqueInternalCode =
      String.valueOf((int) (Math.random() * 1000000000) + 1000000000);
    final ProductsRecord product =
      new ProductsRecord(uniqueId, "product", uniqueInternalCode);
    productDAO.save(product);
    assertThat((List<ProductsRecord>) productDAO.all(), hasItem(product));
    productDAO.delete(product);
  }

  @Test
  @DisplayName("Обновление данных товара из БД")
  void update() {
    final int uniqueId = (int) (Math.random() * 1000) + 20;
    final String uniqueInternalCode =
      String.valueOf((int) (Math.random() * 1000000000) + 1000000000);
    final ProductsRecord product =
      new ProductsRecord(uniqueId, "product", uniqueInternalCode);
    productDAO.save(product);
    final ProductsRecord updatedProduct =
      new ProductsRecord(uniqueId, "productUpdate", uniqueInternalCode);
    productDAO.update(updatedProduct);
    assertThat(productDAO.get(uniqueId), equalTo(updatedProduct));
    productDAO.delete(product);
  }

  @Test
  @DisplayName("Удаление товара из БД")
  void delete() {
    final int uniqueId = (int) (Math.random() * 1000) + 20;
    final String uniqueInternalCode = String.valueOf((int) (Math.random() * 1000000000) + 1000000000);
    final ProductsRecord product = new ProductsRecord(uniqueId, "product", uniqueInternalCode);
    productDAO.save(product);
    assertThat((List<ProductsRecord>) productDAO.all(), hasItem(product));
    productDAO.delete(product);
    assertThat((List<ProductsRecord>) productDAO.all(), not(hasItem(product)));
  }
}