package com.marcubus.charades;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.marcubus.charades.model.Secret;
import com.marcubus.charades.service.SecretFactory;

@Component
public class CLIController implements CommandLineRunner {
  
  @Autowired
  private SecretFactory factory;

  @Override
  public void run(String... args) throws Exception {
    try (BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in))) {
      while (true) {
        Secret secret = factory.getSecret();
        System.out.println(secret.getText());
        bufferRead.readLine();
      }
    }    
  }
  
}
