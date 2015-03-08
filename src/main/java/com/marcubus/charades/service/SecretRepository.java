package com.marcubus.charades.service;

import com.marcubus.charades.model.Category;
import com.marcubus.charades.model.Secret;
import com.marcubus.charades.service.exception.OutOfSecretsException;

public interface SecretRepository {

  Category getCategory();

  Secret yeild() throws OutOfSecretsException;

  int getRemainingSecrets();

  void reset();

}
