package by.dorogokupets.walletservice.util;

import by.dorogokupets.walletservice.repository.impl.ClientRepositoryImpl;
import domain.entity.DBProperties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DbConfig {
  public static DBProperties dbProperties;

  static {
    Properties prop;
    try (InputStream input = ClientRepositoryImpl.class.getClassLoader().getResourceAsStream("database.properties")) {
      prop = new Properties();
      prop.load(input);
    } catch (IOException e) {
      throw new ExceptionInInitializerError(e);
    }
    dbProperties = new DBProperties(
            prop.getProperty("db.url"),
            prop.getProperty("db.username"),
            prop.getProperty("db.password")
    );
  }
}
