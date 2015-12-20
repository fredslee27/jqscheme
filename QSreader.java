
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


Importantly, does not understand: quote, quasiquote, improper list (.-notation), float, string
*/

import java.util.ArrayList;

public class QSreader {
    enum ParserState {
	PARSER_INIT,
	PARSER_INTEGER,
	PARSER_COMMENT,
	PARSER_SYMBOL,
    } parse;
    enum TokenType {
	TOKTYPE_NONE,
	TOKTYPE_INTEGER,
	TOKTYPE_SYMBOL,
	TOKTYPE_LIST_OPEN,
	TOKTYPE_LIST_CLOSE,
    } toktype;
    ArrayList<char> tokval;

    public QSreader () {
	parser = PARSER_INIT;
	toktype = TOKTYPE_NONE;
    }
};
