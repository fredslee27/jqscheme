package local.fredslee;

/* Scheme objects

* null
* boolean
* char
* number
** integer
** rational
** real
** complex
* string
* vector
* procedure/closure


* list

*/

import java.lang.Exception;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Map;
import java.util.TreeMap;
import java.lang.*;

interface IQSobj {
    public String repr () throws Exception;
};

interface IQSprimitive {
    public QSobj apply (QSobj arglist);
};

class QSobj extends Object implements IQSobj {
//  static TreeSet<QSsym> symtree = null;
    static QSsymtree symtree = null;
    static QSprimreg primreg = null;

    public QSobj () {
        if (symtree == null) {
	    build_symtree();
        }
	if (primreg == null) {
	    build_primreg();
	}
    }
    @Override public String repr () throws Exception { return "#<QSobject>"; }
    @Override public String toString () {
	try {
	    return repr();
	} catch (Exception e) {
	    return "{EXCEPTION " + e + "}";
	}
    }

    static protected void build_symtree () {
	if (symtree == null) {
            //symtree = new TreeSet<QSsym>();
            //symtree = new TreeMap<String,QSsym>();
	    symtree = new QSsymtree();
	}
    }
    protected void build_primreg () {
	if (primreg == null) {
	    primreg = new QSprimreg();
	}
    }

    public String dump () {
        if (this instanceof QSnull) {
            return "#<null>";
        }
        else if (this instanceof QSbool) {
            return "#<bool:" + (asBool().Boolean() ? "true" : "false") + ">";
        }
        else if (this instanceof QSchar) {
            return "#<char:" + (int)(asChar().Character()) + ">";
        }
        else if (this instanceof QSinteger) {
            return "#<int:" + asInteger() + ">";
        }
        else if (this instanceof QSreal) {
            return "#<real:" + asReal() + ">";
        }
        else if (this instanceof QSrational) {
            QSrational r = asRational();
            return "#<rational:" + r.numerator() + ":" + r.denominator() + ">";
        }
        else if (this instanceof QScomplex) {
            QScomplex n = asComplex();
            return "#<complex:" + n.real() + ":" + n.imag() + ">";
        }
        else if (this instanceof QSstr) {
            return "#<str:" + asStr() + ">";
        }
        else if (this instanceof QSvec) {
            return "#<vector " + asVec().length() + ">";
        }
        else if (this instanceof QSsym) {
            return "#<symbol " + asSym().symid() + ":" + asSym().name() + ">";
        }
        else if (this instanceof QSpair) {
            return "#<pair" + ":" + asPair().car().dump() + ":" + asPair().cdr().dump() + ">";
        }
        else {
            return "#<UNK:" + this.getClass() + ">";
        }
    }

    protected QSnull asNull () { return (QSnull)this; }
    protected QSbool asBool () { return (QSbool)this; }
    protected QSchar asChar () { return (QSchar)this; }
    protected QSinteger asInteger() { return (QSinteger)this; }
    protected QSreal asReal() { return (QSreal)this; }
    protected QSrational asRational() { return (QSrational)this; }
    protected QScomplex asComplex() { return (QScomplex)this; }
    protected QSstr asStr() { return (QSstr)this; }
    protected QSsym asSym() { return (QSsym)this; }
    protected QSvec asVec() { return (QSvec)this; }
    protected QSpair asPair() { return (QSpair)this; }
    //protected QSpair asPair() { if (this instanceof QSpair) return (QSpair)this; else return QSpair.guard; }
    //protected QSpair asPair() { if (this instanceof QSpair) return (QSpair)this; else return null; }
    //protected QSpair asPair() { if (this instanceof QSpair) return (QSpair)this; else return null; }
    protected QScontinuation asKont() { return (QScontinuation)this; }
    protected QScontinuation asContinuation() { return (QScontinuation)this; }

/*
    public boolean isNull () { return (this instanceof QSnull); }
    public boolean isBoolean () { return (this instanceof QSbool); }
    public boolean isCharacter () { return (this instanceof QSchar); }
    public boolean isNumber () { return (this instanceof QSnumber); }
    public boolean isVector () { return (this instanceof QSvec); }
    public boolean isString () { return (this instanceof QSstr); }
    public boolean isSymbol () { return (this instanceof QSsym); }
    public boolean isPrimitive () { return (this instanceof QSprim); }
    public boolean isProcedure () { return (this instanceof QSproc); }
    public boolean isPair () { return (this instanceof QSpair); }

    public boolean isList () { return (QSpair.p(this) && QSpair.QSlist.p((QSpair)this)); }
*/
    public boolean isNull () { return nullp(this); }
    public boolean isBoolean () { return booleanp(this); }
    public boolean isCharacter () { return charp(this); }
    public boolean isNumber () { return numberp(this); }
    public boolean isVector () { return vectorp(this); }
    public boolean isString () { return stringp(this); }
    public boolean isSymbol () { return symbolp(this); }
    public boolean isPrimitive () { return (this instanceof QSprim); }
    public boolean isProcedure () { return (this instanceof QSproc); }
    public boolean isPair () { return pairp(this); }
    public boolean isContinuation () { return continuationp(this); }

