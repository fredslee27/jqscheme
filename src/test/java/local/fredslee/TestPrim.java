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
    QSprimreg primreg;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TestPrim (String testName)
    {
        super( testName );
	primreg = new QSprimreg();
	//primreg.install_base();
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

	QSobj qsobj;
	String repr, expect;

	System.out.println("Direct invoke dump");
	qsobj = QSobj.make(94);
	QSmachine machine = new QSmachine();
	machine.primitives.dump.apply(QSobj.cons(qsobj, null));

/*
	System.out.println("len: " + primreg.primlist.size());
	Object prim = primreg.get(1);
	System.out.println("prim[0]=" + prim);

	prim = primreg.lookup(qs_crash);
	System.out.println("prim[qs_crash]=" + prim);
	*/
    }
}
