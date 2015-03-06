package com.marcubus.charades.service;

import com.marcubus.charades.model.Secret;

public interface SecretFactory {

  void addRepository(SecretRepository secrets) throws SecretRepositoryHasNoAvailableSecretsException;
  
  Secret getSecret() throws NoAvailableSecretRepositoriesException;

  Secret getSecret(String secretRepositoryId) throws NoAvailableSecretRepositoriesException;

}
