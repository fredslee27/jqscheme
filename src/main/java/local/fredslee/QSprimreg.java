package local.fredslee;

// Primitives registry.

import java.util.ArrayList;
import java.util.TreeMap;

public class QSprimreg {
    ArrayList<IQSprimitive> primlist;

    public QSprimreg () {
	primlist = new ArrayList<IQSprimitive>();
	primlist.add(null);  // reserve 0.
    }
    public int install (IQSprimitive impl) {
	int retval = primlist.size();
	primlist.add(impl);
	return retval;
    }
    // get
};
