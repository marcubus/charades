package com.marcubus.charades.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.marcubus.charades.model.Secret;

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
    Secret secret = new Secret("test");
    SecretRepository secrets = mock(SecretRepository.class);
    
    when(secrets.getRemainingSecrets()).thenReturn(1);    
    factory.addRepository(secrets);
    when(secrets.yeild()).thenReturn(secret);       
    secret = factory.getSecret();
        
    assertEquals("test", secret.getText());
  }
  
  @Test
  public void getSecretHandleOutOfSecretsException() throws Exception {
    Secret secret = new Secret("woww");
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
    Secret secret = new Secret("xx");
    SecretRepository secrets = mock(SecretRepository.class);
    
    when(secrets.getRemainingSecrets()).thenReturn(1);
    when(secrets.getId()).thenReturn("test");
    factory.addRepository(secrets);
    
    when(secrets.yeild()).thenReturn(secret);       
    secret = factory.getSecret("test");
        
    assertEquals("xx", secret.getText());
  }

  @Test(expected=NoAvailableSecretRepositoriesException.class)
  public void getSecretWithBadId() throws Exception {
    Secret secret = new Secret("xx");
    SecretRepository secrets = mock(SecretRepository.class);
    
    when(secrets.getRemainingSecrets()).thenReturn(1);
    when(secrets.getId()).thenReturn("robot");
    factory.addRepository(secrets);
    
    when(secrets.yeild()).thenReturn(secret);       
    factory.getSecret("blaa");
  }
   
}
