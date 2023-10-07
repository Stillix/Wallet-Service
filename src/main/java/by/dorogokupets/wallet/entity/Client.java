package by.dorogokupets.wallet.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Client {
   private String clientName;
   private String clientLastName;
   private BigDecimal balance;
   private String login;
   private String password;

   public Client() {
   }

   public String getClientName() {
      return clientName;
   }

   public void setClientName(String clientName) {
      this.clientName = clientName;
   }

   public String getClientLastName() {
      return clientLastName;
   }

   public BigDecimal getBalance() {
      return balance;
   }

   public void setBalance(BigDecimal balance) {
      this.balance = balance;
   }

   public void setClientLastName(String clientLastName) {
      this.clientLastName = clientLastName;
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
      return Objects.equals(clientName, client.clientName) && Objects.equals(clientLastName, client.clientLastName);
   }

   @Override
   public int hashCode() {
      return Objects.hash(clientName, clientLastName);
   }

   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder("Client{");
      sb.append("clientName='").append(clientName).append('\'');
      sb.append(", clientSurname='").append(clientLastName).append('\'');
      sb.append(", balance=").append(balance);
      sb.append('}');
      return sb.toString();
   }
}
