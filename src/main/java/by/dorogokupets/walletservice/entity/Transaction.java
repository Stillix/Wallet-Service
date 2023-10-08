package by.dorogokupets.walletservice.entity;

import java.time.LocalDateTime;
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
}
