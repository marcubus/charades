package com.marcubus.charades.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.marcubus.charades.model.Category;
import com.marcubus.charades.model.Secret;
import com.marcubus.charades.service.exception.NoAvailableSecretRepositoriesException;
import com.marcubus.charades.service.exception.OutOfSecretsException;
import com.marcubus.charades.service.exception.SecretRepositoryHasNoAvailableSecretsException;

public class SecretFactoryImpl implements SecretFactory {

  Random selector;
  Map<Category, SecretRepository> repositories;
  private List<Category> categories;
  
  public SecretFactoryImpl() {
    selector = new Random();
    repositories = new HashMap<Category, SecretRepository>();
    categories = new ArrayList<Category>();
  }
  
  @Override
  public void addRepository(SecretRepository secrets) throws SecretRepositoryHasNoAvailableSecretsException {
    if (secrets.getRemainingSecrets() <= 0) 
      throw new SecretRepositoryHasNoAvailableSecretsException();
    repositories.put(secrets.getCategory(), secrets);
    categories.add(secrets.getCategory());
  }

  @Override
  public Secret getSecret() throws NoAvailableSecretRepositoriesException {
    return getSecret(getRandomSecretRepositoryCategoryName());
  }

  @Override
  public Secret getSecret(Category secretRepositoryCategory) throws NoAvailableSecretRepositoriesException {
    if (repositories.size() == 0 || !repositories.containsKey(secretRepositoryCategory))
      throw new NoAvailableSecretRepositoriesException();
    SecretRepository secrets = repositories.get(secretRepositoryCategory);
    Secret secret = null;
    try {
      secret = secrets.yeild();
    } catch (OutOfSecretsException e) {
      secrets.reset();
      secret = getSecret(secretRepositoryCategory);
    }
    return secret;
  }
  
  private Category getRandomSecretRepositoryCategoryName() {
    if (categories.size() == 0)
      return null;
    int index = selector.nextInt(categories.size());    
    return categories.get(index);
  }

  @Override
  public List<Category> getCategories() {
    return Collections.unmodifiableList(categories);
  }

}
