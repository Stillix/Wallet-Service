package by.dorogokupets.walletservice.liquibase;

import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * This class is responsible for running Liquibase migrations to manage the database schema.
 */
public class Liquibase {
  public static void runMigrations() {
    try {
      Connection connection = DriverManager.getConnection(
              "jdbc:postgresql://localhost:5432/Wallet-service",
              "dorogokupets",
              "ltl123"
      );
      Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
      liquibase.Liquibase liquibase = new liquibase.Liquibase("db/changelog/changelog.xml", new ClassLoaderResourceAccessor(), database);
      liquibase.update();
      System.out.println("Миграции успешно выполнены!");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
