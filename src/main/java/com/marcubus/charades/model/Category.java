package com.marcubus.charades.model;

public class Category {

  private String name;
  private int secretCount;
  
  public Category(final String category, final int secretCount) {
    this.name = category;
    this.secretCount = secretCount;
  }

  public String getName() {
    return name;
  }
  
  public int getSecretCount() {
    return secretCount;
  }
  
}
