package local.fredslee;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class TestEnv
    extends TestCase
{
    QSenv env;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TestEnv (String testName)
    {
        super( testName );
	env = new QSenv();
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TestEnv.class );
    }

    public void apply (QSobj qsobj, String expect)
    {
	String repr = (qsobj != null) ? qsobj.toString() : "null";
	System.out.println("obj=" + repr + " vs expect=" + expect + " => " + expect.equals(repr));
	assertTrue(expect.equals(repr));
    }

    /**
     * 
     */
    public void testQScheme()
    {
        //assertTrue( true );
	System.out.println("env=" + env);
	apply(env, "(())");

	QSsym sym0;
	QSobj val0;

	sym0 = QSobj.intern("foo");
	val0 = QSobj.make("bar");
	env.freshen(sym0);
	env.bind(sym0, val0);
	System.out.println("env=" + env);
	apply(env, "(((foo . \"bar\")))");

	sym0 = QSobj.intern("baz");
	val0 = QSobj.make(19);
	env.freshen(sym0);
	env.bind(sym0, val0);
	System.out.println("env=" + env);
	apply(env, "(((baz . 19)(foo . \"bar\")))");

	env.newframe();
	sym0 = QSobj.intern("baz");
	val0 = QSobj.make(18);
	env.freshen(sym0);
	env.bind(sym0, val0);
	System.out.println("env=" + env);
	apply(env, "(((baz . 18))((baz . 19)(foo . \"bar\")))");

	sym0 = QSobj.intern("foo");
	val0 = env.resolve(sym0, true);
	System.out.println("resolve(" + sym0 + ") = " + val0);
	apply(val0, "\"bar\"");

	sym0 = QSobj.intern("foo");
	val0 = env.resolve(sym0, false);
	System.out.println("resolve1(" + sym0 + ") = " + val0);
	apply(val0, "null");
    }
}
