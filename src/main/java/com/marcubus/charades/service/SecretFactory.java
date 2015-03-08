package com.marcubus.charades.service;

import java.util.List;

import com.marcubus.charades.model.Category;
import com.marcubus.charades.model.Secret;
import com.marcubus.charades.service.exception.NoAvailableSecretRepositoriesException;
import com.marcubus.charades.service.exception.SecretRepositoryHasNoAvailableSecretsException;

public interface SecretFactory {

  void addRepository(SecretRepository secrets) throws SecretRepositoryHasNoAvailableSecretsException;

  List<Category> getCategories();
  
  Secret getSecret() throws NoAvailableSecretRepositoriesException;

  Secret getSecret(Category secretRepositoryCategory) throws NoAvailableSecretRepositoriesException;

}
