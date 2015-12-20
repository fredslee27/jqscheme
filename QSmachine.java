
/*
Scheme machine:
C - code/control
E - environment
S - store
K - continuation
*/

import java.util.ArrayList;


public class QSmachine {
    /* mapping of primitive-id to primitive method. */
    // TODO: object type for primitive.

    QSobj c;  // control
    //QSobj e;  // environment
    QSenv e;  // environment
    QSobj s;  // store
    QSobj k;  // kontinuation
    QSobj a;  // answer

/*
  public QSmachine () {
    if (prims == null)
      {
        prims = new ArrayList<Object>();
        prims.add(null);  // reserve 0.
      }
  }

  public QSobj C() { return c; }
  public QSobj E() { return e; }
  public QSobj S() { return s; }
  public QSobj K() { return k; }

  public QSobj newPrimitive (Object x) {
    int primid = prims.size();
    //QSprimitive primobj = new QSprimitive(primid);
    QSprim primobj = new QSprim(primid);
    prims.add(primobj);
    //QSobj retval = new QSobj(primobj);
    //QSobj retval = QSobj.make(primobj);
    QSobj retval = primobj;
    return retval;
  }
*/
    public QSmachine () {
	c = null;
	e = null;
	s = null;
	k = null;
	a = null;
    }

    public void setC (QSobj val) { c = val; }
    public void setE (QSobj val) { e = (QSenv)val; }
    public void setE (QSenv val) { e = val; }
    public void setS (QSobj val) { s = val; }
    public void setK (QSobj val) { k = val; }

/*
    public int applykont (QScontinuation kont, QSobj ans) {
	if (kont == null) {
	    k = null;
	    return 0;
	} else if (kont.isHalt()) {
	    k = null;
	    c = kont.c;
	} else if (kont.isLet()) {
	    if (kont.v != null) {
		// assign to variable.
		QSenv env = (QSenv)e;
		QSenvbind bind = qsenv_find(e, v, false);
		if (bind == null) {
		    // not found; new var.
		    kont.e = kont.e.freshen(kont.v);
		}
		kont.e = kont.e.bind(kont.v, ans);
		e = kont.e;
	    } else {
		// no variable to assign; restore environment.
		e = kont.e;
	    }
	} else if (kont.isCall()) {
	} else if (kont.isSel()) {
	}
	return 0;
    }

    public int cycle_applykont (QSobj ans) {
	return applykont((QScontinuation)k, ans);
    }
    public int cycle_return () {
	cycle_applykont(a);
	return 0;
    }
    */

    public void cycle () {
	if (c == null) {
	    return;
	}
	if (c.isPair()) {
	    // evaluate as code.
	} else if (c.isSymbol()) {
	    // evaluate as variable.
	} else {
	    // resolve to self.
	    a = c;
//	    cycle_return();
	}

    }
};

