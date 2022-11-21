package ru.vk.DAO;

import com.google.inject.Inject;
import generated.tables.records.InvoicesRecord;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vk.AbstractTest;

import javax.inject.Named;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class InvoiceDAOTest extends AbstractTest {
  @Inject
  @NotNull
  @Named("Invoice")
  private Dao invoiceDAO;

  @Test
  @DisplayName("Получение накладной из БД")
  void get() {
    final String uniqueNum = String.valueOf((int) (Math.random() * 999999999) + 1000000000);
    final InvoicesRecord invoiceRecord =
      new InvoicesRecord(0, uniqueNum, LocalDate.parse("2022-11-16"), 1);
    final int generatedId = invoiceDAO.save(invoiceRecord);
    invoiceRecord.setId(generatedId);
    assertThat(invoiceDAO.get(generatedId), equalTo(invoiceRecord));
    invoiceDAO.delete(invoiceRecord);
  }

  @Test
  @DisplayName("Просмотр всех накладных в БД")
  void all() {
    List<InvoicesRecord> list = List.of(
      new InvoicesRecord(1, "1111111111", LocalDate.parse("2022-11-01"), 1),
      new InvoicesRecord(2, "2222222222", LocalDate.parse("2022-11-03"), 2),
      new InvoicesRecord(3, "3333333333", LocalDate.parse("2022-11-09"), 3),
      new InvoicesRecord(4, "4444444444", LocalDate.parse("2022-11-02"), 4),
      new InvoicesRecord(5, "5555555555", LocalDate.parse("2022-10-07"), 5),
      new InvoicesRecord(6, "6666666666", LocalDate.parse("2022-11-08"), 6),
      new InvoicesRecord(7, "7777777777", LocalDate.parse("2022-11-04"), 7),
      new InvoicesRecord(8, "8888888888", LocalDate.parse("2022-10-30"), 8),
      new InvoicesRecord(9, "9999999999", LocalDate.parse("2022-10-03"), 9),
      new InvoicesRecord(10, "0000000000", LocalDate.parse("2022-11-02"), 10),
      new InvoicesRecord(11, "1212121212", LocalDate.parse("2022-10-23"), 11),
      new InvoicesRecord(12, "1313131313", LocalDate.parse("2022-11-03"), 12),
      new InvoicesRecord(13, "1414141414", LocalDate.parse("2022-11-01"), 13),
      new InvoicesRecord(14, "1535354564", LocalDate.parse("2022-10-22"), 14),
      new InvoicesRecord(15, "4675686345", LocalDate.parse("2022-11-02"), 15)
    );
    assertThat(invoiceDAO.all(), equalTo(list));
  }

  @Test
  @DisplayName("Добавление новой накладной в БД")
  void save() {
    final String uniqueNum = String.valueOf((int) (Math.random() * 999999999) + 1000000000);
    final InvoicesRecord invoicesRecord =
      new InvoicesRecord(0, uniqueNum, LocalDate.parse("2022-11-16"), 1);
    final int generatedId = invoiceDAO.save(invoicesRecord);
    invoicesRecord.setId(generatedId);
    assertThat((List<InvoicesRecord>) invoiceDAO.all(), hasItem(invoicesRecord));
    invoiceDAO.delete(invoicesRecord);
  }

  @Test
  @DisplayName("Обновление данных накладной из БД")
  void update() {
    final String uniqueNum = String.valueOf((int) (Math.random() * 999999999) + 1000000000);
    final InvoicesRecord invoicesRecord =
      new InvoicesRecord(0, uniqueNum, LocalDate.parse("2022-11-16"), 1);
    final int generatedId = invoiceDAO.save(invoicesRecord);
    invoicesRecord.setId(generatedId);
    final InvoicesRecord updatedInvoicesRecord =
      new InvoicesRecord(generatedId, uniqueNum, LocalDate.parse("2022-11-16"), 1);
    invoiceDAO.update(updatedInvoicesRecord);
    assertThat(invoiceDAO.get(generatedId), equalTo(updatedInvoicesRecord));
    invoiceDAO.delete(invoicesRecord);
  }

  @Test
  @DisplayName("Удаление накладной из БД")
  void delete() {
    final String uniqueNum = String.valueOf((int) (Math.random() * 999999999) + 1000000000);
    final InvoicesRecord invoicesRecord = new InvoicesRecord(0, uniqueNum, LocalDate.parse("2022-11-16"), 1);
    final int generatedId = invoiceDAO.save(invoicesRecord);
    invoicesRecord.setId(generatedId);
    assertThat((List<InvoicesRecord>) invoiceDAO.all(), hasItem(invoicesRecord));
    invoiceDAO.delete(invoicesRecord);
    assertThat((List<InvoicesRecord>) invoiceDAO.all(), not(hasItem(invoicesRecord)));
  }
}