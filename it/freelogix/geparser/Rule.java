package it.freelogix.geparser;

import it.freelogix.geparser.exception.RuleException;

abstract public class Rule {
	abstract public void evaluate(Parser parser, Token token) throws RuleException;
}
