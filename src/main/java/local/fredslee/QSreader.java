package local.fredslee;

/* Bootstrapping (minimalist) Scheme reader.
Intended to bootstrap into a proper Scheme reader written in Scheme (going meta).
*/
/*

Recognized tokens:
 list-start
 list-end
 comment-line
 symbol
  via '#'-notation:
  * true
  * false

Importantly, does not understand: quote, quasiquote, improper list (.-notation), float, character, string

Strings are built in the form of "(list->string (integer->char CODEPOINT1) (integer->char CODEPOINT2) (integer->char CODEPOINT3) ...)",
or "(symbol->string SYMBOL1 SYMBOL2 ...)"

Characters are built in the form of "(integer->char UNICHR1)"

Vectors are built in the form of "(list->vector OBJECT1 OBJECT2 ...)"
*/

import java.util.ArrayList;
import java.util.Stack;
import java.lang.StringBuilder;


class BuildContext {
    public QSpair root;
    public QSpair last;
    public BuildContext () { root = null; last = null; }
}

public class QSreader {
    enum ParserState {
	INIT,
	NUMBER,
	COMMENT,
	SYMBOL,
	DONE,
    };
    enum TokenType {
	NONE,
	NUMBER,
	SYMBOL,
	LIST_OPEN,
	LIST_CLOSE,
    };

    ParserState parse;
    TokenType toktype;
    //ArrayList<Character> tokval;
    StringBuilder tokval;
    Stack<BuildContext> build;
    QSobj root;
    int crumbs;

    public QSreader () {
	parse = ParserState.INIT;
	toktype = TokenType.NONE;
	//tokval = new ArrayList<Character>();
	tokval = new StringBuilder();
	build = new Stack<BuildContext>();
	root = null;

	crumbs = 0;
    }

    void setLogLevel (int n) { crumbs = n; }
    void crumb (int thresholdLevel, String msg)
    {
	if (thresholdLevel <= crumbs) {
	    System.out.println(msg);
	}
    }

    static private String LIST_OPEN = "(";
    static private String LIST_CLOSE = ")";
    static private String NUMBER_INIT = "+-0123456789";
    static private String NUMERIC = "0123456789";
    static private String WHITESPACE = " \t\r\n";
    static private String EOL = "\r\n";
    static private String COMMENT = ";";
    protected boolean isChr (String chset, char ch) { return chset.indexOf(ch) > -1; }
    protected boolean isListOpen (char ch) { return isChr(LIST_OPEN, ch); }
    protected boolean isListClose (char ch) { return isChr(LIST_CLOSE, ch); }
    protected boolean isNumeric (char ch) { return isChr(NUMERIC, ch); }
    protected boolean isNumericInit (char ch) { return isChr(NUMBER_INIT, ch); }
    protected boolean isWhitespace (char ch) { return isChr(WHITESPACE, ch); }
    protected boolean isComment (char ch) { return isChr(COMMENT, ch); }
    protected boolean isEndOfLine (char ch) { return isChr(EOL, ch); }
    protected boolean isSymbolic (char ch) {
	if (isListOpen(ch)) return false;
	if (isListClose(ch)) return false;
	if (isWhitespace(ch)) return false;
	if (isComment(ch)) return false;
	return true;
    }


