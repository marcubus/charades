package com.marcubus.charades.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.marcubus.charades.model.Secret;

public class SecretFactoryImpl implements SecretFactory {

  Random selector;
  Map<String, SecretRepository> repositories;
  
  public SecretFactoryImpl() {
    selector = new Random();
    repositories = new HashMap<String, SecretRepository>();
  }
  
  @Override
  public void addRepository(SecretRepository secrets) throws SecretRepositoryHasNoAvailableSecretsException {
    if (secrets.getRemainingSecrets() <= 0) 
      throw new SecretRepositoryHasNoAvailableSecretsException();
    repositories.put(secrets.getId(), secrets);
  }

  @Override
  public Secret getSecret() throws NoAvailableSecretRepositoriesException {
    return getSecret(getRandomSecretRepositoryId());
  }

  @Override
  public Secret getSecret(String secretRepositoryId) throws NoAvailableSecretRepositoriesException {
    if (repositories.size() == 0 || !repositories.containsKey(secretRepositoryId))
      throw new NoAvailableSecretRepositoriesException();
    SecretRepository secrets = repositories.get(secretRepositoryId);
    Secret secret = null;
    try {
      secret = secrets.yeild();
    } catch (OutOfSecretsException e) {
      secrets.reset();
      secret = getSecret(secretRepositoryId);
    }
    return secret;
  }
  
  private String getRandomSecretRepositoryId() {
    if (repositories.size() == 0)
      return null;
    int index = selector.nextInt(repositories.size());    
    return repositories.keySet().toArray(new String[repositories.size()])[index];
  }

}
