package it.freelogix.geparser.exception;

public class RuleException extends ParserException {
  public RuleException(String msg) {
    super(msg);
  }
  
  public RuleException(RuleException e) {
	    super(e);
	  }
}