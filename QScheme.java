class QScheme {
    static QSmachine mach;

    static void test1 ()
    {
	QSobj obj0;

	//obj0 = QSobj.make();
	obj0 = new QSnull();
	//System.out.println("obj1 = (" + obj1.getClass() + ")" + obj1);
	System.out.println("obj0 = " + obj0.dump());

	//obj0 = QSobj.make('@');
	obj0 = new QSbool(true);
	System.out.println("obj0 = " + obj0.dump());

	//obj0 = QSobj.make('@');
	obj0 = new QSchar('@');
	System.out.println("obj0 = " + obj0.dump());

	//obj0 = QSobj.make(42);
	obj0 = new QSinteger(42);
	System.out.println("obj0 = " + obj0.dump());

	//obj0 = QSobj.make(-1.25);
	obj0 = new QSreal(-1.25);
	System.out.println("obj0 = " + obj0.dump());

	//obj0 = QSobj.make(2,3);
	obj0 = new QSrational(2,3);
	System.out.println("obj0 = " + obj0.dump());

	//obj0 = QSobj.make(0,1.);
	obj0 = new QScomplex(0, 1.);
	System.out.println("obj0 = " + obj0.dump());

	//obj0 = QSobj.make(0,1.);
	obj0 = new QSvec(2);
	System.out.println("obj0 = " + obj0.dump());

	//obj0 = QSobj.make("foobar");
	obj0 = new QSstr("foobar");
	System.out.println("obj0 = " + obj0.dump());

	obj0 = QSobj.intern("sym0");
	System.out.println("obj0 = " + obj0.dump());
	obj0 = QSobj.intern("symA");
	System.out.println("obj0 = " + obj0.dump());
	obj0 = QSobj.intern("sym0");
	System.out.println("obj0 = " + obj0.dump());

	QSobj pair0;
	pair0 = new QSpair(new QSinteger(17), new QSinteger(37));
	System.out.println("pair0 = " + pair0.dump());
	pair0 = QSobj.cons(QSobj.make(18), QSobj.make(38));
	System.out.println("pair0 = " + pair0.dump());
    }

    static void test_list ()
    {
	// QSobj obj1 = new QSlist(new QSobj(1));
    }

    static void test_reader ()
    {
	QSreader reader0;

	reader0 = new QSreader();
	reader0.parse("12345");
	System.out.println("root=" + reader0.root);

	System.out.println("----");

	reader0 = new QSreader();
	reader0.parse("foobar");
	System.out.println("root=" + reader0.root);

	System.out.println("----");

	reader0 = new QSreader();
	reader0.parse("(1 2 3)");
	System.out.println("root=" + reader0.root);

	System.out.println("----");

	reader0 = new QSreader();
	reader0.parse("()");
	System.out.println("root=" + reader0.root);

	System.out.println("----");

	reader0 = new QSreader();
	reader0.parse("(1 (a c d) 3)");
	System.out.println("root=" + reader0.root);
    }

    static public void main (String argv[])
    {
	mach = new QSmachine();
	System.out.println("QScheme READY");

	//test1();
	test_reader();
    }
}

