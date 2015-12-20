
/* Collection of all symbols, help assign unique symbol id. */

import java.util.Map;
import java.util.TreeMap;

public class QSsymtree {
    Map<String,QSsym> by_name;
    Map<Integer,QSsym> by_id;
    int symcount;  // monotonic increasing to assign unique symbol id.

    public QSsymtree () {
	by_name = new TreeMap<String,QSsym>();
	by_id = new TreeMap<Integer,QSsym>();
	symcount = 1;  // reserve 0.
    }
    public QSsym intern (String s) {
	QSsym retval = by_name.get(s);
	if (retval == null) {
	    retval = new QSsym(s, symcount);
	    by_name.put(s, retval);
	    by_id.put(symcount, retval);
	    symcount++;
	}
	return retval;
    }
    public QSsym get (String s) {
	QSsym retval = by_name.get(s);
	return retval;
    }
    public QSsym get (int symid) {
	QSsym retval = by_id.get(symid);
	return retval;
    }
};
