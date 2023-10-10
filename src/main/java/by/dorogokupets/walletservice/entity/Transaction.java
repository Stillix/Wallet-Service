package by.dorogokupets.walletservice.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Transaction {
   private UUID transactionId = UUID.randomUUID();
   private final Client client;
   private final TransactionType type;
   private final LocalDateTime timestamp;

   public Transaction(Client client, TransactionType type, UUID transactionId) {
      this.client = client;
      this.type = type;
      this.transactionId = transactionId;
      this.timestamp = LocalDateTime.now();
   }

   public UUID getTransactionId() {
      return transactionId;
   }

   public Client getClient() {
      return client;
   }

   public TransactionType getType() {
      return type;
   }

   public LocalDateTime getTimestamp() {
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

   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder("Транзакция:   ");
      sb.append("Идентификатор транзакции - ").append(transactionId);
      sb.append(", имя клиента - ").append(client.getClientFirstName());
      sb.append(", фамилия клиента - ").append(client.getClientLastName());
      sb.append(", Тип транзакции - ").append(type);
      sb.append(", Дата и время - ").append(timestamp);
      sb.append('.');
      return sb.toString();
   }
}
