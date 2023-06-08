package it.freelogix.geparser;

import it.freelogix.geparser.exception.ActionException;

abstract public class Action {
	abstract public void run(Parser parser, Token token) throws ActionException;
}
