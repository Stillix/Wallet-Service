package by.dorogokupets.walletservice.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Transaction {
   private final UUID transactionId = UUID.randomUUID();
   private final Client client;
   private final TransactionType type;
   private final LocalDateTime timestamp;

   public Transaction(Client client, TransactionType type) {
      this.client = client;
      this.type = type;
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
      final StringBuilder sb = new StringBuilder("Transaction{");
      sb.append("transactionId=").append(transactionId);
      sb.append(", client=").append(client);
      sb.append(", type=").append(type);
      sb.append(", timestamp=").append(timestamp);
      sb.append('}');
      return sb.toString();
   }
}
