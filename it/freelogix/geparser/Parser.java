package it.freelogix.geparser;

import java.util.LinkedList;
import java.util.regex.Matcher;

import it.freelogix.geparser.exception.ActionException;
import it.freelogix.geparser.exception.ParserException;
import it.freelogix.geparser.exception.RuleException;
import it.freelogix.geparser.exception.TokenInfoNotFoundException;

// $.error.message

public class Parser {

	private LinkedList<Token> tokenInfos;
	private LinkedList<Token> tokens;
	private String source;

	public static final int MODE_RULES_AND_ACIONS = 10;
	public static final int MODE_ALL_RULES_BEFORE_ALL_ACTIONS= 20;
	public static final int MODE_ONLY_RULES= 30;
	public static final int MODE_ONLY_ACTIONS= 40;

	private int mode=MODE_RULES_AND_ACIONS;

	/**
	 * Return token info by token id
	 * @param _tokenId int
	 * @return Token
	 */
	public Token getTokenInfo(int tokenId) {
		for(Token ti :tokenInfos) {
			if(ti.getTokenID()==tokenId) {
				return ti;
			}
		}
		throw new TokenInfoNotFoundException("Token not found with ID: "+tokenId);
	}

	/**
	 * Find and return token info by regex string
	 * @param _regex String
	 * @return TokenInfo
	 */
	public Token getTokenInfo(String regex) {
		for(Token ti :tokenInfos) {
			if(ti.getRegexpString().equals(regex)) {
				return ti;
			}
		}
		throw new TokenInfoNotFoundException("Token not found with regex string: "+regex);
	}

	/**
	 * Remove all token info
	 */
	public void tokenInfosClear() {
		tokenInfos.clear();
	}


	/**
	 * Add new token info
	 * @param token Token
	 */
	public void addToken(Token token)
	{
		tokenInfos.add(token);
	}

	public void addToken(Token token, int position) {
		tokenInfos.add(position, token);
	}

	public void addTokenFirst(Token token) {
		tokenInfos.addFirst(token);
	}

	public void addTokenAfter(Token token) {
		for(int a=0;a<tokenInfos.size();a++) {
			if(tokenInfos.get(a).equals(token)) {
				if(a==tokenInfos.size()-1) {
					tokenInfos.add(token);
				}else {
					tokenInfos.add(a+1,token);
				}
			}
		}
	}

	/**
	 * Return all token after tokenizin
	 * @return LinkedList<Token>
	 */
	public LinkedList<Token> getTokens()
	{
		return tokens;
	}

	/**
	 * Return all tokens info
	 * @return LinkedList<TokenInfo> 
	 */
	public LinkedList<Token> getTokensInfo()
	{
		return tokenInfos;
	}

	/**
	 * Set source data(string) that will be tokenize 
	 * @param source String
	 */
	public void setSource(String source) {
		this.source=source;
	}

	/**
	 * get source data
	 * @return String
	 */
	public String getSource() {
		return this.source;
	}

	/**
	 * Set mode
	 * @param mode integer
	 */
	public void setMode(int mode) {
		this.mode=mode;
	}

	// -------------------------------------------------------

	public Parser()
	{
		tokenInfos = new LinkedList<Token>();
		tokens = new LinkedList<Token>();
	}

	public void tokenize(String str) throws ParserException,ActionException
	{
		String s = str.trim();
		tokens.clear();
		while (!s.equals(""))
		{
			boolean match = false;

			for (Token info : tokenInfos)
			{
				Matcher m = info.regex.matcher(s);
				if (m.find())
				{
					match = true;
					String tok = m.group().trim();
					s = m.replaceFirst("").trim();

					info.setSequence(tok);

					for(Action tokenizeAction: info.getTokenizeActions()) {
						tokenizeAction.run(this, info);
					}

					try {
						tokens.add((Token)info.clone());
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
					break;
				}
			}
			if (!match) {
				throw new ParserException("Unexpected character in input: "+s.charAt(0));
			}
		}
	}

	// -------------------------------------------------------

	public void evaluateTokenParseRules(Token token) {
		for(Rule r:token.getParseRules()) {
			r.evaluate(this,token);
		}
	}

	public void runTokenParseActions(Token token) {
		for(Action a:token.getParseActions()) {
			a.run(this,token);
		}
	}

	public void parse() throws RuleException, ActionException{
		tokenize(source);

		switch(mode) {
		case MODE_RULES_AND_ACIONS:
			for(Token t:tokens) {
				evaluateTokenParseRules(t);
				runTokenParseActions(t);
			}
			break;
		case MODE_ALL_RULES_BEFORE_ALL_ACTIONS:
			for(Token t:tokens) {
				evaluateTokenParseRules(t);
			}

			for(Token t:tokens) {
				runTokenParseActions(t);
			}
			break;
		case MODE_ONLY_RULES:
			for(Token t:tokens) {
				evaluateTokenParseRules(t);
			}
			break;
		case MODE_ONLY_ACTIONS:
			for(Token t:tokens) {
				runTokenParseActions(t);
			}
			break;
		}
	}
}
