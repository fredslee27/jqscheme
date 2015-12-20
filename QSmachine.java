
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
  static ArrayList<Object> prims = null;

  QSobj c;
  QSobj e;
  QSobj s;
  QSobj k;

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
};

