package it.freelogix.geparser.exception;

public class TokenInfoNotFoundException extends ParserException {
  public TokenInfoNotFoundException(String msg) {
    super(msg);
  }
  
  public TokenInfoNotFoundException(TokenInfoNotFoundException e) {
	    super(e);
	  }
}