    // Given a string, parse one object out of it, returning the remainder/unconsumed string.
    public String parse (String src) {
	int consumed = 0;

	char ch = 0;
	if (consumed < src.length()) {
	    ch = src.charAt(consumed);
	    consumed++;
	}

//	while (ch != 0) {
	while (parse != ParserState.DONE) {
	    //System.out.println("Parser state=" + parse + ", tokval=" + tokval.toString() + ", ch=" + (int)ch + "/" + ch);
	    crumb(1, "Parser state=" + parse + ", tokval=" + tokval.toString() + ", ch=" + (int)ch + "/" + ch);

	    switch (parse) {
		case INIT:
		    if (ch == 0) {
			// end of string.
			parse = ParserState.DONE;
		    } else if (isListOpen(ch)) {
			tokval.append(ch);
			toktype = TokenType.LIST_OPEN;
		    } else if (isListClose(ch)) {
			tokval.append(ch);
			toktype = TokenType.LIST_CLOSE;
		    } else if (isNumericInit(ch)) {
			tokval.append(ch);
			parse = ParserState.NUMBER;
		    } else if (isWhitespace(ch)) {
			parse = ParserState.INIT;  // stay.
		    } else {
			// build symbol.
			//tokval.add(ch);
			parse = ParserState.SYMBOL;
			tokval.append(ch);
		    }
		    break;
		case NUMBER:
		    if (ch == 0) {
			// end of number.
			toktype = TokenType.NUMBER;
			parse = ParserState.DONE;
		    } else if (isNumeric(ch)) {
			tokval.append(ch);
		    } else {
			// terminate and unget one.
			toktype = TokenType.NUMBER;
			consumed--;
			parse = ParserState.INIT;
		    }
		    break;
		case COMMENT:
		    if (ch == 0) {
			// end of everything.
			parse = ParserState.DONE;
		    } else if (isEndOfLine(ch)) {
			parse = ParserState.INIT;
			tokval.setLength(0);
		    } else {
			// stay.
			//parse = ParserState.COMMENT;
		    }
		    break;
		case SYMBOL:
		    if (ch == 0) {
			// end of symbol.
			toktype = TokenType.SYMBOL;
			parse = ParserState.DONE;
		    } else if (isWhitespace(ch)) {
			toktype = TokenType.SYMBOL;
			parse = ParserState.INIT;
		    } else if (! isSymbolic(ch)) {
			toktype = TokenType.SYMBOL;
			parse = ParserState.INIT;
			consumed--;
		    } else {
			tokval.append(ch);
		    }
		    break;
		default:
		    break;
	    };

	    //System.out.println("toktype = " + toktype);

	    if (toktype != TokenType.NONE) {
		// Handle completed token.
		int i = 0;
		String token = tokval.toString();
		QSobj val = null;
		switch (toktype) {
		    case NUMBER:
		    	try {
			    i = Integer.parseInt(token);
			} catch (Exception e) {
			    i = 0;
			}
			val = new QSinteger(i);
			//System.out.println("token.int: " + i);
			crumb(1, "token.int: " + i);
			break;
		    case SYMBOL:
		    	if (token.equals("#t")) {
			    val = new QSbool(true);
			    crumb(1, "token.true");
			} else if (token.equals("#f")) {
			    val = new QSbool(false);
			    crumb(1, "token.false");
			} else {
			    val = QSobj.intern(token);
			    crumb(1, "token.symbol: " + val);
			}
			break;
		    case LIST_OPEN:
		    	crumb(1, "LIST OPEN");
			// create new pair, push.
			BuildContext ctx = new BuildContext();
			ctx.root = null;
			ctx.last = ctx.root;
			build.push(ctx);
			toktype = TokenType.NONE;
			break;
		    case LIST_CLOSE:
		    	crumb(1, "LIST CLOSE");
			ctx = build.pop();
			val = ctx.root;
			if (val == null)
			    //val = new QSnull();
			    val = QSobj.make();
			break;
		    default:
			break;
		}

		if (toktype != TokenType.NONE) {
		    // attach val to build, or set to root.
		    if (build.size() > 0) {
			BuildContext ctx = build.peek();
			//QSpair next = new QScons(val, null);
			QSpair next = QSobj.cons(val, null);
			if (ctx.root == null) {
			    // build new.
			    ctx.root = ctx.last = next;
			} else {
			    // attach.
			    ctx.last.setcdr(next);
			    ctx.last = next;
			}
		    } else {
			// no (more) nested build, toplevel object.
			root = val;
			crumb(1, "setting root to " + root);
		    }
		}

		toktype = TokenType.NONE;
		tokval.setLength(0);
	    }

	    // next char.
	    if (consumed < src.length()) {
		ch = src.charAt(consumed);
		consumed++;
	    } else if (ch == 0) {
		parse = ParserState.DONE;
	    } else {
		ch = 0;
	    }
	}

	return src.substring(consumed);
    };
};
