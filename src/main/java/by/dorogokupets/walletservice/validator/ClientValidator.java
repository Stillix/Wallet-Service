package by.dorogokupets.walletservice.validator;

import by.dorogokupets.walletservice.domain.dto.ClientRegistrationDto;

public class ClientValidator {
  public static final String LOGIN_REGEX = "^[a-zA-Z0-9]{4,20}$";
  public static final String NAME_REGEX = "^[a-zA-Z]{2,30}$";
  public static final String PASSWORD_REGEX = "(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{6,30}";

  public boolean isValidClient(ClientRegistrationDto clientDTO) {
    return clientDTO != null &&
            isValidLogin(clientDTO.getLogin()) &&
            isValidPassword(clientDTO.getPassword()) &&
            isValidName(clientDTO.getClientFirstName()) &&
            isValidName(clientDTO.getClientLastName());
  }

  public boolean isValidLogin(String login) {
    return login != null && login.matches(ClientValidator.LOGIN_REGEX);
  }

  public boolean isValidPassword(String password) {
    return password != null && password.matches(ClientValidator.PASSWORD_REGEX);
  }

  public boolean isValidName(String name) {
    return name != null && name.matches(ClientValidator.NAME_REGEX);
  }


}