    public boolean isList () { return listp(this); }


    /* Type predicates, static. */
    static public boolean nullp (QSobj x) { return ((x == null) || (x instanceof QSnull)); }
    static public boolean booleanp (QSobj x) { return ((x != null) && (x instanceof QSbool)); }
    static public boolean charp (QSobj x) { return ((x != null) && (x instanceof QSchar)); }
    static public boolean numberp (QSobj x) { return ((x != null) && (x instanceof QSnumber)); }
      static public boolean integerp (QSobj x) { return ((x != null) && (x instanceof QSinteger)); }
      static public boolean rationalp (QSobj x) { return ((x != null) && (x instanceof QSrational)); }
      static public boolean realp (QSobj x) { return ((x != null) && (x instanceof QSreal)); }
      static public boolean complexp (QSobj x) { return ((x != null) && (x instanceof QScomplex)); }
    static public boolean stringp (QSobj x) { return ((x != null) && (x instanceof QSstr)); }
    static public boolean symbolp (QSobj x) { return ((x != null) && (x instanceof QSsym)); }
    static public boolean vectorp (QSobj x) { return ((x != null) && (x instanceof QSvec)); }
    static public boolean procedurep (QSobj x) { return ((x != null) && ((x instanceof QSproc) || (x instanceof QSprim))); }
    static public boolean pairp (QSobj x) { return ((x != null) && (x instanceof QSpair)); }
    static public boolean continuationp (QSobj x) { return ((x != null) && (x instanceof QScontinuation)); }

    static public boolean listp (QSobj x) { return QSpair.QSlist.p(x); }

    static public QSsym intern (String symname) {
	if (symtree == null) {
	    build_symtree();
	}
	return symtree.intern(symname);
    }
    // Factory functions sensitive to argument type.
    static public QSobj make () { return new QSnull(); }
    static public QSobj make (boolean b) { return new QSbool(b); }
    static public QSobj make (char c) { return new QSchar(c); }
    static public QSobj make (int i) { return new QSinteger(i); }
    static public QSobj make (double f) { return new QSreal(f); }
    static public QSobj make (int p, int q) { return new QSrational(p,q); }
    static public QSobj make (double a, double b) { return new QScomplex(a,b); }
    static public QSobj make (String s) { return new QSstr(s); }
    /*
      static public QSobj make (QSobj[] elts) {
        QSvec retval = new QSvec(elts.length);
        for (int i = 0; i < elts.length; i++) {
          retval.setq(i, elts[i]);
        }
        return retval;
      }
    */

    static public QSobj vec (int initlen) { return new QSvec(initlen, null); }
    static public QSobj vec (int initlen, QSobj fill) { return new QSvec(initlen, fill); }
    static public QSpair cons (QSobj a, QSobj d) { return new QSpair(a,d); }
    static public QSpair list (QSobj ... x) { return QSpair.QSlist.make(x); }
};


class QSnull extends QSobj {
    public QSnull () { }
    //@Override public String repr () { return "'()"; }
    @Override public String repr () { return "()"; }
};

class QSbool extends QSobj {
    boolean b;
    public QSbool (boolean initb) { b = initb; }
    public QSbool (int initb) { b = (initb != 0); }
    public Boolean Boolean () { return new Boolean(b); }
    @Override public String repr () { return (b ? "#t" : "f"); }
};

class QSchar extends QSobj {
    char c;
    public QSchar (char initc) { c = initc; }
    public Character Character () { return Character.valueOf(c); }
    @Override public String repr () { return "#\\" + c; }
};

class QSstr extends QSobj {
    String s;
    public QSstr (String inits) { s = inits; }
    public String String () { return new String(s); }
    @Override public String repr () { return "\"" + s.toString() + "\""; }
};

