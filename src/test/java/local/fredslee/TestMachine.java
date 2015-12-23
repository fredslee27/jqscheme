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

	System.out.println("# resolving int:36");
	qsobj = QSnumber.make(36);
	machine.setC(qsobj);
	System.out.println("running c=" + machine.C());
	machine.cycle();
	System.out.println("a=" + machine.A());

	System.out.println("# resolving variable:'revision'");
	machine.reset();
	machine.setE(QSenv.standard6());
	qsobj = QSsym.intern("revision");
	machine.setC(qsobj);
	System.out.println("running c=" + machine.C());
	machine.cycle();
	System.out.println("a=" + machine.A());

	System.out.println("# executing list:(__dump__ 86)");
	machine.reset();
	qsobj = QSpair.QSlist.make(machine.primitives.dump, QSobj.make(86));
	machine.setE(QSenv.standard6());
	machine.setC(qsobj);
	machine.cycle();
	System.out.println("a=" + machine.A());

	System.out.println(machine);

	System.out.println("# executing list:(__boolean_p__ true)");
	machine.reset();
	qsobj = QSpair.QSlist.make(machine.primitives.boolean_p, QSobj.make(true));
	machine.setC(qsobj);
	machine.cycle();
	System.out.println("a=" + machine.A());

	System.out.println("# executing list:(__boolean_p__ false)");
	machine.reset();
	qsobj = QSpair.QSlist.make(machine.primitives.boolean_p, QSobj.make(false));
	machine.setC(qsobj);
	machine.cycle();
	System.out.println("a=" + machine.A());

	System.out.println("# executing list:(__boolean_p__ 9)");
	machine.reset();
	qsobj = QSpair.QSlist.make(machine.primitives.boolean_p, QSobj.make(9));
	machine.setC(qsobj);
	machine.cycle();
	System.out.println("a=" + machine.A());

    }
}
