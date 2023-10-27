package domain.dto;

import domain.enums.TransactionType;
import lombok.*;


import java.math.BigDecimal;


public class TransactionDto {

  private int clientId;
  private BigDecimal amount;
  private TransactionType type;

  public int getClientId() {
    return clientId;
  }

  public void setClientId(int clientId) {
    this.clientId = clientId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public TransactionType getType() {
    return type;
  }

  public void setType(TransactionType type) {
    this.type = type;
  }
}
