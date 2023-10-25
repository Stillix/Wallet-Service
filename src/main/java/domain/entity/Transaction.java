package domain.entity;

import domain.enums.TransactionType;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;


/**
 * Class representing a financial transaction associated with a client account.
 */
public class Transaction {
  private int transactionId;
  private BigDecimal amount;
  private Client client;
  private TransactionType type;
  private Timestamp timestamp;

  public Transaction() {
  }

  public Transaction(Client client, TransactionType type, BigDecimal amount, Timestamp timestamp) {
    this.client = client;
    this.type = type;
    this.amount = amount;
    this.timestamp = timestamp;
  }

  public int getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(int transactionId) {
    this.transactionId = transactionId;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public void setType(TransactionType type) {
    this.type = type;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public Client getClient() {
    return client;
  }

  public TransactionType getType() {
    return type;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Transaction that = (Transaction) o;
    return Objects.equals(transactionId, that.transactionId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionId);
  }

  /**
   * Overridden toString method to represent transaction information as a string.
   *
   * @return A string with transaction information.
   */
  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Транзакция:   ");
    sb.append("Идентификатор транзакции - ").append(transactionId);
    sb.append(", имя клиента - ").append(client.getClientFirstName());
    sb.append(", фамилия клиента - ").append(client.getClientLastName());
    sb.append(", Тип транзакции - ").append(type.name());
    sb.append(", Сумма - ").append(amount);
    sb.append(", Дата и время - ").append(timestamp);
    sb.append('.');
    return sb.toString();
  }


}
