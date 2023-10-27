package by.dorogokupets.walletservice.infrastructure.out.mapper;

import by.dorogokupets.walletservice.domain.dto.TransactionDto;
import by.dorogokupets.walletservice.domain.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionMapper {
  TransactionMapper MAPPER = Mappers.getMapper(TransactionMapper.class);

  TransactionDto mapToTransactionDTO(Transaction transaction);

  Transaction mapToTransaction(TransactionDto transactionDto);
}