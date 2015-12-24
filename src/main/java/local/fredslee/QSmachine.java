package local.fredslee;

/*
Scheme machine:
C - code/control
E - environment
S - store
K - continuation
*/

import java.util.ArrayList;
import java.util.TreeMap;


public class QSmachine {
    /* mapping of primitive-id to primitive method. */
    // TODO: object type for primitive.
    boolean halt;

    QSobj c;  // control
    //QSobj e;  // environment
    QSenv e;  // environment
    QSobj s;  // store
    //QSobj k;  // kontinuation
    QScontinuation k;  // kontinuation
    QSobj a;  // answer

    interface RuleHandler {
	public int handle (QSpair obj);
    };
    TreeMap<QSsym, RuleHandler> ruleset;

    // Primitives.
    // Directly modify machine state.
    // Evaluation results (Atomic values, Answers) go into 'A' (setA(...)).
    // Return value... TODO: decide what to return
    class Primitives {
	class qs_crash extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		System.out.println("*** Crashing");
		System.exit(1);
		return null;
	    }
	};
	class qs_halt extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		setK(null);
		return null;
	    }
	};
	class qs_dump extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		System.out.println("DUMP:");
		String repr = (arglist.car()).dump();
		System.out.println(repr);
		System.out.println(".");
		setA(null);
		return null;
	    }
	};

	class qs_null_p extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		QSobj a0 = QSpair.QSlist.nth(arglist, 0);
		QSobj ans = QSobj.make( QSobj.nullp(a0) );
		setA(ans);
		return null;
	    };
	};
	class qs_eq_p extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_boolean_p extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		QSobj a0 = QSpair.QSlist.nth(arglist, 0);
		QSobj ans = QSobj.make( QSobj.booleanp(a0) );
		setA(ans);
		return null;
	    };
	};
	class qs_pair_p extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		QSobj a0 = QSpair.QSlist.nth(arglist, 0);
		QSobj ans = QSobj.make( QSobj.pairp(a0) );
		setA(ans);
		return null;
	    };
	};
	class qs_pair_make extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_pair_getcar extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_pair_getcdr extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_pair_setcar extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_pair_setcdr extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_symbol_p extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_symbol_getstring extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_symbol_getid extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_symbol_make extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_string_p extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		QSobj a0 = QSpair.QSlist.nth(arglist, 0);
		QSobj ans = QSobj.make( QSobj.stringp(a0) );
		setA(ans);
		return null;
	    };
	};
	class qs_string_make extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_string_getlen extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_string_getch extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_string_setch extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_string_copy extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_string_slice extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_string_cmp extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_string_explode extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_string_implode extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};

	class qs_bytevec_p extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_bytevec_make extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_bytevec_getlen extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_bytevec_getelt extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_bytevec_setelt extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_bytevec_copyq extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};

	class qs_vector_p extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		QSobj a0 = QSpair.QSlist.nth(arglist, 0);
		QSobj ans = QSobj.make( QSobj.vectorp(a0) );
		setA(ans);
		return null;
	    };
	};
	class qs_vector_make extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_vector_getlen extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_vector_getlet extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_vector_setelt extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};

	class qs_procedure_p extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		QSobj a0 = QSpair.QSlist.nth(arglist, 0);
		QSobj ans = QSobj.make( QSobj.procedurep(a0) );
		setA(ans);
		return null;
	    };
	};
	class qs_procedure_getparms extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_procedure_getbody extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_procedure_getenv extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_procedure_apply extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};

	class qs_char_p extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		QSobj a0 = QSpair.QSlist.nth(arglist, 0);
		QSobj ans = QSobj.make( QSobj.charp(a0) );
		setA(ans);
		return null;
	    };
	};
	class qs_char_make extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_char_getcodepoint extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};

	class qs_continuation_p extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		QSobj a0 = QSpair.QSlist.nth(arglist, 0);
		QSobj ans = QSobj.make( QSobj.continuationp(a0) );
		setA(ans);
		return null;
	    };
	};

	class qs_primitive_p extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		QSobj a0 = QSpair.QSlist.nth(arglist, 0);
		QSobj ans = QSobj.make( QSobj.primp(a0) );
		setA(ans);
		return null;
	    };
	};

	class qs_list_p extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		QSobj a0 = QSpair.QSlist.nth(arglist, 0);
		QSobj ans = QSobj.make( QSobj.listp(a0) );
		setA(ans);
		return null;
	    };
	};
	class qs_list extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_list_reverse extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_list_length extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_list_getelt extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_list_end extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_list_tail extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};

	class qs_number_p extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		QSobj a0 = QSpair.QSlist.nth(arglist, 0);
		QSobj ans = QSobj.make( QSobj.numberp(a0) );
		setA(ans);
		return null;
	    };
	};
	class qs_cmp extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_add extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_sub extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_mul extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_div extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_mod extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_int_p extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		QSobj a0 = QSpair.QSlist.nth(arglist, 0);
		QSobj ans = QSobj.make( QSobj.integerp(a0) );
		setA(ans);
		return null;
	    };
	};
	class qs_real_p extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		QSobj a0 = QSpair.QSlist.nth(arglist, 0);
		QSobj ans = QSobj.make( QSobj.realp(a0) );
		setA(ans);
		return null;
	    };
	};
	class qs_rational_p extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		QSobj a0 = QSpair.QSlist.nth(arglist, 0);
		QSobj ans = QSobj.make( QSobj.rationalp(a0) );
		setA(ans);
		return null;
	    };
	};
	class qs_complex_p extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		QSobj a0 = QSpair.QSlist.nth(arglist, 0);
		QSobj ans = QSobj.make( QSobj.complexp(a0) );
		setA(ans);
		return null;
	    };
	};
	class qs_inexact_p extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_exact_p extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_to_int extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_to_real extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_to_rational extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_to_complex extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_to_inexact extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_to_exact extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_floor extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_ceil extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_truncate extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_round extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_make_rectangular extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_make_polar extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_real_part extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_imag_part extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_magnitude extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_angle extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_min extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_max extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};

	class qs_exp extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_log extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_sin extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_cos extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_tan extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_asin extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_acos extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_atan extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_atan2 extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_sqrt extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};
	class qs_expt extends QSprim {
	    @Override public QSobj apply (QSpair arglist) {
		return null;
	    };
	};

	QSprim crash;
	QSprim halt;
	QSprim dump;

	QSprim null_p;

	QSprim eq_p;

	QSprim boolean_p;

	QSprim pair_p;
	QSprim pair_make;
	QSprim pair_getcar;
	QSprim pair_getcdr;
	QSprim pair_setcar;
	QSprim pair_setcdr;

	QSprim symbol_p;
	QSprim symbol_getstring;
	QSprim symbol_getid;
	QSprim symbol_make;

	QSprim string_p;
	QSprim string_make;
	QSprim string_getlen;
	QSprim string_getch;
	QSprim string_setch;
	QSprim string_copy;
	QSprim string_slice;
	QSprim string_cmp;
	QSprim string_explode;
	QSprim string_implode;

	QSprim vector_p;
	QSprim vector_make;
	QSprim vector_getlen;
	QSprim vector_getlet;
	QSprim vector_setelt;

	QSprim procedure_p;
	QSprim procedure_getparms;
	QSprim procedure_getbody;
	QSprim procedure_getenv;
	QSprim procedure_apply;

	QSprim char_p;
	QSprim char_make;
	QSprim char_getcodepoint;

	QSprim continuation_p;

	QSprim primitive_p;

	QSprim list_p;
	QSprim list;
	QSprim list_reverse;
	QSprim list_length;
	QSprim list_getelt;
	QSprim list_end;
	QSprim list_tail;

	QSprim number_p;
	QSprim cmp;
	QSprim add;
	QSprim sub;
	QSprim mul;
	QSprim div;
	QSprim mod;
	QSprim int_p;
	QSprim real_p;
	QSprim rational_p;
	QSprim complex_p;
	QSprim inexact_p;
	QSprim exact_p;
	QSprim to_int;
	QSprim to_real;
	QSprim to_rational;
	QSprim to_complex;
	QSprim to_inexact;
	QSprim to_exact;
	QSprim floor;
	QSprim ceil;
	QSprim truncate;
	QSprim round;
	QSprim make_rectangular;
	QSprim make_polar;
	QSprim real_part;
	QSprim imag_part;
	QSprim magnitude;
	QSprim angle;
	QSprim min;
	QSprim max;
	QSprim exp;
	QSprim log;
	QSprim sin;
	QSprim cos;
	QSprim tan;
	QSprim asin;
	QSprim acos;
	QSprim atan;
	QSprim atan2;
	QSprim sqrt;
	QSprim expt;

	// omg, so much redundancy...
	public Primitives () {
	    crash = new qs_crash();
	    halt = new qs_halt();
	    dump = new qs_dump();
	    null_p = new qs_null_p();
	    eq_p = new qs_eq_p();
	    boolean_p = new qs_boolean_p();
	    pair_p = new qs_pair_p();
	    pair_make = new qs_pair_make();
	    pair_getcar = new qs_pair_getcar();
	    pair_getcdr = new qs_pair_getcdr();
	    pair_setcar = new qs_pair_setcar();
	    pair_setcdr = new qs_pair_setcdr();
	    symbol_p = new qs_symbol_p();
	    symbol_getstring = new qs_symbol_getstring();
	    symbol_getid = new qs_symbol_getid();
	    symbol_make = new qs_symbol_make();
	    string_p = new qs_string_p();
	    string_make = new qs_string_make();
	    string_getlen = new qs_string_getlen();
	    string_getch = new qs_string_getch();
	    string_setch = new qs_string_setch();
	    string_copy = new qs_string_copy();
	    string_slice = new qs_string_slice();
	    string_cmp = new qs_string_cmp();
	    string_explode = new qs_string_explode();
	    string_implode = new qs_string_implode();
	    vector_p = new qs_vector_p();
	    vector_make = new qs_vector_make();
	    vector_getlen = new qs_vector_getlen();
	    vector_getlet = new qs_vector_getlet();
	    vector_setelt = new qs_vector_setelt();
	    procedure_p = new qs_procedure_p();
	    procedure_getparms = new qs_procedure_getparms();
	    procedure_getbody = new qs_procedure_getbody();
	    procedure_getenv = new qs_procedure_getenv();
	    procedure_apply = new qs_procedure_apply();
	    char_p = new qs_char_p();
	    char_make = new qs_char_make();
	    char_getcodepoint = new qs_char_getcodepoint();
	    continuation_p = new qs_continuation_p();
	    primitive_p = new qs_primitive_p();
	    list_p = new qs_list_p();
	    list = new qs_list();
	    list_reverse = new qs_list_reverse();
	    list_length = new qs_list_length();
	    list_getelt = new qs_list_getelt();
	    list_end = new qs_list_end();
	    list_tail = new qs_list_tail();
	    number_p = new qs_number_p();
	    cmp = new qs_cmp();
	    add = new qs_add();
	    sub = new qs_sub();
	    mul = new qs_mul();
	    div = new qs_div();
	    mod = new qs_mod();
	    int_p = new qs_int_p();
	    real_p = new qs_real_p();
	    rational_p = new qs_rational_p();
	    complex_p = new qs_complex_p();
	    inexact_p = new qs_inexact_p();
	    exact_p = new qs_exact_p();
	    to_int = new qs_to_int();
	    to_real = new qs_to_real();
	    to_rational = new qs_to_rational();
	    to_complex = new qs_to_complex();
	    to_inexact = new qs_to_inexact();
	    to_exact = new qs_to_exact();
	    floor = new qs_floor();
	    ceil = new qs_ceil();
	    truncate = new qs_truncate();
	    round = new qs_round();
	    make_rectangular = new qs_make_rectangular();
	    make_polar = new qs_make_polar();
	    real_part = new qs_real_part();
	    imag_part = new qs_imag_part();
	    magnitude = new qs_magnitude();
	    angle = new qs_angle();
	    min = new qs_min();
	    max = new qs_max();
	    exp = new qs_exp();
	    log = new qs_log();
	    sin = new qs_sin();
	    cos = new qs_cos();
	    tan = new qs_tan();
	    asin = new qs_asin();
	    acos = new qs_acos();
	    atan = new qs_atan();
	    atan2 = new qs_atan2();
	    sqrt = new qs_sqrt();
	    expt = new qs_expt();
	}
    };
    Primitives primitives;

    /*
    Special Rules.
    Handles special forms, evaluating lists starting with a specific symbol.
    */
    class SpecialRuleSet extends TreeMap<QSsym,RuleHandler> {
	class rule_quote implements RuleHandler {
	    public int handle (QSpair arglist) {
		return 0;
	    };
	};
	class rule_define implements RuleHandler {
	    public int handle (QSpair arglist) {
		// arglist <- ( sym value )
		// arglist <- ( ( sym . parmlist ) body )
		QSobj a0 = QSpair.QSlist.nth(arglist, 0);
		QSobj a1 = QSpair.QSlist.nth(arglist, 1);

		if (QSobj.pairp(a0))
		{
		    // (define (sym . parmlist) body)
		    QSpair parenform = (QSpair)a0;
		    QSsym sym = (QSsym)(parenform.car());
		    QSpair parm = (QSpair)(parenform.cdr());
		    QSobj val = QSobj.lambda(parm, a1, E());
		    QScontinuation kont = K();
		    //QSobj val = QSpair.list(QSobj.intern("lambda"), parm, body);
		    if (! E().exists(sym, false))
		    {
			E().freshen(sym);
		    }
		    QScontinuation k = new QSletk(sym, null, E(), K());
		    setC(val);  // bare lambda evaluates to self, then store into sym.
		    setK(k);
		}
		else
		{
		    // (define sym value)
		    QSsym sym = (QSsym)a0;
		    QScontinuation k = new QSletk(sym, null, E(), K());
		    E().freshen(sym);
		    setC(a1);  // evaluate the expression, then storeinto sym.
		    setK(k);
		}
		return 0;
	    };
	};
	class rule_lambda implements RuleHandler {
	    public int handle (QSpair arglist) {
		// arglist <- ( parameters:list . body:list )
		QSpair parmlist = (QSpair)(arglist.car());
		QSobj body = (QSpair)(arglist.cdr());
		QSenv env = E();
		QSobj ans = QSobj.lambda(parmlist, body, env);
		setA(ans);
		return 0;
	    };
	};
	class rule_setq implements RuleHandler {
	    public int handle (QSpair arglist) {
		return 0;
	    };
	};
	class rule_let implements RuleHandler {
	    public int handle (QSpair arglist) {
		return 0;
	    };
	};
	class rule_if implements RuleHandler {
	    public int handle (QSpair arglist) {
		// arglist <- ( testing consequent alternate )
		// arglist <- ( testing consequent )
		QSobj testing = QSpair.QSlist.nth(arglist, 0);
		QSobj consequent = QSpair.QSlist.nth(arglist, 1);
		QSobj alternate = QSpair.QSlist.nth(arglist, 2);  // null if short, is fine.
		QSenv env = E();
		QScontinuation future = K();
		QScontinuation selk = new QSselk(consequent, alternate, env, future);
		// First, evaluate the testing condition.
		setC(testing);
		// Then after evaluation, pick one branch of 'if'.
		setK(selk);
		return 0;
	    };
	};
	class rule_call_cc implements RuleHandler {
	    public int handle (QSpair arglist) {
		return 0;
	    };
	};
	class rule_begin implements RuleHandler {
	    public int handle (QSpair arglist) {
		return 0;
	    };
	};
	class rule_or implements RuleHandler {
	    public int handle (QSpair arglist) {
		return 0;
	    };
	};
	class rule_and implements RuleHandler {
	    public int handle (QSpair arglist) {
		return 0;
	    };
	};

	public SpecialRuleSet () {
	    super();
	    QSmachine m = QSmachine.this;
	    this.put(QSobj.intern("quote"), new rule_quote());
	    this.put(QSobj.intern("define"), new rule_define());
	    this.put(QSobj.intern("lambda"), new rule_lambda());
	    this.put(QSobj.intern("setq"), new rule_setq());
	    this.put(QSobj.intern("let"), new rule_let());
	    this.put(QSobj.intern("if"), new rule_if());
	    this.put(QSobj.intern("call/cc"), new rule_call_cc());
	    this.put(QSobj.intern("call-with-current-continuation"), new rule_call_cc());
	    this.put(QSobj.intern("begin"), new rule_begin());
	    this.put(QSobj.intern("or"), new rule_or());
	    this.put(QSobj.intern("and"), new rule_and());
	};
    };
    SpecialRuleSet specialruleset;

    public QSmachine () {
	halt = false;
	c = null;
	e = null;
	s = null;
	k = null;
	a = null;
	primitives = new Primitives();
	specialruleset = new SpecialRuleSet();
    }

    public QSmachine getMachine () { return this; }
    public void setC (QSobj val) { c = val; }
    public void setE (QSobj val) { e = (QSenv)val; }
    public void setE (QSenv val) { e = val; }
    public void setS (QSobj val) { s = val; }
    public void setK (QSobj val) { k = (QScontinuation)val; }
    public void setK (QScontinuation val) { k = val; }
    public void setA (QSobj val) { a = val; }

    public QSobj C () { return c; }
    public QSenv E () { return e; }
    public QSobj S () { return s; }
    public QScontinuation K () { return k; }
    public QSobj A () { return a; }

    public void reset ()
    {
	c = null;
	//e = QSenv.standard();
	//e = null;
	e = new QSenv();
	s = null;
	k = null;
	//k = new QShaltk();
	a = null;
    }

    // implicit apply kontinuation.
    public int applyK ()
    {
	int retval = 0;
	if (k instanceof QScontinuation)
	{
	    retval = k.applykont(this);
	}
	return retval;
    }
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

    */

    public int cycle_applykont (QSobj ans) {
	//return applykont((QScontinuation)k, ans);
	int retval = 0;
	if (k == null)
	{
	    halt = true;
	}
	else
	{
	    // TODO: explicit answer argument.
	    retval = k.applykont(this);
	}
	return retval;
    }

    public int cycle_pair ()
    {
	int retval = 0;
	QSpair arglist = (QSpair)c;
	QSobj head = arglist.car();
	QSpair rest = (QSpair)(arglist.cdr());
	RuleHandler specialrule = null;
	if (QSobj.symbolp(head))
	    specialrule = specialruleset.get((QSsym)head);
	if (specialrule != null)
	{
	    retval = specialrule.handle(rest);
	}
	else if (QSobj.primp(head))
	{
	    // apply machine primitive.
	    QSprim prim = (QSprim)head;
	    prim.apply(rest);
	    retval = 0;
	}
	else if (QSobj.procp(head))
	{
	    // apply procedure.
	    // cycle_applyproc();
	}
	else if (QSobj.continuationp(head))
	{
	    QSobj val = ((QSpair)(arglist.cdr())).car();
	    // applykont(head, val);
	    k = (QScontinuation)head;
	    applyK();
	}
	else
	{
	    // interpret as procedure call.
	    // cycle_proc()
	    QSpair args = (QSpair)rest;
	    QSobj first = null;
	    if (args != null) {
		first = args.car();
		rest = (QSpair)(args.cdr());
	    } else {
		first = null;
		rest = null;
	    }
	    QScontinuation k = new QScallk(rest, (QSpair)null, E(), K());
	    setC(first);
	    setE(E());
	    setK(k);
	}
	return retval;
    };

    public int cycle_return ()
    {
	cycle_applykont(a);
	return 0;
    }

    public void cycle ()
    {
	if (halt)
	    return;
	if (c == null) {
	    cycle_return();
	} else if (c.isPair()) {
	    // evaluate as code.
	    cycle_pair();
	} else if (c.isSymbol()) {
	    // evaluate as variable.
	    a = e.resolve(c.asSym(), true);
	    cycle_return();
	} else {
	    // resolve to self.
	    a = c;
	    cycle_return();
	}
    }

    void limited_run (int lim)
    {
	int i;
	halt = false;
	for (i = 0; (!halt) && (i < lim); i++)
	{
	    System.out.println(this);
	    cycle();
	}
	System.out.println(this);
	System.out.println("HALT after " + i + " cycles");
    }


    // extend env current frame with primitives bindings.
/*
    public void populate_prims (QSenv env)
    {
	env.define(QSobj.intern("__qsprim:dump__"), primitives.dump);
    }
    */



    public String toString () {
	StringBuilder ss = new StringBuilder();
	ss.append("MACHINE{\n");
	ss.append(" C="+c+"\n");
	ss.append(" E="+e+"\n");
	ss.append(" K="+k+"\n");
	ss.append(" A="+a+"\n");
	ss.append("}\n");
	return ss.toString();
    }
};

