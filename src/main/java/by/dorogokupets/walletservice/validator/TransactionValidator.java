package by.dorogokupets.walletservice.validator;

import java.math.BigDecimal;

public class TransactionValidator {
  public boolean isValidAmount(BigDecimal amount) {
    return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
  }
}
