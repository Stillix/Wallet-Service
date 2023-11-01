package by.dorogokupets.walletservice.infrastructure.out.mapper;

import by.dorogokupets.walletservice.domain.dto.ClientAuthenticationDto;
import by.dorogokupets.walletservice.domain.dto.ClientRegistrationDto;
import by.dorogokupets.walletservice.domain.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {
  ClientMapper MAPPER = Mappers.getMapper(ClientMapper.class);

  ClientRegistrationDto mapToClientRegistrationDTO(Client client);

  ClientAuthenticationDto mapToClientAuthenticationDTO(Client client);

  Client mapToClient(ClientRegistrationDto clientRegistrationDTO);

  Client mapToClient(ClientAuthenticationDto clientAuthenticationDTO);

}
