package com.marcubus.charades.service;

import com.marcubus.charades.model.Secret;

public interface SecretRepository {

  Secret yeild() throws OutOfSecretsException;

  void reset();

  String getId();

  int getRemainingSecrets();

}
