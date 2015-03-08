package com.marcubus.charades.service;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.marcubus.charades.model.Category;
import com.marcubus.charades.model.Secret;
import com.marcubus.charades.service.exception.OutOfSecretsException;

public class SecretRepositoryImpl implements SecretRepository {

  private Category category;
  private String path;
  
  private List<Secret> secrets;
  private List<Secret> publicKnowledge; 
    
  public SecretRepositoryImpl(final String categoryName, final String path) throws Exception {    
    this.path = path;
    readSecretsFromFile();    
    this.category = new Category(categoryName, secrets.size());
  }

  private void readSecretsFromFile() throws Exception {
    secrets = new ArrayList<Secret>();
    publicKnowledge = new ArrayList<Secret>();    
    List<String> rawSecrets = Files.readAllLines(getFilePath(path), Charset.defaultCharset());
    rawSecrets.stream().forEach(raw -> secrets.add(new Secret(raw)));
    Collections.shuffle(secrets);
  }
  
  private Path getFilePath(String path) throws Exception {
    Path result = new File(path).isFile() ? Paths.get(path) :
        Paths.get(this.getClass().getResource(path).toURI());
    return result;
  }
  
  public Secret yeild() throws OutOfSecretsException {
    if (secrets.size() == 0)
      throw new OutOfSecretsException();
    Secret result = secrets.remove(secrets.size() - 1);
    publicKnowledge.add(result);
    return result;
  }

  public void reset() {
    secrets.addAll(publicKnowledge);
    Collections.shuffle(secrets); 
    publicKnowledge.clear();
  }

  public Category getCategory() {
    return category;
  }

  @Override
  public int getRemainingSecrets() {
    return secrets.size();
  }

}
