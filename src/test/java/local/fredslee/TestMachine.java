package local.fredslee;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class TestMachine
    extends TestCase
{
    QSmachine machine;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TestMachine (String testName)
    {
        super( testName );
	machine = new QSmachine();
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TestMachine.class );
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
	System.out.println("Testing machine");

	QSobj qsobj;
	String repr, expect;

	machine.reset();

	qsobj = QSnumber.make(36);
	machine.setC(qsobj);
	machine.cycle();
	System.out.println("a=" + machine.A());
    }
}
