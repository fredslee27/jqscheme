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
    static QSsymtree symtree;
    static QSprimreg primreg;

    public QSobj () {
        if (symtree == null) {
            //symtree = new TreeSet<QSsym>();
            //symtree = new TreeMap<String,QSsym>();
	    symtree = new QSsymtree();
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

    public boolean isNull () { return (this instanceof QSnull); }
    public boolean isBoolean () { return (this instanceof QSbool); }
    public boolean isCharacter () { return (this instanceof QSchar); }
    public boolean isNumber () { return (this instanceof QSnumber); }
    public boolean isVector () { return (this instanceof QSvec); }
    public boolean isString () { return (this instanceof QSstr); }
    public boolean isSymbol () { return (this instanceof QSsym); }
    public boolean isPrimitive () { return (this instanceof QSprim); }
    public boolean isProcedure () { return (this instanceof QSproc); }
    public boolean isPair() { return (this instanceof QSpair); }

    static public QSsym intern (String symname) {
	return symtree.intern(symname);
    }
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
};


class QSnull extends QSobj {
    public QSnull () { }
    @Override public String repr () { return "'()"; }
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
    @Override public String repr () { return "\\#" + c; }
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
        return "#<vector " + data.length + ">";
    }
}


class QSprim extends QSobj {
};

class QSproc extends QSobj {
};

class QSpair extends QSobj {
    QSobj a, d;
    public QSpair (QSobj inita, QSobj initd) {
        a = inita;
        d = initd;
    }
    public QSobj car () {
        return a;
    }
    public QSobj cdr () {
        return d;
    }
    void setcar (QSobj v) {
        if (true) a = v;
    }
    void setcdr (QSobj v) {
        if (true) d = v;
    }
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
    public QSobj V () { return v; }
    public QSobj E () { return e; }
    public QSobj K () { return k; }
};


