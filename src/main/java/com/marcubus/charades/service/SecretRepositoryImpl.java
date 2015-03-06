package com.marcubus.charades.service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.marcubus.charades.model.Secret;

public class SecretRepositoryImpl implements SecretRepository {

  private String id;
  private String path;
  private Random selector;
  
  private List<Secret> secrets;
  private List<Secret> publicKnowledge; 
    
  public SecretRepositoryImpl(final String id, final String path) throws IOException {
    this.id = id;
    this.path = path;
    init();
  }

  private void init() throws IOException {
    selector = new Random();
    secrets = new ArrayList<Secret>();
    publicKnowledge = new ArrayList<Secret>();
    
    List<String> rawSecrets = Files.readAllLines(Paths.get(path), Charset.forName("UTF-8"));
    rawSecrets.stream().forEach(raw -> secrets.add(new Secret(raw)));    
  }
  
  public Secret yeild() throws OutOfSecretsException {
    if (secrets.size() == 0)
      throw new OutOfSecretsException();
    Secret result = secrets.remove(selector.nextInt(secrets.size()));
    publicKnowledge.add(result);
    return result;
  }

  public void reset() {
    secrets.addAll(publicKnowledge);
    publicKnowledge.clear();
  }

  public String getId() {
    return id;
  }

  @Override
  public int getRemainingSecrets() {
    return secrets.size();
  }

}
