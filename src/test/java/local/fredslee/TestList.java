package local.fredslee;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class TestList
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TestList ( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TestList.class );
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
	String expect;
	QSpair lis0;

	lis0 = QSobj.cons(QSobj.make(17), QSobj.make(42));
	expect = "(17 . 42)";
	apply(lis0, expect);

	QSpair pair2 = QSobj.cons(QSobj.make(56), null);
	QSpair pair1 = QSobj.cons(QSobj.make(34), pair2);
	QSpair pair0 = QSobj.cons(QSobj.make(12), pair1);
	expect = "(12 34 56)";
	apply(pair0, expect);

	QSpair lis1 = QSpair.list(QSobj.make(11), QSobj.make(22), QSobj.make(33), QSobj.make(44), QSobj.make(55));
	expect = "(11 22 33 44 55)";
	apply(lis1, expect);
    }
}