class QSsym extends QSobj {
    String s;
    int _symid;
    public QSsym (String inits, int symid) {
        s = inits;
        _symid = symid;
    }
    //public String String () { return s; }
    //public Integer Integer () { return new Integer(_symid); }
    public String name () { return s; }
    public int symid () { return _symid; }
    @Override public String repr () { return s.toString(); }
};

class QSvec extends QSobj {
    QSobj[] data;
    public QSvec () {
        data = new QSobj[0];
    }
    public QSvec (int initlen, QSobj initfill) {
        data = new QSobj[initlen];
        fill(initfill);
    }
    public QSvec (int initlen) { data = new QSobj[initlen]; fill(null);
    }
    public int length () {
        return data.length;
    }
    public QSobj get (int idx) throws ArrayIndexOutOfBoundsException {
        if ((idx < 0) || (idx >= data.length)) {
            throw new ArrayIndexOutOfBoundsException(idx);
        }
        return data[idx];
    }
    public void setq (int idx, QSobj v) throws ArrayIndexOutOfBoundsException {
        if ((idx < 0) || (idx >= data.length)) {
            throw new ArrayIndexOutOfBoundsException(idx);
        }
        data[idx] = v;
    }
    public void fill (QSobj v) {for(int i=0;i<data.length;i++){data[i]=v;}}
    @Override public String repr () {
//        return "#<vector " + data.length + ">";
	StringBuilder ss = new StringBuilder();
	ss.append("#(");
	for (int i = 0; i < length(); i++) {
	    if (i != 0) {
		ss.append(" ");
	    }
	    QSobj elt = get(i);
	    if (elt != null) {
		ss.append(elt.toString());
	    } else {
		ss.append("()");
	    }
	}
	ss.append(")");
	return ss.toString();
    }
}


class QSprim extends QSobj {
};

class QSproc extends QSobj {
};

class QSpair extends QSobj {
    // Guard value to let car and cdr on non-pair return recursible value.
    static public final QSpair guard = new QSpair(null,null);

    QSobj a, d;

    static class /*QSlist*/QSlist {
	private static QSpair nextpair (QSpair pair)
	{
	    if (pair.cdr() == null)
		return null;
	    QSobj next = pair.cdr();
	    if (QSpair.p(next)) {
		return (QSpair)next;
	    } else {
		return null;
	    }
	}
	static QSpair make (QSobj ... elements) {
	    QSpair retval = null;
	    QSpair last = null;
	    QSpair next = null;
	    for (QSobj item : elements) {
		if (retval == null) {
		    retval = last = next = QSobj.cons(item, null);
		} else {
		    next = QSobj.cons(item, null);
		    last.setcdr(next);
		    last = next;
		}
	    }
	    return retval;
	};
	/* isList() */
	static boolean p (QSobj root) {
	    if (root == null) {
		return true;
	    }
	    if (! (root instanceof QSpair)) {
		return false;
	    }
	    QSpair iter = (QSpair)root;
	    iter = end(iter);
	    return ((iter == null) || (iter.cdr() == null));
	};
	static int length (QSpair root) {
	    int retval = 0;
	    QSpair iter = root;
	    while (iter != null) {
		retval++;
		iter = nextpair(iter);
	    };
	    return retval;
	};
	static QSpair copy (QSpair root) {
	    QSpair retval = null;
	    QSpair last = retval;
	    //QSpair iter = pair();
	    QSpair iter = root;
	    while (iter != null) {
		if (retval == null) {
		    last = retval = new QSpair(iter.car(), null);
		} else {
		    QSpair next = new QSpair(iter.car(), null);
		    last.setcdr(next);
		    last = next;
		}
	    }
	    return retval;
	};
	/* Return final cons of a list. */
	static QSpair end (QSpair root) {
	    QSpair iter = root;
	    if (iter == null)
		return null;
	    while ((iter.cdr() != null) && QSpair.p(iter)) {
		iter = (QSpair)(iter.cdr());
	    }
	    return iter;
	};
	/* Return (sub)list starting from nth cons cell; may return null. */
	static QSpair tail (QSpair root, int idx) {
	    QSpair retval = root;
	    QSobj next = null;
	    int k = idx;
	    while ((k > 0) && (retval != null)) {
		retval = nextpair(retval);
		k--;
	    }
	    return retval;
	};
	/* Return car of nth cons cells. */
	static QSobj nth (QSpair root, int idx) {
	    QSpair sublist = tail(root, idx);
	    if (sublist != null)
		return sublist.car();
	    return null;
	};
    };
    //QSlist list = null;
    //protected QSpair pair () { return this; }

