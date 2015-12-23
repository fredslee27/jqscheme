package local.fredslee;

// Primitives registry.

import java.util.ArrayList;
import java.util.TreeMap;

/*
class QSprimitiveImpl extends QSprim implements IQSprimitive {
    @Override public QSobj apply (QSmachine machine, QSobj arglist) {
	System.out.println("[Not implemented]");
	return null;
    }
};

class BasePrimitives {
    static class qs_crash extends QSprimitiveImpl {
	@Override public QSobj apply (QSmachine machine, QSobj arglist)
	{
	    System.out.println("*** Crashing");
	    System.exit(1);
	    return null;
	}
    };

    static class qs_halt extends QSprimitiveImpl {
	@Override public QSobj apply (QSmachine machine, QSobj arglist)
	{
	    machine.setK(null);
	    return null;
	}
    };

    static class qs_dump extends QSprimitiveImpl {
	@Override public QSobj apply (QSmachine machine, QSobj arglist)
	{
	    return null;
	}
    };
};
*/

public class QSprimreg {
    ArrayList<IQSprimitive> primlist;

    public QSprimreg () {
	primlist = new ArrayList<IQSprimitive>();
	primlist.add(null);  // reserve 0.
    }
    public int install (IQSprimitive impl) {
	int retval;// = primlist.size();
	primlist.add(impl);
	retval = primlist.size();
	return retval;
    }
    // get
    public IQSprimitive get (int primid)
    {
	return primlist.get(primid);
    }
    public int find (IQSprimitive prim)
    {
	for (int i = 0; i < primlist.size(); i++)
	{
	    if (primlist.get(i) == prim)
	    {
		return i;
	    }
	}
	return -1;
    }
    public IQSprimitive lookup (Class primImpl)
    {
	for (IQSprimitive iter : primlist) {
	    if (iter.getClass() == primImpl)
		return iter;
	}
	return null;
    }


/*
    public int install_base ()
    {
	int retval = 0;
	QSprimitiveImpl[] baseset = {
	    new BasePrimitives.qs_crash(),
	};
	for (IQSprimitive prim : baseset) {
	    install(prim);
	    retval++;
	}
	return retval;
    }
    */
};

