package ru.vk.DAO;

import com.google.inject.Inject;
import generated.tables.records.PositionsRecord;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vk.AbstractTest;

import javax.inject.Named;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PositionDAOTest extends AbstractTest {
  @Inject
  @NotNull
  @Named("Position")
  private Dao positionDAO;

  @Test
  @DisplayName("Получение позиции накладной из БД")
  void get() {
    final int uniqueId = (int) (Math.random() * 1000) + 20;
    final PositionsRecord position =
      new PositionsRecord(uniqueId, BigDecimal.valueOf(23944.55), 4, 390);
    positionDAO.save(position);
    assertThat(positionDAO.get(uniqueId), equalTo(position));
    positionDAO.delete(position);
  }

  @Test
  @DisplayName("Просмотр всех позиции накладных в БД")
  void all() {
    List<PositionsRecord> list = List.of(
      new PositionsRecord(1, BigDecimal.valueOf(1242.32).setScale(2, RoundingMode.CEILING),
        1, 2000),
      new PositionsRecord(2, BigDecimal.valueOf(1314.96).setScale(2,
        RoundingMode.CEILING), 2, 3000),
      new PositionsRecord(3, BigDecimal.valueOf(24524.64).setScale(2,
        RoundingMode.CEILING), 3, 4000),
      new PositionsRecord(4, BigDecimal.valueOf(13345.85).setScale(2,
        RoundingMode.CEILING), 4, 1000),
      new PositionsRecord(5, BigDecimal.valueOf(3435.40).setScale(2,
        RoundingMode.CEILING), 5, 348800),
      new PositionsRecord(6, BigDecimal.valueOf(232343.00).setScale(2,
        RoundingMode.CEILING), 6, 895),
      new PositionsRecord(7, BigDecimal.valueOf(12343.50).setScale(2,
        RoundingMode.CEILING), 7, 2457),
      new PositionsRecord(8, BigDecimal.valueOf(9875.50).setScale(2,
        RoundingMode.CEILING), 8, 3859),
      new PositionsRecord(9, BigDecimal.valueOf(876.00).setScale(2,
        RoundingMode.CEILING), 9, 333),
      new PositionsRecord(10, BigDecimal.valueOf(343.99).setScale(2,
        RoundingMode.CEILING), 10, 3500),
      new PositionsRecord(11, BigDecimal.valueOf(3747.00).setScale(2,
        RoundingMode.CEILING), 11, 4000),
      new PositionsRecord(12, BigDecimal.valueOf(2457.85).setScale(2,
        RoundingMode.CEILING), 12, 555),
      new PositionsRecord(13, BigDecimal.valueOf(13345.99).setScale(2,
        RoundingMode.CEILING), 13, 3400),
      new PositionsRecord(14, BigDecimal.valueOf(456778.30).setScale(2,
        RoundingMode.CEILING), 14, 20),
      new PositionsRecord(15, BigDecimal.valueOf(34567.50).setScale(2,
        RoundingMode.CEILING), 15, 100000)
    );
    assertThat(positionDAO.all(), equalTo(list));
  }

  @Test
  @DisplayName("Добавление новой позиции накладной в БД")
  void save() {
    final int uniqueId = (int) (Math.random() * 1000) + 20;
    final PositionsRecord position =
      new PositionsRecord(uniqueId, BigDecimal.valueOf(23944.55), 4, 390);
    positionDAO.save(position);
    assertThat((List<PositionsRecord>) positionDAO.all(), hasItem(position));
    positionDAO.delete(position);
  }

  @Test
  @DisplayName("Обновление данных позиции накладной из БД")
  void update() {
    final int uniqueId = (int) (Math.random() * 1000) + 20;
    final PositionsRecord position =
      new PositionsRecord(uniqueId, BigDecimal.valueOf(23944.55), 4, 390);
    positionDAO.save(position);
    final PositionsRecord updatedPosition =
      new PositionsRecord(uniqueId, BigDecimal.valueOf(23944.55), 4, 2000);
    positionDAO.update(updatedPosition);
    assertThat(positionDAO.get(uniqueId), equalTo(updatedPosition));
    positionDAO.delete(position);
  }

  @Test
  @DisplayName("Удаление позиции накладной из БД")
  void delete() {
    final int uniqueId = (int) (Math.random() * 1000) + 20;
    final PositionsRecord position =
      new PositionsRecord(uniqueId, BigDecimal.valueOf(239544.55), 5, 3970);
    positionDAO.save(position);
    assertThat((List<PositionsRecord>) positionDAO.all(), hasItem(position));
    positionDAO.delete(position);
    assertThat((List<PositionsRecord>) positionDAO.all(), not(hasItem(position)));
  }
}