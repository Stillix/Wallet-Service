package by.dorogokupets.walletservice.liquibase;

import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

public class Liquibase {
  public static void runMigrations() {
    try {
      Properties databaseProperties = loadProperties("database.properties");
      String dbUrl = databaseProperties.getProperty("db.url");
      String dbUsername = databaseProperties.getProperty("db.username");
      String dbPassword = databaseProperties.getProperty("db.password");

      Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
      Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

      Properties liquibaseProperties = loadProperties("liquibase.properties");
      String changelogFile = liquibaseProperties.getProperty("changeLogFile");
      String schemaName = liquibaseProperties.getProperty("defaultSchemaName");
      database.setDefaultSchemaName(schemaName);

      liquibase.Liquibase liquibase = new liquibase.Liquibase(changelogFile, new ClassLoaderResourceAccessor(), database);
      liquibase.update();

      System.out.println("Миграции успешно выполнены!");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static Properties loadProperties(String fileName) throws IOException {
    Properties properties = new Properties();
    try (InputStream input = Liquibase.class.getClassLoader().getResourceAsStream(fileName)) {
      properties.load(input);
    }
    return properties;
  }
}
