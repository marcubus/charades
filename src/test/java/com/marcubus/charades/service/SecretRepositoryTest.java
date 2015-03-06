package com.marcubus.charades.service;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.marcubus.charades.model.Secret;
import com.marcubus.charades.service.OutOfSecretsException;
import com.marcubus.charades.service.SecretRepository;
import com.marcubus.charades.service.SecretRepositoryImpl;

public class SecretRepositoryTest {

  private SecretRepository secrets;

  @Before
  public void setup() throws IOException {
    secrets = new SecretRepositoryImpl("one", "src/test/resources/one.txt");
  }
  
  @Test
  public void construct() {
    assertNotNull(secrets);
  }
  
  @Test
  public void idWorks() {
    assertEquals("one", secrets.getId());
  }
  
  
  @Test
  public void yeildNotNull() throws OutOfSecretsException {
    Secret secret = secrets.yeild();
    assertNotNull(secret);
  }
  
  @Test
  public void reset() {
    secrets.reset();    
  }
  
  @Test(expected=OutOfSecretsException.class)
  public void yeildThrowsOutOfSecretsExceptionAsExpected() throws IOException, OutOfSecretsException {
    secrets.yeild();
    secrets.yeild();
  }
  
  @Test
  public void yeildActuallyWorks() throws OutOfSecretsException {
    Secret secret = secrets.yeild();
    assertEquals("one", secret.getText());
  }

  @Test
  public void resetActuallyWorks() throws OutOfSecretsException {
    secrets.yeild();    
    secrets.reset();    
    Secret secret = secrets.yeild();
    assertEquals("one", secret.getText());
  }
  
  @Test
  public void getRemainingSecrets() {
    assertEquals(1, secrets.getRemainingSecrets());
  }
}