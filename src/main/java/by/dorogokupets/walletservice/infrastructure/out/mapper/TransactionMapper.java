package by.dorogokupets.walletservice.infrastructure.out.mapper;

import domain.dto.TransactionDto;
import domain.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionMapper {
  TransactionMapper MAPPER = Mappers.getMapper(TransactionMapper.class);

  TransactionDto mapToTransactionDTO(Transaction transaction);

  Transaction mapToTransaction(TransactionDto transactionDto);
}