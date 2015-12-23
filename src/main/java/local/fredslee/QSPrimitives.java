package local.fredslee;

// Primitives basis.

import java.util.ArrayList;
import java.util.TreeMap;
import java.lang.reflect.*;


interface IQSPrimitive {
    public QSobj apply (QSmachine machine, QSpair arglist);
};

class QSPrimitiveImpl implements IQSPrimitive {
    public QSobj apply (QSmachine machine, QSpair arglist) { return null; }
};


public class QSPrimitives {
/*
    static public QSobj qs_crash (QSmachine machine, QSpair arglist)
    {
	System.out.println("*** Crashing");
	System.exit(1);
	return null;
    }

    static public void blah () throws Exception {
	Class k = Class.forName("QSPrimitives");
	Method m = k.getMethod("qs_crash");
	QSPrimitives baseset = new QSPrimitives();
	m.invoke(baseset, null, null);
    };

    static public void bleh () throws Exception {
	QSPrimitiveImpl impl = new qs_crash();
	impl.apply(null,null);
    };
*/

    static public class qs_crash extends QSPrimitiveImpl {
	@Override public QSobj apply (QSmachine machine, QSpair arglist) {
	    System.out.println("*** Crashing");
	    System.exit(1);
	    return null;
	}
    };

    static public class qs_halt extends QSPrimitiveImpl {
	@Override public QSobj apply (QSmachine machine, QSpair arglist) {
	    machine.setK(null);
	    return null;
	}
    };

    static public class qs_dump extends QSPrimitiveImpl {
	@Override public QSobj apply (QSmachine machine, QSpair arglist) {
	    return null;
	};
    };



/*
    static public int install_base (QSprimreg primreg) {
	QSPrimitiveImpl[] baseset = {
	    new qs_crash(),
	    new qs_halt(),
	    new qs_dump(),
	};
	int retval = 0;
	for (QSPrimitiveImpl iter : baseset) {
	    QSprim prim = QSprim.install(iter);
	    primreg.install(prim);
	    retval++;
	}
	return retval;
    };
*/
};

