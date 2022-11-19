package ru.vk.DAO;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vk.AbstractTest;
import ru.vk.entities.Invoice;

import javax.inject.Named;
import java.sql.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class InvoiceDAOTest extends AbstractTest {
  @Inject
  @NotNull
  @Named("Invoice")
  private Dao invoiceDAO;

  @Test
  @DisplayName("Получение накладной из БД")
  void get() {
    final int uniqueId = (int) (Math.random() * 1000) + 20;
    final String uniqueNum = String.valueOf((int) (Math.random() * 1000000000) + 1000000000);
    final Invoice invoice = new Invoice(uniqueId, uniqueNum, Date.valueOf("2022-11-16"), 1);
    invoiceDAO.save(invoice);
    assertThat(invoiceDAO.get(uniqueId), equalTo(invoice));
    invoiceDAO.delete(invoice);
  }

  @Test
  @DisplayName("Просмотр всех накладных в БД")
  void all() {
    List<Invoice> list = List.of(
      new Invoice(1, "1111111111", Date.valueOf("2022-11-01"), 1),
      new Invoice(2, "2222222222", Date.valueOf("2022-11-03"), 2),
      new Invoice(3, "3333333333", Date.valueOf("2022-11-09"), 3),
      new Invoice(4, "4444444444", Date.valueOf("2022-11-02"), 4),
      new Invoice(5, "5555555555", Date.valueOf("2022-10-07"), 5),
      new Invoice(6, "6666666666", Date.valueOf("2022-11-08"), 6),
      new Invoice(7, "7777777777", Date.valueOf("2022-11-04"), 7),
      new Invoice(8, "8888888888", Date.valueOf("2022-10-30"), 8),
      new Invoice(9, "9999999999", Date.valueOf("2022-10-03"), 9),
      new Invoice(10, "0000000000", Date.valueOf("2022-11-02"), 10),
      new Invoice(11, "1212121212", Date.valueOf("2022-10-23"), 11),
      new Invoice(12, "1313131313", Date.valueOf("2022-11-03"), 12),
      new Invoice(13, "1414141414", Date.valueOf("2022-11-01"), 13),
      new Invoice(14, "1535354564", Date.valueOf("2022-10-22"), 14),
      new Invoice(15, "4675686345", Date.valueOf("2022-11-02"), 15)
    );
    assertThat(invoiceDAO.all(), equalTo(list));
  }

  @Test
  @DisplayName("Добавление новой накладной в БД")
  void save() {
    final int uniqueId = (int) (Math.random() * 1000) + 20;
    final String uniqueNum = String.valueOf((int) (Math.random() * 1000000000) + 1000000000);
    final Invoice invoice = new Invoice(uniqueId, uniqueNum, Date.valueOf("2022-11-16"), 1);
    invoiceDAO.save(invoice);
    assertThat((List<Invoice>) invoiceDAO.all(), hasItem(invoice));
    invoiceDAO.delete(invoice);
  }

  @Test
  @DisplayName("Обновление данных накладной из БД")
  void update() {
    final int uniqueId = (int) (Math.random() * 1000) + 20;
    final String uniqueNum = String.valueOf((int) (Math.random() * 1000000000) + 1000000000);
    final Invoice invoice = new Invoice(uniqueId, uniqueNum, Date.valueOf("2022-11-16"), 1);
    invoiceDAO.save(invoice);
    final Invoice updatedInvoice = new Invoice(uniqueId, uniqueNum, Date.valueOf("2022-11-16"), 1);
    invoiceDAO.update(updatedInvoice);
    assertThat(invoiceDAO.get(uniqueId), equalTo(updatedInvoice));
    invoiceDAO.delete(invoice);
  }

  @Test
  @DisplayName("Удаление накладной из БД")
  void delete() {
    final int uniqueId = (int) (Math.random() * 1000) + 20;
    final String uniqueNum = String.valueOf((int) (Math.random() * 1000000000) + 1000000000);
    final Invoice invoice = new Invoice(uniqueId, uniqueNum, Date.valueOf("2022-11-16"), 1);
    invoiceDAO.save(invoice);
    assertThat((List<Invoice>) invoiceDAO.all(), hasItem(invoice));
    invoiceDAO.delete(invoice);
    assertThat((List<Invoice>) invoiceDAO.all(), not(hasItem(invoice)));
  }
}