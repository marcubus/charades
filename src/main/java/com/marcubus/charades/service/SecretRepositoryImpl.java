package com.marcubus.charades.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.marcubus.charades.model.Category;
import com.marcubus.charades.model.Secret;
import com.marcubus.charades.service.exception.OutOfSecretsException;

public class SecretRepositoryImpl implements SecretRepository {

  private Category category;  
  private List<Secret> secrets;
  private List<Secret> publicKnowledge; 
    
  public SecretRepositoryImpl(final String categoryName, final String path) throws Exception {    
    this.category = new Category(categoryName);      
    this.publicKnowledge = new ArrayList<Secret>(); 
    this.secrets = readSecretsFromFile(category, path);  
  }

  private List<Secret> readSecretsFromFile(Category category, String path) throws Exception {
    List<Secret> secrets = new ArrayList<>();
    List<String> rawSecrets = readAllLines(path);   
    rawSecrets.stream().forEach(raw -> secrets.add(new Secret(raw, category)));
    Collections.shuffle(secrets);
    return secrets;
  }
  
  private List<String> readAllLines(String path) throws Exception {
    List<String> lines = new ArrayList<>();
    Resource resource = Files.isRegularFile(Paths.get(path)) ? 
        new FileSystemResource(path) :
          new ClassPathResource(path);        
    try (Scanner scanner = new Scanner(resource.getInputStream())) {
      while (scanner.hasNext())
        lines.add(scanner.nextLine());
    }
    return lines;
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
