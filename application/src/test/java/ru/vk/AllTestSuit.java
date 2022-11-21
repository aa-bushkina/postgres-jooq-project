package ru.vk;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;
import ru.vk.DAO.InvoiceDAOTest;
import ru.vk.DAO.OrganizationDAOTest;
import ru.vk.DAO.PositionDAOTest;
import ru.vk.DAO.ProductDAOTest;
import ru.vk.application.QueriesTest;

@SelectClasses({
  QueriesTest.class,
  InvoiceDAOTest.class,
  OrganizationDAOTest.class,
  PositionDAOTest.class,
  ProductDAOTest.class})
@Suite
@SuiteDisplayName("All Tests")
public class AllTestSuit {

}