package com.marcubus.charades.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.marcubus.charades.model.Category;
import com.marcubus.charades.model.Secret;
import com.marcubus.charades.service.exception.NoAvailableSecretRepositoriesException;
import com.marcubus.charades.service.exception.OutOfSecretsException;
import com.marcubus.charades.service.exception.SecretRepositoryHasNoAvailableSecretsException;

public class SecretFactoryTest {

  private SecretFactory factory;

  @Before
  public void setup() {
    factory = new SecretFactoryImpl();    
  }
  
  @Test
  public void construct() {
    assertNotNull(factory);
  }
  
  @Test(expected=NoAvailableSecretRepositoriesException.class)
  public void getSecretFromEmptyFactory() throws Exception {
    factory.getSecret();
  }
  
  @Test(expected=SecretRepositoryHasNoAvailableSecretsException.class)
  public void addSecretRepository() throws Exception {
    SecretRepository secrets = mock(SecretRepository.class);
    factory.addRepository(secrets);
  }
  
  @Test
  public void getSecret() throws Exception {
    Secret secret = new Secret("test", new Category("derp"));    
    SecretRepository secrets = mock(SecretRepository.class);
    
    when(secrets.getRemainingSecrets()).thenReturn(1);    
    factory.addRepository(secrets);
    when(secrets.yeild()).thenReturn(secret);       
    secret = factory.getSecret();
        
    assertEquals("test", secret.getText());
  }
  
  @Test
  public void getSecretHandleOutOfSecretsException() throws Exception {
    Secret secret = new Secret("woww", new Category("derp"));    
    SecretRepository secrets = mock(SecretRepository.class);
    
    when(secrets.getRemainingSecrets()).thenReturn(1);
    factory.addRepository(secrets);
    when(secrets.yeild()).thenThrow(new OutOfSecretsException()).thenReturn(secret);    
    secret = factory.getSecret();
        
    assertEquals("woww", secret.getText());
  }
 
  @Test
  public void addSecretRepositoryWithSecrets() throws Exception {
    SecretRepository secrets = mock(SecretRepository.class);    
    when(secrets.getRemainingSecrets()).thenReturn(1);    
    factory.addRepository(secrets);
  }
  
  @Test
  public void getSecretId() throws Exception {
    Category category = new Category("test");
    Secret secret = new Secret("xx", category);
    SecretRepository secrets = mock(SecretRepository.class);
    
    when(secrets.getRemainingSecrets()).thenReturn(1);
    when(secrets.getCategory()).thenReturn(category);
    factory.addRepository(secrets);
    
    when(secrets.yeild()).thenReturn(secret); 
    secret = factory.getSecret(category);
        
    assertEquals("xx", secret.getText());
  }

  @Test(expected=NoAvailableSecretRepositoriesException.class)
  public void getSecretWithBadId() throws Exception {
    SecretRepository secrets = mock(SecretRepository.class);
    Category category = new Category("robot");
    
    when(secrets.getRemainingSecrets()).thenReturn(1);
    when(secrets.getCategory()).thenReturn(category);
    factory.addRepository(secrets);    
    when(secrets.yeild()).thenReturn(new Secret("xx", category));       
    factory.getSecret(new Category("blaa"));
  }
  
  @Test
  public void getCategories() {
    List<Category> categories = factory.getCategories();
    assertNotNull(categories);
  }
  
  @Test
  public void getCategoriesWorks() throws Exception {
    SecretRepository secrets = mock(SecretRepository.class);
    Category category = new Category("test");
    
    when(secrets.getRemainingSecrets()).thenReturn(1);    
    when(secrets.getCategory()).thenReturn(category);
    factory.addRepository(secrets);    
    List<Category> categories = factory.getCategories();
    
    assertEquals(1, categories.size());
    assertEquals("test", categories.get(0).getName());
  }
}
