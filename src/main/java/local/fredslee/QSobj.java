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
    // Generate string version of object recognizable by Scheme reader.
    public String repr () throws Exception;
};

interface IQSprimitive {
    public QSobj apply (QSobj arglist);
};

class QSobj extends Object implements IQSobj {
    static QSnull qsnull = QSnull.singleton;

    public QSobj () {
    }
    @Override public String repr () throws Exception { return "#<QSobject " + this.getClass() + ">"; }
    @Override public String toString () {
	try {
	    return repr();
	} catch (Exception e) {
	    return "{EXCEPTION " + e + "}";
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
            return "#<complex:" + n.realpart() + ":" + n.imagpart() + ">";
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

    // Type casting.
    public QSnull asNull () { return (QSnull)this; }
    public QSbool asBool () { return (QSbool)this; }
    public QSchar asChar () { return (QSchar)this; }
    public QSinteger asInteger() { return (QSinteger)this; }
    public QSreal asReal() { return (QSreal)this; }
    public QSrational asRational() { return (QSrational)this; }
    public QScomplex asComplex() { return (QScomplex)this; }
    public QSstr asStr() { return (QSstr)this; }
    public QSsym asSym() { return (QSsym)this; }
    public QSvec asVec() { return (QSvec)this; }
    public QSpair asPair() { return (QSpair)this; }
    public QScontinuation asKont() { return (QScontinuation)this; }
    public QScontinuation asContinuation() { return (QScontinuation)this; }


    // Type predicates, instance.
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


    public boolean eqp (QSobj other) { return eqp(this, other); }
    public boolean eqvp (QSobj other) { return eqvp(this, other); }
    public boolean equalp (QSobj other) { return equalp(this, other); }


    /* Type predicates, static. */
    static public boolean nullp (QSobj x) { return QSnull.p(x); }
    static public boolean booleanp (QSobj x) { return QSbool.p(x); }
    static public boolean charp (QSobj x) { return QSchar.p(x); }
    static public boolean numberp (QSobj x) { return QSnumber.p(x); }
      static public boolean integerp (QSobj x) { return QSinteger.p(x); }
      static public boolean rationalp (QSobj x) { return QSrational.p(x); }
      static public boolean realp (QSobj x) { return QSreal.p(x); }
      static public boolean complexp (QSobj x) { return QScomplex.p(x); }
    static public boolean stringp (QSobj x) { return QSstr.p(x); }
    static public boolean symbolp (QSobj x) { return QSsym.p(x); }
    static public boolean vectorp (QSobj x) { return QSvec.p(x); }
    static public boolean procp (QSobj x) { return QSproc.p(x); }
    static public boolean primp (QSobj x) { return QSprim.p(x); }
    static public boolean procedurep (QSobj x) { return (QSproc.p(x) || QSprim.p(x)); }
    static public boolean pairp (QSobj x) { return QSpair.p(x); }
    static public boolean continuationp (QSobj x) { return QScontinuation.p(x); }

    static public boolean listp (QSobj x) { return QSpair.QSlist.p(x); }


    static public boolean eqp (QSobj a, QSobj b) { return (a == b); }
    static public boolean eqvp (QSobj a, QSobj b) { return (a == b); }
    static public boolean equalp (QSobj a, QSobj b) { return (a == b); }

    // Factory functions sensitive to argument type.
    //static public QSobj make () { return new QSnull(); }
    static public QSobj make () { return QSnull.make(); }
    static public QSobj make (boolean b) { return QSbool.make(b); }
    static public QSobj make (char c) { return QSchar.make(c); }
    static public QSobj make (int i) { return QSinteger.make(i); }
    static public QSobj make (double f) { return QSreal.make(f); }
    static public QSobj make (int p, int q) { return QSrational.make(p,q); }
    static public QSobj make (double a, double b) { return QScomplex.make(a,b); }
    static public QSobj make (String s) { return QSstr.make(s); }

    static public QSsym intern (String symname) { return QSsym.make(symname); }
    static public QSobj vec (int initlen) { return QSvec.make(initlen, null); }
    static public QSobj vec (int initlen, QSobj fill) { return QSvec.make(initlen, fill); }
    static public QSpair cons (QSobj a, QSobj d) { return QSpair.make(a,d); }
    static public QSpair list (QSobj ... x) { return QSpair.QSlist.make(x); }
};


class QSnull extends QSobj {
    public static QSnull singleton = new QSnull();

    //public QSnull () { }
    private QSnull () { }
    //@Override public String repr () { return "'()"; }
    @Override public String repr () { return "()"; }

    static public boolean p (QSobj x) { return ((x == null) || (x == singleton)); }
    static public QSnull make () { return singleton; }
};

class QSbool extends QSobj {
    boolean b;
    public QSbool (boolean initb) { b = initb; }
    public QSbool (int initb) { b = (initb != 0); }
    public Boolean Boolean () { return new Boolean(b); }
    @Override public String repr () { return (b ? "#t" : "f"); }

    static public boolean p (QSobj x) { return ((x != null) && (x instanceof QSbool)); }
    static public QSbool make (boolean b) { return new QSbool(b); }
};

class QSchar extends QSobj {
    char c;
    public QSchar (char initc) { c = initc; }
    public Character Character () { return Character.valueOf(c); }
    @Override public String repr () { return "#\\" + c; }

    static public boolean p (QSobj x) { return ((x != null) && (x instanceof QSchar)); }
    static public QSchar make (char c) { return new QSchar(c); }
};

class QSstr extends QSobj {
    String s;
    public QSstr (String inits) { s = inits; }
    public String String () { return new String(s); }
    @Override public String repr () { return "\"" + s.toString() + "\""; }

    static public boolean p (QSobj x) { return ((x != null) && (x instanceof QSstr)); }
    static public QSstr make (String s) { return new QSstr(s); }
};

class QSsym extends QSobj implements Comparable<QSsym> {
    static QSsymtree symtree = new QSsymtree();

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

    // Comparable.compareTo
    @Override public int compareTo (QSsym other) { return (other._symid - _symid); }

    static public boolean p (QSobj x) { return ((x != null) && (x instanceof QSsym)); }
    static public QSsym make (String symname)
    {
	return symtree.intern(symname);
    }
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

    static public boolean p (QSobj x) { return ((x != null) && (x instanceof QSvec)); }
    static public QSvec make (int k) { return new QSvec(k); }
    static public QSvec make (int k, QSobj x) { return new QSvec(k, x); }
    static public QSvec make (QSobj ... elts)
    {
	QSvec retval = new QSvec(elts.length, null);
	for (int i = 0; i < elts.length; i++) {
	    retval.setq(i, elts[i]);
	}
	return retval;
    }
}


class QSprim extends QSobj {
    static QSprimreg primreg = new QSprimreg();

    static public boolean p (QSobj x) { return ((x != null) && (x instanceof QSprim)); }
};

class QSproc extends QSobj {
    static public boolean p (QSobj x) { return ((x != null) && (x instanceof QSproc)); }
};

class QSpair extends QSobj {
    // Guard value to let car and cdr on non-pair return recursible value.
    static public final QSpair guard = new QSpair(null,null);

    QSobj a, d;

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
    // Pairs implementing List.
    static class QSlist {
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
	// isList()
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
	// shallow copy.
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
	// Return final cons of a list.
	static QSpair end (QSpair root) {
	    QSpair iter = root;
	    if (iter == null)
		return null;
	    while ((iter.cdr() != null) && QSpair.p(iter)) {
		iter = (QSpair)(iter.cdr());
	    }
	    return iter;
	};
	// Return (sub)list starting from nth cons cell; may return null.
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
	// Return car of nth cons cells.
	static QSobj nth (QSpair root, int idx) {
	    QSpair sublist = tail(root, idx);
	    if (sublist != null)
		return sublist.car();
	    return null;
	};
    };
    //QSlist list = null;
    //protected QSpair pair () { return this; }

    // Pairs implementing association list.
    static class QSalist {
	// alist := ( ASSOC1 ASSOC2 ASSOC3 ... )
	// assoc := ( key. value )
	public static boolean p (QSobj root) {
	    if (!QSpair.p(root)) return false;
	    QSpair iter = (QSpair)root;
	    for (; iter != null; iter = nextpair(iter)) {
		if (! (QSpair.p(iter.car())))
		    return false;
	    };
	    return true;
	};
	public static QSpair insert (QSpair alist, QSobj k, QSobj v) {
	    QSpair node = QSobj.cons(k, v);
	    QSpair retval = QSobj.cons(node, alist);
	    return retval;
	}
	public static QSpair make (QSobj firstkey, QSobj firstvalue) {
	    return insert(null, firstkey, firstvalue);
	};
	// Returns the assoc node.
	public static QSpair assoc (QSobj alist, QSobj key) {
	    return null;
	}
    };

    public QSpair (QSobj inita, QSobj initd) { build(inita, initd); }
    public QSpair (QSobj inita) { build(inita, null); }
    protected void build (QSobj inita, QSobj initd)
    {
	a = inita;
	d = initd;
	//list = new QSlist();
    }
    /*
    public QSobj car () { return a; }
    public QSobj cdr () { return d; }
    void setcar (QSobj v) { if (true) a = v; }
    void setcdr (QSobj v) { if (true) d = v; }
    */
    public QSobj car () { return car(this); }
    public QSobj cdr () { return cdr(this); }
    public void setcar (QSobj v) { setcar(this, v); }
    public void setcdr (QSobj v) { setcdr(this, v); }
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


    static public boolean p (QSobj root) { return (root instanceof QSpair); }
    static public boolean listp (QSobj root) { return (QSpair.p(root) && QSlist.p((QSpair)root)); }
    static public QSpair make (QSobj a, QSobj d) { return new QSpair(a,d); }

    static public QSobj car (QSobj x) { return (QSpair.p(x) ? ((QSpair)x).a : null); }
    static public QSobj cdr (QSobj x) { return (QSpair.p(x) ? ((QSpair)x).d : null); }
    static public QSobj setcar (QSobj x, QSobj v)
    {
	QSpair xx = (QSpair.p(x) ? (QSpair)x : null);
	if (xx == null) return null;
	if (true) {
	    xx.a = v;
	}
	return null;
    }
    static public QSobj setcdr (QSobj x, QSobj v)
    {
	QSpair xx = (QSpair.p(x) ? (QSpair)x : null);
	if (xx == null) return null;
	if (true) {
	    xx.d = v;
	}
	return null;
    }
};




class QSnumber extends QSobj {
    // guards
    static public QSnumber NaN = new QSnumber();
    static public QSnumber inf = new QSnumber();

    enum NumCmp {
	NC,  // not comparable.
	LT,
	EQ,
	GT,
    };
    public boolean isNumber () { return true; }
    public boolean isInteger() { return false; }
    public boolean isReal() { return false; }
    public boolean isRational() { return false; }
    public boolean isComplex() { return false; }
    public boolean isNaN () { return false; }
    public boolean isInf () { return false; }
    @Override public String repr () throws Exception {
	throw new Exception("(Number base class)");
    }

    public QSinteger asInt () { return (QSinteger)this; }
    public QSreal asReal () { return (QSreal)this; }
    public QSrational asRational() { return (QSrational)this; }
    public QScomplex asComplex() { return (QScomplex)this; }

    static public boolean p (QSobj x) { return ((x != null) && (x instanceof QSnumber)); }
    static public QSnumber make (int i) { return new QSinteger(i); }
    static public QSnumber make (double f) { return new QSreal(f); }
    static public QSnumber make (int p, int q) { return new QSrational(p,q); }
    static public QSnumber make (double a, double b) { return new QScomplex(a,b); }
    static public QSnumber nan () { return NaN; }


    static public NumCmp sign (QSobj a)
    {
	if (QSinteger.p(a)) return ((QSinteger)a).sign();
	else if (QSreal.p(a)) return ((QSreal)a).sign();
	else if (QSrational.p(a)) return ((QSrational)a).sign();
	else if (QScomplex.p(a)) return ((QScomplex)a).sign();
	return NumCmp.NC;
    }
    static public NumCmp cmp (QSnumber a, QSnumber b)
    {
	QSnumber test = QSarith.sub(a,b);
	NumCmp retval = sign(test);
	return retval;
	//return NumCmp.NC;
    }
};

class QSinteger extends QSnumber {
    int i;
    public QSinteger (int initi) { i = initi; }
    public Integer Integer() { return new Integer(i); }
    public NumCmp sign () { if (i < 0) return NumCmp.LT; else if (i > 0) return NumCmp.GT; else return NumCmp.EQ; }
    @Override public boolean isInteger () { return true; }
    @Override public String repr () { return Integer.toString(i); }

    static public boolean p (QSobj x) { return ((x != null) && (x instanceof QSinteger)); }
    static public QSinteger make (int i) { return new QSinteger(i); }
    static public boolean eqvp (QSobj a, QSobj b)
    {
	// baseline comparison.
	QSinteger aa = QSinteger.p(a) ? (QSinteger)a : null;
	QSinteger bb = QSinteger.p(b) ? (QSinteger)b : null;
	if ((aa == null) || (b == null)) return false;
	return (aa.i == bb.i);
    }
    static public boolean eqp (QSobj a, QSobj b)
    {
	// strictest comparison.
	return eqvp(a,b);
    }
    static public boolean equalp (QSobj a, QSobj b)
    {
	// loosest comparison.
	return false;
    }
};

class QSreal extends QSnumber {
    double f;
    public QSreal (double initf) { f = initf; }
    public Double Double() { return new Double(f); }
    public NumCmp sign () { if (f < 0) return NumCmp.LT; else if (f > 0) return NumCmp.GT; else return NumCmp.EQ; }
    @Override public boolean isReal () { return true; }
    @Override public String repr () { return Double.toString(f); }

    static public boolean p (QSobj x) { return ((x != null) && (x instanceof QSreal)); }
    static public QSreal make (double f) { return new QSreal(f); }
};

class QSrational extends QSnumber {
    int p, q;
    public QSrational (int initp, int initq) { p = initp; q = initq; }
    public Integer[] Integers() { return new Integer[]{numerator(), denominator()}; }
    public int numerator () { return p; }
    public int denominator () { return q; }
    public NumCmp sign () {
	int align = p * q;
	if (align < 0) return NumCmp.LT;
	else if (align > 0) return NumCmp.GT;
	else return NumCmp.EQ;
    }
    @Override public boolean isRational () { return true; }
    @Override public String repr () { return p + "/" + q; }

    static public boolean p (QSobj x) { return ((x != null) && (x instanceof QSrational)); }
    static public QSrational make (int p, int q) { return new QSrational(p,q); }
};

class QScomplex extends QSnumber {
    double a, b;
    public QScomplex (double inita, double initb) { a = inita; b = initb;
    }
    public Double[] Doubles() { return new Double[]{realpart(),imagpart()}; }
    public NumCmp sign () {
	if ((a == 0) && (b == 0)) return NumCmp.EQ;
	else return NumCmp.NC;
    }
    public double realpart () { return a; }
    public double imagpart () { return b; }
    public QScomplex conjugate () { return QScomplex.make(a, -b); }
    @Override public String repr () { return a + "+" + b + "i"; }

    static public boolean p (QSobj x) { return ((x != null) && (x instanceof QScomplex)); }
    static public QScomplex make (double a, double b) { return new QScomplex(a,b); }
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

    static public boolean p (QSobj x) { return ((x != null) && (x instanceof QScontinuation)); }
    static public QScontinuation make (QSobj v, QSobj e, QSobj k)
    {
	return new QScontinuation(v,e,k);
    }
};



/*
root = ( frame1 frame2 frame3 ... )
frame1 = ( ( var1 . val1 ) ( var2 . val2 ) ... )
*/
class QSenv extends QSobj {
    public static QSobj blackhole = new QSobj();
    ArrayList< TreeMap<QSsym,QSobj> > env;
    //QSobj envroot;

    public QSenv () {
	env = new ArrayList< TreeMap<QSsym,QSobj> >();
	newframe();
	//envroot = initenv
    }
    public QSenv (QSenv closure) {
	env = (ArrayList< TreeMap<QSsym,QSobj> >)closure.env.clone();
	newframe();
    }
    public TreeMap<QSsym,QSobj> newframe ()
    {
	TreeMap<QSsym,QSobj> frame = new TreeMap<QSsym,QSobj>();
	env.add(0, frame);
	return frame;
    }
    // Returns bound value of symbol.
    public QSobj resolve (QSsym sym, boolean recurse)
    {
	QSobj retval = null;
	boolean found = false;
	for (TreeMap<QSsym,QSobj> frameiter : env) {
	    if (frameiter.containsKey(sym)) {
		retval = frameiter.get(sym);
		return retval;
	    }
	    if (! recurse) {
		// TODO: throw exception.
		return null;
	    }
	}
	// TODO: throw exception.
	return null;
    }
    // Ensure an uninitialized binding exists in current env frame.
    public QSobj freshen (QSsym sym)
    {
	TreeMap<QSsym,QSobj> frame;
	if (env.size() == 0)
	{
	    frame = newframe();
	}
	else
	{
	    frame = env.get(0);
	    if (frame.containsKey(sym))
	    {
		// then create new frame to ensure shadowing.
		frame = newframe();
	    }
	}
	frame.put(sym, blackhole);
	return null;
    }
    public QSobj bind (QSsym sym, QSobj val)
    {
	TreeMap<QSsym,QSobj> frame;
	if (env.size() == 0)
	{
	    return null;
	}
	else
	{
	    frame = env.get(0);
	    if (frame.containsKey(sym))
	    {
		frame.put(sym, val);
	    }
	    else
	    {
		return null;
	    }
	}
	return null;
    }
    @Override public String toString ()
    {
	StringBuilder ss = new StringBuilder();
	ss.append("(");
	for (TreeMap<QSsym,QSobj> frameiter : env) {
	    ss.append("(");
	    for (QSsym symiter : frameiter.keySet()) {
	        ss.append("(");
		ss.append(symiter.repr());
		ss.append(" . ");
		ss.append(frameiter.get(symiter));
		ss.append(")");
	    }
	    ss.append(")");
	}
	ss.append(")");
	return ss.toString();
    }
};

