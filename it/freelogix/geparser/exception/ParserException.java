package it.freelogix.geparser.exception;

public class ParserException extends RuntimeException {
  public ParserException(String msg) {
    super(msg);
  }
  
  public ParserException(ParserException e) {
	    super(e);
	  }
}