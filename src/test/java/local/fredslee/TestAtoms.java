package local.fredslee;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class TestAtoms
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TestAtoms (String testName)
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TestAtoms.class );
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

	System.out.println("Testing atoms");

	QSobj qsobj;
	String repr, expect;

	System.out.println("# null");
	qsobj = QSobj.make();
	repr = qsobj.toString();
	expect = "()";
	System.out.println("obj = " + repr + " vs " + expect);
	assertTrue(repr.equals(expect));

	System.out.println("# bool");
	apply(QSobj.make(true), "#t");

	System.out.println("# char");
	apply(QSobj.make('@'), "#\\@");

	System.out.println("# number");
	apply(QSobj.make(42), "42");
	apply(QSobj.make(-1.25), Double.toString(-1.25));
	apply(QSobj.make(2,3), "2/3");
	apply(QSobj.make(0,0.1), Double.toString(0) + "+" + Double.toString(0.1) + "i");

	System.out.println("# string");
	apply(QSobj.make("foobar"), "\"foobar\"");
	apply(QSobj.make("lorem ipsum dolor sit amet"), "\"lorem ipsum dolor sit amet\"");

	System.out.println("# vector");
	apply(QSobj.vec(2), "#(() ())");

	System.out.println("# list");
	apply(new QSpair(QSobj.make(17), QSobj.make(48)), "(17 . 48)");
    }
}
