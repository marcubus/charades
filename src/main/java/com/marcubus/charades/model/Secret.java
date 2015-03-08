package com.marcubus.charades.model;

public class Secret {

  private String text;
  private Category category;
  
  public Secret(final String secret, Category category) {
    this.text = secret;
    this.category = category;
  }

  public String getText() {
    return text;
  }

  public Category getCategory() {
    return category;
  }

}
