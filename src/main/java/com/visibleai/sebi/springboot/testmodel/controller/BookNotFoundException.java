package com.visibleai.sebi.springboot.testmodel.controller;

public class BookNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 3994071398281446341L;

  public BookNotFoundException() {
    super();
  }

  public BookNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
  // ...
}