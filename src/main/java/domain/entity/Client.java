package domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * A class representing a client's account.
 */

public class Client {
  private int clientId;
  private String clientFirstName;
  private String clientLastName;
  private BigDecimal balance;
  private String login;
  private String password;

  public Client() {
  }

  public int getClientId() {
    return clientId;
  }

  public void setClientId(int clientId) {
    this.clientId = clientId;
  }

  public String getClientFirstName() {
    return clientFirstName;
  }

  public void setClientFirstName(String clientFirstName) {
    this.clientFirstName = clientFirstName;
  }

  public String getClientLastName() {
    return clientLastName;
  }

  public void setClientLastName(String clientLastName) {
    this.clientLastName = clientLastName;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Client client = (Client) o;
    return Objects.equals(clientFirstName, client.clientFirstName) && Objects.equals(clientLastName, client.clientLastName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clientFirstName, clientLastName);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Информация об счете:\n");
    sb.append("Имя: ").append(clientFirstName).append("\n");
    sb.append("Фамилия: ").append(clientLastName).append("\n");
    sb.append("Баланс: ").append(balance).append("\n");
    return sb.toString();
  }
}
