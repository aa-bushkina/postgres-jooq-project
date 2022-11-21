package ru.vk.DAO;

import com.google.inject.Inject;
import generated.tables.records.OrganizationsRecord;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vk.AbstractTest;

import javax.inject.Named;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class OrganizationDAOTest extends AbstractTest {
  @Inject
  @NotNull
  @Named("Organization")
  private Dao organizationDAO;

  @Test
  @DisplayName("Получение организации из БД")
  void get() {
    final String uniqueInn = String.valueOf((int) (Math.random() * 999999999) + 1000000000);
    final String uniquePaymentAccount = String.valueOf((int) (Math.random() * 999999999) + 1000000000);
    final OrganizationsRecord organization = new OrganizationsRecord(0, "organization", uniqueInn, uniquePaymentAccount);
    final int generatedId = organizationDAO.save(organization);
    organization.setId(generatedId);
    assertThat(organizationDAO.get(generatedId), equalTo(organization));
    organizationDAO.delete(organization);
  }

  @Test
  @DisplayName("Просмотр всех организаций в БД")
  void all() {
    List<OrganizationsRecord> list = List.of(
      new OrganizationsRecord(1, "organization1", "2330123011", "1234567890"),
      new OrganizationsRecord(2, "organization2", "2500000021", "2345678901"),
      new OrganizationsRecord(3, "organization3", "7300450045", "3456789012"),
      new OrganizationsRecord(4, "organization4", "2201450011", "4567890123"),
      new OrganizationsRecord(5, "organization5", "1050337331", "5678901234"),
      new OrganizationsRecord(6, "organization6", "1050343811", "4444444444"),
      new OrganizationsRecord(7, "organization7", "5444007811", "5555555554"),
      new OrganizationsRecord(8, "organization8", "1050007811", "1452667778"),
      new OrganizationsRecord(9, "organization9", "1052227811", "3749506837"),
      new OrganizationsRecord(10, "organization10", "1054407811", "3456384888"),
      new OrganizationsRecord(11, "organization11", "1050007661", "0000066666"),
      new OrganizationsRecord(12, "organization12", "3567007811", "3456237474"),
      new OrganizationsRecord(13, "organization13", "1052323356", "2345654334"),
      new OrganizationsRecord(14, "organization14", "1056003635", "5656434564"),
      new OrganizationsRecord(15, "organization15", "1023456811", "3434343434")
    );
    assertThat(organizationDAO.all(), equalTo(list));
  }

  @Test
  @DisplayName("Добавление новой организации в БД")
  void save() {
    final String uniqueInn = String.valueOf((int) (Math.random() * 999999999) + 1000000000);
    final String uniquePaymentAccount = String.valueOf((int) (Math.random() * 999999999) + 1000000000);
    final OrganizationsRecord organization = new OrganizationsRecord(0, "organization", uniqueInn, uniquePaymentAccount);
    final int generatedId = organizationDAO.save(organization);
    organization.setId(generatedId);
    assertThat((List<OrganizationsRecord>) organizationDAO.all(), hasItem(organization));
    organizationDAO.delete(organization);
  }

  @Test
  @DisplayName("Обновление данных организации из БД")
  void update() {
    final String uniqueInn = String.valueOf((int) (Math.random() * 999999999) + 1000000000);
    final String uniquePaymentAccount = String.valueOf((int) (Math.random() * 999999999) + 1000000000);
    final OrganizationsRecord organization = new OrganizationsRecord(0, "organization", uniqueInn, uniquePaymentAccount);
    final int generatedId = organizationDAO.save(organization);
    organization.setId(generatedId);
    final OrganizationsRecord updatedOrganization = new OrganizationsRecord(generatedId, "organization", uniqueInn, uniquePaymentAccount);
    organizationDAO.update(updatedOrganization);
    assertThat(organizationDAO.get(generatedId), equalTo(updatedOrganization));
    organizationDAO.delete(organization);
  }

  @Test
  @DisplayName("Удаление организации из БД")
  void delete() {
    final String uniqueInn = String.valueOf((int) (Math.random() * 999999999) + 1000000000);
    final String uniquePaymentAccount = String.valueOf((int) (Math.random() * 1000000000) + 1000000000);
    final OrganizationsRecord organization = new OrganizationsRecord(0, "organization", uniqueInn, uniquePaymentAccount);
    final int generatedId = organizationDAO.save(organization);
    organization.setId(generatedId);
    assertThat((List<OrganizationsRecord>) organizationDAO.all(), hasItem(organization));
    organizationDAO.delete(organization);
    assertThat((List<OrganizationsRecord>) organizationDAO.all(), not(hasItem(organization)));
  }
}