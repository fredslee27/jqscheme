package local.fredslee;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class TestReader
    extends TestCase
{
    QSreader reader0;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TestReader ( String testName )
    {
        super( testName );
	reader0 = new QSreader();
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TestReader.class );
    }

    public void apply (String srctext, String expect)
    {
	reader0 = new QSreader();
	reader0.parse(srctext);
        String repr = reader0.root.toString();
	System.out.println("src=\"" + srctext + "\" -> " + repr + " vs expect=" + expect + " => " + expect.equals(repr));
        //System.out.println("obj=" + repr + " vs expect=" + expect + " => " + expect.equals(repr));
        assertTrue(expect.equals(repr));
    }

    /**
     *
     */
    public void testQScheme()
    {
        //assertTrue( true );
	String expect = null;
	QSobj val = null;

	apply("12345", "12345");

	System.out.println("----");

	apply("  #t", "#t");
	val = reader0.root;
	if (val.isBoolean()) {
	    assertTrue(((QSbool)val).Boolean() == true);
	}

	System.out.println("----");

	apply("foobar", "foobar");

	System.out.println("----");

	apply("(1 2    34)", "(1 2 34)");

	System.out.println("----");

	apply("()", "()");
	apply("(        )", "()");

	System.out.println("----");

	apply("(1    (a c d)3)", "(1 (a c d) 3)");
    }
}

