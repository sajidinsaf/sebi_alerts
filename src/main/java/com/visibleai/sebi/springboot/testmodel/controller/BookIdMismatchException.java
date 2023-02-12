package com.visibleai.sebi.springboot.testmodel.controller;

public class BookIdMismatchException extends RuntimeException {

  private static final long serialVersionUID = -4339151373991454859L;

  public BookIdMismatchException() {
    super();
  }

  public BookIdMismatchException(String message, Throwable cause) {
    super(message, cause);
  }
}