    static public boolean p (QSobj root) { return (root instanceof QSpair); }
    static public boolean listp (QSobj root) { return (QSpair.p(root) && QSlist.p((QSpair)root)); }

    public QSpair (QSobj inita, QSobj initd) { build(inita, initd); }
    public QSpair (QSobj inita) { build(inita, null); }
    protected void build (QSobj inita, QSobj initd)
    {
	a = inita;
	d = initd;
	//list = new QSlist();
	}
    public QSobj car () { return a; }
    public QSobj cdr () { return d; }
    void setcar (QSobj v) { if (true) a = v; }
    void setcdr (QSobj v) { if (true) d = v; }
    @Override public String toString () {
	StringBuilder temp = new StringBuilder();
	temp.append("(");
	QSobj iter = this;
	while (iter != null) {
	    if (! (iter == this)) {
		temp.append(" ");
	    }
	    if (iter instanceof QSpair) {
		QSpair visitor = iter.asPair();
		// proper list; stringify and advance.
		if (visitor.car() != null) {
		    temp.append(visitor.car().toString());
		} else {
		    temp.append("()");
		}
		iter = visitor.cdr();
	    } else {
		// end of improper list.
		temp.append(". ");
		temp.append(iter.toString());
		iter = null;
	    }
	}
	temp.append(")");
	return temp.toString();
    };

    /* list. */
    //public QSpair list (QSobj ... elts) { return QSlist.make(elts); }
    public boolean isList () { return QSlist.p(this); }
    public int length () { return QSlist.length(this); }
    public QSpair copy () { return QSlist.copy(this); }
    public QSobj nth (int idx) { return QSlist.nth(this, idx); }
    public QSpair tail (int idx) { return QSlist.tail(this, idx); }
    public QSpair end () { return QSlist.end(this); }

    /* alist. */
};




class QSnumber extends QSobj {
    public boolean isNumber () { return true; }
    public boolean isInteger() { return false; }
    public boolean isReal() { return false; }
    public boolean isRational() { return false; }
    public boolean isComplex() { return false; }
    @Override public String repr () throws Exception {
	throw new Exception("(Number base class)");
    }
};

class QSinteger extends QSnumber {
    int i;
    public QSinteger (int initi) { i = initi; }
    public Integer Integer() { return new Integer(i); }
    @Override public boolean isInteger () { return true; }
    @Override public String repr () { return Integer.toString(i); }
};

class QSreal extends QSnumber {
    double f;
    public QSreal (double initf) { f = initf; }
    public Double Double() { return new Double(f); }
    @Override public boolean isReal () { return true; }
    @Override public String repr () { return Double.toString(f); }
};

class QSrational extends QSnumber {
    int p, q;
    public QSrational (int initp, int initq) { p = initp; q = initq;
    }
//
    public int numerator () { return p; }
    public int denominator () { return q; }
    @Override public boolean isRational () { return true; }
    @Override public String repr () { return p + "/" + q; }
};

class QScomplex extends QSnumber {
    double a, b;
    public QScomplex (double inita, double initb) { a = inita; b = initb;
    }
//
    public double real () { return a; }
    public double imag () { return b; }
    @Override public String repr () { return a + "+" + b + "i"; }
};



class QScontinuation extends QSobj {
    QSobj v, e, k;
    public boolean isContinuation () { return true; }
    public QScontinuation (QSobj cv, QSobj ce, QSobj ck) {
	v = cv; e = ce; k = ck;
    }
    public QScontinuation () { v = e = k = null; }
    public QSobj V () { return v; }
    public QSobj E () { return e; }
    public QSobj K () { return k; }

    public class halt extends QScontinuation { };
    public class letk extends QScontinuation {
	public boolean isLet() { return true; }
    };
    public class callk extends QScontinuation {
	public boolean isCall() { return true; }
    };
    public class selk extends QScontinuation {
	public boolean isSel() { return true; }
    };

    public boolean isHalt () { return true; }
    public boolean isLet () { return false; }
    public boolean isCall () { return false; }
    public boolean isSel () { return false; }
};



/*
root = ( frame1 frame2 frame3 ... )
frame1 = ( ( var1 . val1 ) ( var2 . val2 ) ... )
*/
class QSenv extends QSobj {
    class QSbind extends QSpair {
	public QSbind (QSobj sym, QSobj val) { super(sym, val); }
	public QSobj sym () { return car(); }
	public QSobj val () { return cdr(); }
    };

    QSobj envroot;
    public QSenv (QSobj initenv) { envroot = initenv; }
};

