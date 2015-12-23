package local.fredslee;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class TestPrim
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TestPrim (String testName)
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TestPrim.class );
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
	System.out.println("Testing prims");

	QSobj qsobj, ans;
	String repr, expect;

	System.out.println("Direct invoke dump");
	qsobj = QSobj.make(94);
	QSmachine machine = new QSmachine();
	machine.primitives.dump.apply(QSobj.cons(qsobj, null));
	ans = machine.A();
	System.out.println("ans = " + ans);

	machine.primitives.null_p.apply(QSobj.cons(qsobj, null));
	ans = machine.A();
	System.out.println("ans = " + ans);

	machine.primitives.null_p.apply(QSobj.cons(null, null));
	ans = machine.A();
	System.out.println("ans = " + ans);
    }
}
