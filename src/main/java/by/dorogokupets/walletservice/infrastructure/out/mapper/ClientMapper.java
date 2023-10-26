package by.dorogokupets.walletservice.infrastructure.out.mapper;

import domain.dto.ClientAuthenticationDto;
import domain.dto.ClientRegistrationDto;
import domain.entity.Client;
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
