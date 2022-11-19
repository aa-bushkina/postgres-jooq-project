package ru.vk.DAO;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vk.AbstractTest;
import ru.vk.entities.Organization;

import javax.inject.Named;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class OrganizationDAOTest extends AbstractTest {
  @Inject
  @NotNull
  @Named("Organization")
  private Dao organizationDAO;

  @Test
  @DisplayName("Получение организации из БД")
  void get() {
    final int uniqueId = (int) (Math.random() * 1000) + 20;
    final String uniqueInn = String.valueOf((int) (Math.random() * 1000000000) + 1000000000);
    final String uniquePaymentAccount = String.valueOf((int) (Math.random() * 1000000000) + 1000000000);
    final Organization organization = new Organization(uniqueId, "organization", uniqueInn, uniquePaymentAccount);
    organizationDAO.save(organization);
    assertThat(organizationDAO.get(uniqueId), equalTo(organization));
    organizationDAO.delete(organization);
  }

  @Test
  @DisplayName("Просмотр всех организаций в БД")
  void all() {
    List<Organization> list = List.of(
      new Organization(1, "organization1", "2330123011", "1234567890"),
      new Organization(2, "organization2", "2500000021", "2345678901"),
      new Organization(3, "organization3", "7300450045", "3456789012"),
      new Organization(4, "organization4", "2201450011", "4567890123"),
      new Organization(5, "organization5", "1050337331", "5678901234"),
      new Organization(6, "organization6", "1050343811", "4444444444"),
      new Organization(7, "organization7", "5444007811", "5555555554"),
      new Organization(8, "organization8", "1050007811", "1452667778"),
      new Organization(9, "organization9", "1052227811", "3749506837"),
      new Organization(10, "organization10", "1054407811", "3456384888"),
      new Organization(11, "organization11", "1050007661", "0000066666"),
      new Organization(12, "organization12", "3567007811", "3456237474"),
      new Organization(13, "organization13", "1052323356", "2345654334"),
      new Organization(14, "organization14", "1056003635", "5656434564"),
      new Organization(15, "organization15", "1023456811", "3434343434")
    );
    assertThat(organizationDAO.all(), equalTo(list));
  }

  @Test
  @DisplayName("Добавление новой организации в БД")
  void save() {
    final int uniqueId = (int) (Math.random() * 1000) + 20;
    final String uniqueInn = String.valueOf((int) (Math.random() * 1000000000) + 1000000000);
    final String uniquePaymentAccount = String.valueOf((int) (Math.random() * 1000000000) + 1000000000);
    final Organization organization = new Organization(uniqueId, "organization", uniqueInn, uniquePaymentAccount);
    organizationDAO.save(organization);
    assertThat((List<Organization>) organizationDAO.all(), hasItem(organization));
    organizationDAO.delete(organization);
  }

  @Test
  @DisplayName("Обновление данных организации из БД")
  void update() {
    final int uniqueId = (int) (Math.random() * 1000) + 20;
    final String uniqueInn = String.valueOf((int) (Math.random() * 1000000000) + 1000000000);
    final String uniquePaymentAccount = String.valueOf((int) (Math.random() * 1000000000) + 1000000000);
    final Organization organization = new Organization(uniqueId, "organization", uniqueInn, uniquePaymentAccount);
    organizationDAO.save(organization);
    final Organization updatedOrganization = new Organization(uniqueId, "organization", uniqueInn, uniquePaymentAccount);
    organizationDAO.update(updatedOrganization);
    assertThat(organizationDAO.get(uniqueId), equalTo(updatedOrganization));
    organizationDAO.delete(organization);
  }

  @Test
  @DisplayName("Удаление организации из БД")
  void delete() {
    final int uniqueId = (int) (Math.random() * 1000) + 20;
    final String uniqueInn = String.valueOf((int) (Math.random() * 1000000000) + 1000000000);
    final String uniquePaymentAccount = String.valueOf((int) (Math.random() * 1000000000) + 1000000000);
    final Organization organization = new Organization(uniqueId, "organization", uniqueInn, uniquePaymentAccount);
    organizationDAO.save(organization);
    assertThat((List<Organization>) organizationDAO.all(), hasItem(organization));
    organizationDAO.delete(organization);
    assertThat((List<Organization>) organizationDAO.all(), not(hasItem(organization)));
  }
}