package com.marcubus.charades;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.marcubus.charades.service.SecretFactory;
import com.marcubus.charades.service.SecretFactoryImpl;
import com.marcubus.charades.service.SecretRepositoryImpl;
import com.marcubus.charades.service.exception.SecretRepositoryHasNoAvailableSecretsException;

@SpringBootApplication
public class App {
  
  @Bean
  public SecretFactory getSecretFactory() throws Exception, SecretRepositoryHasNoAvailableSecretsException {
    SecretFactory factory = new SecretFactoryImpl();
    factory.addRepository(new SecretRepositoryImpl("books", "books.txt"));
    factory.addRepository(new SecretRepositoryImpl("movies", "movies.txt"));
    factory.addRepository(new SecretRepositoryImpl("songs", "songs.txt"));
    return factory;
  }
  
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
  
}
