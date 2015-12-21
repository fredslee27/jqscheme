package local.fredslee;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class TestMath
    extends TestCase
{
    QSreader reader0;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TestMath ( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TestMath.class );
    }

    public void apply (QSobj obj, String expect)
    {
        String repr = obj.toString();
        System.out.println("obj=" + repr + " vs expect=" + expect + " => " + expect.equals(repr));
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

	QSnumber num0;
	QSnumber num1;

	num0 = QSnumber.make(3);
	num1 = QSnumber.make(4);
	val = QSarith.add(num0, num1);
	System.out.println("add(" + num0 + "," + num1 + ") = " + val);
	apply(val, Integer.toString(7));

	num0 = QSnumber.make(0.5);
	num1 = QSnumber.make(1.5);
	val = QSarith.add(num0, num1);
	System.out.println("add(" + num0 + "," + num1 + ") = " + val);
	apply(val, Double.toString(2.0));

	num0 = QSnumber.make(1,3);
	num1 = QSnumber.make(2,3);
	val = QSarith.add(num0, num1);
	System.out.println("add(" + num0 + "," + num1 + ") = " + val);
	//apply(val, Integer.toString(1) + "/" + Integer.toString(1));
	apply(val, Integer.toString(9) + "/" + Integer.toString(9));

	num0 = QSnumber.make(0,.2);
	num1 = QSnumber.make(8.,0);
	val = QSarith.add(num0, num1);
	System.out.println("add(" + num0 + "," + num1 + ") = " + val);
	//apply(val, Integer.toString(1) + "/" + Integer.toString(1));
	apply(val, Double.toString(8) + "+" + Double.toString(.2) + "i");


	num0 = QSnumber.make(92);
	num1 = QSnumber.make(55);
	val = QSarith.sub(num0, num1);
	System.out.println("sub(" + num0 + "," + num1 + ") = " + val);
	apply(val, Integer.toString(92-55));


	num0 = QSnumber.make(5);
	num1 = QSnumber.make(9);
	val = QSarith.mul(num0, num1);
	System.out.println("mul(" + num0 + "," + num1 + ") = " + val);
	apply(val, Integer.toString(5*9));

	num0 = QSnumber.make(3.,4.);
	num1 = QSnumber.make(2.,5.);
	val = QSarith.mul(num0, num1);
	System.out.println("mul(" + num0 + "," + num1 + ") = " + val);
	apply(val, Double.toString(-14) + "+" + Double.toString(23) + "i");


	num0 = QSnumber.make(1);
	num1 = QSnumber.make(2);
	val = QSarith.div(num0, num1);
	System.out.println("div(" + num0 + "," + num1 + ") = " + val);
	apply(val, Integer.toString(1) + "/" + Integer.toString(2));

	num0 = QSnumber.make(1.);
	num1 = QSnumber.make(2.);
	val = QSarith.div(num0, num1);
	System.out.println("div(" + num0 + "," + num1 + ") = " + val);
	apply(val, Double.toString(1./2.));

	num0 = QSnumber.make(3.,4.);
	num1 = QSnumber.make(6.,-11.);
	val = QSarith.div(num0, num1);
	System.out.println("div(" + num0 + "," + num1 + ") = " + val);
	apply(val, Double.toString(-26./157) + "+" + Double.toString(57./157) + "i");

	num0 = QSnumber.make(9);
	val = QSarith.sqrt(num0);
	System.out.println("sqrt(" + num0 + ") = " + val);
	apply(val, Integer.toString(3));

	num0 = QSnumber.make(8);
	val = QSarith.sqrt(num0);
	System.out.println("sqrt(" + num0 + ") = " + val);
	apply(val, Double.toString(Math.sqrt(8.)));
    }
}

