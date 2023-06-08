package it.freelogix.geparser.exception;

public class ActionException extends RuntimeException {
  public ActionException(String msg) {
    super(msg);
  }
  
  public ActionException(ActionException e) {
	    super(e);
	  }
}