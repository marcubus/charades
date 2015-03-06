package com.marcubus.charades.model;

public class Secret {

  private String text;
  
  public Secret(final String secret) {
    setText(secret);
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

}
