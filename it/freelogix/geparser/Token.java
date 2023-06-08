package it.freelogix.geparser;

import java.util.LinkedList;
import java.util.regex.Pattern;

// $.error.message

public class Token implements Cloneable{
	protected final Pattern regex;
	protected final int tokenId;
	protected String regexString;
	protected String sequence;
	
	protected LinkedList<Rule> parseRules = new LinkedList<Rule>();
	protected LinkedList<Action> parseActions = new LinkedList<Action>();
	
	protected LinkedList<Action> tokenizeActions = new LinkedList<Action>();
	
	
	public Token(String regex, int tokenId)
	{
		super();
		this.regexString=regex;
		this.regex = Pattern.compile("^("+regex+")");
		this.tokenId = tokenId;
	}
	
	// ---------------------------------------------------------------------
	
	public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
	
	/**
	 * Set data to token working-on 
	 * @param sequence	String
	 */
	public void setSequence(String sequence) {
		this.sequence=sequence;
	}
	
	
	/**
	 * get data to token working-on 
	 * @return String
	 */
	public String getSequence() {
		return this.sequence;
	}
	
	/**
	 * get token ID
	 * @return int
	 */
	public int getTokenID() {
		return this.tokenId;
	}
	
	/**
	 * Get regexp string
	 * @return String
	 */
	public String getRegexpString() {
		return this.regexString;
	}
	
	/**
	 * Add a role evaluate in parse step
	 * @param rule Role
	 */
	public void addParseRule(Rule rule){
		parseRules.add(rule);
	}
	
	/**
	 * get all rules of parse step
	 * @return LinkedList<Rule>
	 */
	public LinkedList<Rule> getParseRules(){
		return parseRules;
	}
	
	/**
	 * Add an action to be run in parse step
	 * @param action Action
	 */
	public void addParseAction(Action action){
		parseActions.add(action);
	}
	
	/**
	 * Get all actions of parse step
	 * @return LinkedList<Action>
	 */
	public LinkedList<Action> getParseActions(){
		return parseActions;
	}
	
	/**
	 * Add an action to be run in tokenize spep, these actions
	 * are performed before add new token
	 * @param action Action
	 */
	public void addTokenizeAction(Action action){
		tokenizeActions.add(action);
	}
	
	/**
	 * Get all actions of tokenize step
	 * @return LinkedList<Action>
	 */
	public LinkedList<Action> getTokenizeActions(){
		return tokenizeActions;
	}
	
	
	// ---------------------------------------------------------------------
}
