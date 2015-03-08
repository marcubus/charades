package com.marcubus.charades;

import static org.junit.Assert.*;

import org.junit.Test;

import com.marcubus.charades.service.SecretFactory;

public class AppTest {

  @Test
  public void main() {
    App.main(new String[0]);
  }
  
  @Test
  public void getSecretFactory() throws Exception {
    App c = new App();
    SecretFactory factory = c.getSecretFactory();
    assertNotNull(factory);
  }
  
}
