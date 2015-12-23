package local.fredslee;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class TestKont
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TestKont (String testName)
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TestKont.class );
    }

    public void apply (QSobj qsobj, String expect)
    {
	String repr = qsobj.toString();
	System.out.println("obj=" + repr + " vs expect=" + expect + " => " + expect.equals(repr));
	assertTrue(expect.equals(repr));
    }

    /**
     * 
     */
    public void testQScheme()
    {
        //assertTrue( true );
	System.out.println("Testing kontinuations");

	QSobj qsobj;
	String repr, expect;
	QScontinuation kont;
	QSenv env;

	System.out.println("# letk");
	QSmachine machine = new QSmachine();
	env = new QSenv();
	kont = new QSletk(QSobj.intern("foo"), null, env, null);
	machine.setE(env);
	machine.setK(kont);
	machine.setA(QSobj.make(42));
	machine.applyK();
	System.out.println("env=" + machine.E());
	apply(machine.E(), "(((foo . 42)))");

	System.out.println("# callk");
	machine.reset();
	QSpair arglist0 = QSpair.QSlist.make(QSobj.make(10), QSobj.make(20), QSobj.make(30));
	machine.setA(QSobj.make(42));
	env = new QSenv();
	kont = new QScallk(arglist0, null, env, null);
	machine.setC(null);
	machine.setE(env);
	machine.setK(kont);
	machine.setA(QSobj.make(123));
	machine.applyK();
	machine.setA(machine.C());
	machine.applyK();
	machine.setA(machine.C());
	machine.applyK();
	machine.setA(machine.C());
	machine.applyK();
	System.out.println("c=" + machine.C());
	apply(machine.C(), "(123 10 20 30)");

	System.out.println("# selk");
	machine.reset();
	env = new QSenv();
	kont = new QSselk(QSobj.make(17), QSobj.make(42), env, null);
	machine.setA(QSobj.make(true));
	machine.setC(null);
	machine.setE(env);
	machine.setK(kont);
	machine.applyK();
	System.out.println("c=" + machine.C());
	apply(machine.C(), "17");

	machine.reset();
	env = new QSenv();
	kont = new QSselk(QSobj.make(17), QSobj.make(42), env, null);
	machine.setA(QSobj.make(false));
	machine.setC(null);
	machine.setE(env);
	machine.setK(kont);
	machine.applyK();
	System.out.println("c=" + machine.C());
	apply(machine.C(), "42");
    }
}
