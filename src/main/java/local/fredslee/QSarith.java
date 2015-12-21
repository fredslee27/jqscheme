package local.fredslee;

/*
Numeric operations.
*/

import java.lang.Exception;

public class QSarith {
    // guards
    //static public QSnumber NaN = new QSnumber();
    //static public QSnumber inf = new QSnumber();
    static public QSnumber NaN = QSnumber.NaN;
    static public QSnumber inf = QSnumber.inf;

    enum NumType {
	INTEGER,
	REAL,
	RATIONAL,
	COMPLEX,
	INF,
	NAN,
    };

    static public int evalInt (QSnumber a)
    {
	if (QSinteger.p(a)) { return ((QSinteger)a).Integer(); }
	else if (QSreal.p(a)) { return ((QSreal)a).Double().intValue(); }
	else if (QSrational.p(a)) {
	    Integer[] q = ((QSrational)a).Integers();
	    if (q[1] == 0) return 0; // NaN
	    return (int)(q[0] / q[1]);
	} else if (QScomplex.p(a)) {
	    Double[] z = ((QScomplex)a).Doubles();
	    if (z[1] != 0) return 0;  // bad.
	    return z[0].intValue();
	}
	return 0;
    }
    static public double evalDouble (QSnumber a)
    {
	if (QSinteger.p(a)) { return ((QSinteger)a).Integer().doubleValue(); }
	else if (QSreal.p(a)) { return ((QSreal)a).Double().doubleValue(); }
	else if (QSrational.p(a)) {
	    Integer[] q = ((QSrational)a).Integers();
	    if (q[1] == 0) return 0; // NaN
	    return ((double)(q[0]) / (double)(q[1]));
	} else if (QScomplex.p(a)) {
	    Double[] z = ((QScomplex)a).Doubles();
	    if (z[1] != 0) return 0;  // bad
	    return z[0].doubleValue();
	}
	return 0;
    }
    static public int[] evalInts (QSnumber a)
    {
	if (QSinteger.p(a)) { return new int[]{evalInt(a), 1}; }
	else if (QSreal.p(a)) { return new int[]{evalInt(a), 1}; }  // TODO: approximate fraction.
	else if (QSrational.p(a)) {
	    Integer[] q = ((QSrational)a).Integers();
	    return new int[]{q[0], q[1]};
	} else if (QScomplex.p(a)) {
	    Double[] z = ((QScomplex)a).Doubles();
	    if (z[1] != 0) return new int[]{0,1};  // bad.
	    return new int[]{z[0].intValue(), 1};
	}
	return new int[]{0,1};
    }
    static public double[] evalDoubles (QSnumber a)
    {
	if (QScomplex.p(a)) {
	    Double[] z = ((QScomplex)a).Doubles();
	    return new double[]{z[0].doubleValue(),z[1].doubleValue()};
	} else {
	    return new double[]{evalDouble(a),0};
	}
    }

/*
    static public QSnumber.NumCmp sign (QSobj a)
    {
	if (QSinteger.p(a)) return ((QSinteger)a).sign();
	else if (QSreal.p(a)) return ((QSreal)a).sign();
	else if (QSrational.p(a)) return ((QSrational)a).sign();
	else if (QScomplex.p(a)) return ((QScomplex)a).sign();
	return QSnumber.NumCmp.NC;
    }
    static public QSnumber.NumCmp cmp (QSobj a, QSobj b)
    {
	QSnumber test = sub(a,b);
	QSnumber.NumCmp retval = sign(test);
	return retval;
	//return QSnumber.NumCmp.NC;
    }
*/
    static public QSnumber add (QSnumber a, QSnumber b)
    {
	QSnumber retval = NaN;
	NumType ansType = NumType.NAN;
	if (QSinteger.p(a))
	{
	    if (QSinteger.p(b)) ansType = NumType.INTEGER; // Z + Z => Z
	    else if (QSreal.p(b)) ansType = NumType.REAL; // Z + R => R
	    else if (QSrational.p(b)) ansType = NumType.RATIONAL; // Z + Q => Q
	    else if (QScomplex.p(b)) ansType = NumType.COMPLEX; // Z + C => C
	}
	else if (QSreal.p(a))
	{
	    if (QSinteger.p(b)) ansType = NumType.REAL; // R + Z => R
	    else if (QSreal.p(b)) ansType = NumType.REAL; // R + R => R
	    else if (QSrational.p(b)) ansType = NumType.REAL; // R + Q => R
	    else if (QScomplex.p(b)) ansType = NumType.COMPLEX; // R + C => C
	}
	else if (QSrational.p(a))
	{
	    if (QSinteger.p(b)) ansType = NumType.RATIONAL; // Q + Z => Q
	    else if (QSreal.p(b)) ansType = NumType.REAL; // Q + R => R
	    else if (QSrational.p(b)) ansType = NumType.RATIONAL; // Q + Q => Q
	    else if (QScomplex.p(b)) ansType = NumType.COMPLEX; // Q + C => C
	}
	else if (QScomplex.p(a))
	{
	    if (QSinteger.p(b)) ansType = NumType.COMPLEX; // C + Z => C
	    else if (QSreal.p(b)) ansType = NumType.COMPLEX; // C + R => R
	    else if (QSrational.p(b)) ansType = NumType.COMPLEX; // C + Q => R
	    else if (QScomplex.p(b)) ansType = NumType.COMPLEX; // C + C => C
	}

	switch (ansType)
	{
	case INTEGER:
	    int i = evalInt(a), j = evalInt(b);
	    retval = QSinteger.make(i + j);
	    break;
	case REAL:
	    double x = evalDouble(a), y = evalDouble(b);
	    retval = QSreal.make(x + y);
	    break;
	case RATIONAL:
	    int[] ii = evalInts(a), jj = evalInts(b);
	    if ((ii[1] * jj[1]) != 0)
	    {
		int num = ii[0]*jj[1] + ii[1]*jj[0];
		int den = ii[1] * jj[1];
		retval = QSrational.make(num, den);
	    }
	    break;
	case COMPLEX:
	    double[] xx = evalDoubles(a), yy = evalDoubles(b);
	    retval = QScomplex.make(xx[0]+yy[0], xx[1]+yy[1]);
	    break;
	default:
	    retval = NaN;
	    break;
	}

	return retval;
    }

    static public QSnumber sub (QSnumber a, QSnumber b)
    {
	QSnumber retval = NaN;
	NumType ansType = NumType.NAN;
	if (QSinteger.p(a))
	{
	    if (QSinteger.p(b)) ansType = NumType.INTEGER; // Z - Z => Z
	    else if (QSreal.p(b)) ansType = NumType.REAL; // Z - R => R
	    else if (QSrational.p(b)) ansType = NumType.RATIONAL; // Z - Q => Q
	    else if (QScomplex.p(b)) ansType = NumType.COMPLEX; // Z - C => C
	}
	else if (QSreal.p(a))
	{
	    if (QSinteger.p(b)) ansType = NumType.REAL; // R - Z => R
	    else if (QSreal.p(b)) ansType = NumType.REAL; // R - R => R
	    else if (QSrational.p(b)) ansType = NumType.REAL; // R - Q => R
	    else if (QScomplex.p(b)) ansType = NumType.COMPLEX; // R - C => C
	}
	else if (QSrational.p(a))
	{
	    if (QSinteger.p(b)) ansType = NumType.RATIONAL; // Q - Z => Q
	    else if (QSreal.p(b)) ansType = NumType.REAL; // Q - R => R
	    else if (QSrational.p(b)) ansType = NumType.RATIONAL; // Q - Q => Q
	    else if (QScomplex.p(b)) ansType = NumType.COMPLEX; // Q - C => C
	}
	else if (QScomplex.p(a))
	{
	    if (QSinteger.p(b)) ansType = NumType.COMPLEX; // C - Z => C
	    else if (QSreal.p(b)) ansType = NumType.COMPLEX; // C - R => C
	    else if (QSrational.p(b)) ansType = NumType.COMPLEX; // C - Q => C
	    else if (QScomplex.p(b)) ansType = NumType.COMPLEX; // C - C => C
	}

	switch (ansType)
	{
	case INTEGER:
	    int i = evalInt(a), j = evalInt(b);
	    retval = QSinteger.make(i - j);
	    break;
	case REAL:
	    double x = evalDouble(a), y = evalDouble(b);
	    retval = QSreal.make(x - y);
	    break;
	case RATIONAL:
	    int[] ii = evalInts(a), jj = evalInts(b);
	    if ((ii[1] * jj[1]) != 0)
	    {
		int num = ii[0]*jj[1] - ii[1]*jj[0];
		int den = ii[1] * jj[1];
		retval = QSrational.make(num, den);
	    }
	    break;
	case COMPLEX:
	    double[] xx = evalDoubles(a), yy = evalDoubles(b);
	    retval = QScomplex.make(xx[0]-yy[0], xx[1]-yy[1]);
	    break;
	default:
	    retval = NaN;
	    break;
	}

	return retval;
    }

    static public QSnumber mul (QSnumber a, QSnumber b)
    {
	QSnumber retval = NaN;
	NumType ansType = NumType.NAN;
	if (QSinteger.p(a))
	{
	    if (QSinteger.p(b)) ansType = NumType.INTEGER; // Z * Z => Z
	    else if (QSreal.p(b)) ansType = NumType.REAL; // Z * R => R
	    else if (QSrational.p(b)) ansType = NumType.RATIONAL; // Z * Q => Q
	    else if (QScomplex.p(b)) ansType = NumType.COMPLEX; // Z * C => C
	}
	else if (QSreal.p(a))
	{
	    if (QSinteger.p(b)) ansType = NumType.REAL; // R * Z => R
	    else if (QSreal.p(b)) ansType = NumType.REAL; // R * R => R
	    else if (QSrational.p(b)) ansType = NumType.REAL; // R * Q => R
	    else if (QScomplex.p(b)) ansType = NumType.COMPLEX; // R * C => C
	}
	else if (QSrational.p(a))
	{
	    if (QSinteger.p(b)) ansType = NumType.RATIONAL; // Q * Z => Q
	    else if (QSreal.p(b)) ansType = NumType.REAL; // Q * R => R
	    else if (QSrational.p(b)) ansType = NumType.RATIONAL; // Q * Q => Q
	    else if (QScomplex.p(b)) ansType = NumType.COMPLEX; // Q * C => C
	}
	else if (QScomplex.p(a))
	{
	    if (QSinteger.p(b)) ansType = NumType.COMPLEX; // C * Z => C
	    else if (QSreal.p(b)) ansType = NumType.COMPLEX; // C * R => C
	    else if (QSrational.p(b)) ansType = NumType.COMPLEX; // C * Q => .
	    else if (QScomplex.p(b)) ansType = NumType.COMPLEX; // C * C => C
	}

	switch (ansType)
	{
	case INTEGER:
	    int i = evalInt(a), j = evalInt(b);
	    retval = QSinteger.make(i * j);
	    break;
	case REAL:
	    double x = evalDouble(a), y = evalDouble(b);
	    retval = QSreal.make(x * y);
	    break;
	case RATIONAL:
	    int[] ii = evalInts(a), jj = evalInts(b);
	    if ((ii[1] * jj[1]) != 0)
	    {
		int num = ii[0] * jj[0];
		int den = ii[1] * jj[1];
		retval = QSrational.make(num, den);
	    }
	    break;
	case COMPLEX:
	    double[] xx = evalDoubles(a), yy = evalDoubles(b);
	    double aa = (xx[0]*yy[0]) - (xx[1]*yy[1]);  // i^2 = -1
	    double bb = (xx[0]*yy[1]) + (xx[1]*yy[0]);
	    retval = QScomplex.make(aa, bb);
	    break;
	default:
	    retval = NaN;
	    break;
	}

	return retval;
    }

    // integer-theroetical operation.
    static private int[] _divmod (QSnumber a, QSnumber b)
    {
	int[] retval = { 0, 0 };
	if (!QSinteger.p(a)) return retval;
	if (!QSinteger.p(b)) return retval;
	int i = evalInt(a), j = evalInt(b);
	if (j == 0) return retval;
	int q = (i / j);
	int r = (i % j);
	retval = new int[]{q,r};
	return retval;
    }

    static public QSnumber mod (QSnumber a, QSnumber b)
    {
	QSnumber retval = null;
	NumType ansType = NumType.NAN;
	if (QSinteger.p(a))
	{
	    if (QSinteger.p(b)) ansType = NumType.INTEGER; // Z % Z => Z
	    else if (QSreal.p(b)) ansType = NumType.NAN; // Z % R => -
	    else if (QSrational.p(b)) ansType = NumType.NAN; // Z % Q => -
	    else if (QScomplex.p(b)) ansType = NumType.NAN; // Z % C => -
	}
	else
	{
	    ansType = NumType.NAN; // * % * => -
	}
	if (ansType == NumType.INTEGER)
	{
	    int i = evalInt(a), j = evalInt(b);
	    retval = QSinteger.make(i % j);
	}
	else
	{
	    retval = QSinteger.make(0);
	}
	return retval;
    }

    static public QSnumber div (QSnumber a, QSnumber b)
    {
	QSnumber retval = NaN;
	NumType ansType = NumType.NAN;
	if (QSinteger.p(a))
	{
	    if (QSinteger.p(b)) ansType = NumType.RATIONAL; // Z / Z => Q
	    else if (QSreal.p(b)) ansType = NumType.REAL; // Z / R => R
	    else if (QSrational.p(b)) ansType = NumType.RATIONAL; // Z / Q => Q
	    else if (QScomplex.p(b)) ansType = NumType.COMPLEX; // Z / C => C
	}
	else if (QSreal.p(a))
	{
	    if (QSinteger.p(b)) ansType = NumType.REAL; // R / Z => R
	    else if (QSreal.p(b)) ansType = NumType.REAL; // R / R => R
	    else if (QSrational.p(b)) ansType = NumType.REAL; // R / Q => R
	    else if (QScomplex.p(b)) ansType = NumType.COMPLEX; // R / C => C
	}
	else if (QSrational.p(a))
	{
	    if (QSinteger.p(b)) ansType = NumType.RATIONAL; // Q / Z => Q
	    else if (QSreal.p(b)) ansType = NumType.REAL; // Q / R => R
	    else if (QSrational.p(b)) ansType = NumType.RATIONAL; // Q / Q => Q
	    else if (QScomplex.p(b)) ansType = NumType.COMPLEX; // Q / C => C
	}
	else if (QScomplex.p(a))
	{
	    if (QSinteger.p(b)) ansType = NumType.COMPLEX; // C / Z => C
	    else if (QSreal.p(b)) ansType = NumType.COMPLEX; // C / R => C
	    else if (QSrational.p(b)) ansType = NumType.COMPLEX; // C / Q => C
	    else if (QScomplex.p(b)) ansType = NumType.COMPLEX; // C / C => C
	}

	switch (ansType)
	{
	case INTEGER:
	    int i = evalInt(a), j = evalInt(b);
	    retval = QSinteger.make(i / j);
	    break;
	case REAL:
	    double x = evalDouble(a), y = evalDouble(b);
	    retval = QSreal.make(x / y);
	    break;
	case RATIONAL:
	    int[] ii = evalInts(a), jj = evalInts(b);
	    if ((ii[1] * jj[1]) != 0)
	    {
		int num = ii[0] * jj[1];
		int den = ii[1] * jj[0];
		retval = QSrational.make(num, den);
	    }
	    break;
	case COMPLEX:
	    double[] xx = evalDoubles(a), yy = evalDoubles(b);
	    /*
	    a + i*b <= z0
	    c * i*d <= z1
	    (z0 / z1) = (a*c + b*d)/(c^2 + d^2) + i*(b*c - a*d)/(c^2 + d^2)
	    */
	    double den = yy[0]*yy[0] + yy[1]*yy[1];
	    double aa = (xx[0]*yy[0] + xx[1]*yy[1]);
	    double bb = (xx[1]*yy[0] - xx[0]*yy[1]);
	    retval = QScomplex.make(aa/den, bb/den);
	    break;
	default:
	    retval = NaN;
	    break;
	}

	return retval;
    }

    static public QSnumber abs (QSnumber a)
    {
	QSnumber retval = QSnumber.NaN;
	if (QSinteger.p(a))
	{
	    retval = QSinteger.make(Math.abs(a.asInt().Integer().intValue()));
	}
	else if (QSreal.p(a))
	{
	    retval = QSreal.make(Math.abs(a.asReal().Double().doubleValue()));
	}
	else if (QSrational.p(a))
	{
	    int[] q = evalInts(a);
	    retval = QSrational.make(Math.abs(q[0]), Math.abs(q[1]));
	}
	else if (QScomplex.p(a))
	{
	    double[] z = evalDoubles(a);
	    retval = QSreal.make(Math.sqrt( (z[0]*z[0]) + (z[1]*z[1]) ));
	}
	return retval;
    }


    // e**obj
    static public QSnumber exp (QSnumber a)
    {
	QSnumber retval = NaN;
	if (a.isInteger() || a.isReal() || a.isRational())
	{
	    double z = evalDouble(a);
	    z = Math.exp(z);
	    retval = QSreal.make(z);
	}
	else if (a.isComplex())
	{
	    // TODO: exp(C);
	}
	return retval;
    }
    // ln(obj)
    static public QSnumber log (QSnumber a)
    {
	QSnumber retval = NaN;
	if (a.isInteger() || a.isReal() || a.isRational())
	{
	    double z = evalDouble(a);
	    z = Math.log(z);
	    retval = QSreal.make(z);
	}
	else if (a.isComplex())
	{
	    // TODO: ln(C);
	}
	return retval;
    }
    // sin(obj)
    static public QSnumber sin (QSnumber a)
    {
	QSnumber retval = NaN;
	if (a.isInteger() || a.isReal() || a.isRational())
	{
	    double z = evalDouble(a);
	    z = Math.sin(z);
	    retval = QSreal.make(z);
	}
	else if (a.isComplex())
	{
	    // TODO: sin(C);
	}
	return retval;
    }
    // cos(obj)
    static public QSnumber cos (QSnumber a)
    {
	QSnumber retval = NaN;
	if (a.isInteger() || a.isReal() || a.isRational())
	{
	    double z = evalDouble(a);
	    z = Math.cos(z);
	    retval = QSreal.make(z);
	}
	else if (a.isComplex())
	{
	    // TODO: cos(C);
	}
	return retval;
    }
    // tan(obj)
    static public QSnumber tan (QSnumber a)
    {
	QSnumber retval = NaN;
	if (a.isInteger() || a.isReal() || a.isRational())
	{
	    double z = evalDouble(a);
	    z = Math.tan(z);
	    retval = QSreal.make(z);
	}
	else if (a.isComplex())
	{
	    // TODO: tan(C);
	}
	return retval;
    }
    // asin(obj)
    static public QSnumber asin (QSnumber a)
    {
	QSnumber retval = NaN;
	if (a.isInteger() || a.isReal() || a.isRational())
	{
	    double z = evalDouble(a);
	    z = Math.asin(z);
	    retval = QSreal.make(z);
	}
	else if (a.isComplex())
	{
	    // TODO: asin(C);
	}
	return retval;
    }
    // acos(obj)
    static public QSnumber acos (QSnumber a)
    {
	QSnumber retval = NaN;
	if (a.isInteger() || a.isReal() || a.isRational())
	{
	    double z = evalDouble(a);
	    z = Math.acos(z);
	    retval = QSreal.make(z);
	}
	else if (a.isComplex())
	{
	    // TODO: acos(C);
	}
	return retval;
    }
    // atan(obj)
    static public QSnumber atan (QSnumber a)
    {
	QSnumber retval = NaN;
	if (a.isInteger() || a.isReal() || a.isRational())
	{
	    double z = evalDouble(a);
	    z = Math.atan(z);
	    retval = QSreal.make(z);
	}
	else if (a.isComplex())
	{
	    // TODO: atan(C);
	}
	return retval;
    }
    // atan2(obj1, obj2)
    static public QSnumber atan2 (QSnumber a, QSnumber b)
    {
	QSnumber retval = NaN;
	if (a.isInteger() || a.isReal() || a.isRational())
	{
	    double y = evalDouble(a);
	    double x = evalDouble(b);
	    double z = Math.atan2(y, x);
	    retval = QSreal.make(z);
	}
	else if (a.isComplex())
	{
	    // TODO: atan(C,C);
	}
	return retval;
    }


    static public QSnumber sqrt (QSnumber a)
    {
	QSnumber retval = NaN;
	if (a.isInteger())
	{
	    int i = evalInt(a);
	    double x = Math.sqrt(i);
	    int b = (int)x;
	    if ( b*b == i ) retval = QSinteger.make(b);  // perfect square.
	    else retval = QSreal.make(x);
	}
	else if (a.isReal())
	{
	    double x = evalDouble(a);
	    retval = QSreal.make(x);
	}
	else if (a.isRational())
	{
	    int[] q = evalInts(a);
	    double xx = Math.sqrt(q[0]);
	    double yy = Math.sqrt(q[1]);
	    int ii = (int)xx;
	    int jj = (int)yy;
	    if (((ii*ii) == q[0]) && ((jj*jj) == q[1])) {
		// perfect squares.
		retval = QSrational.make(ii,jj);
	    } else {
		retval = QSrational.make(xx, yy);
	    }
	    return retval;
	}
	else if (a.isComplex())
	{
	}
	return retval;
    }

    static public QSnumber expt (QSnumber a, QSnumber b)
    {
	QSnumber retval = NaN;
	if (a.isInteger() && b.isInteger())
	{
	    // integer exponentiation.
	    int i = evalInt(a), j = evalInt(b);
	    int ans = (int)(Math.pow(i, j));
	    retval = QSinteger.make(ans);
	}
	else if (a.isComplex() || b.isComplex())
	{
	    // complex exponentiation.
	}
	else if (b.isRational())
	{
	    // TODO: exponentiation and rooting.
	    double x = evalDouble(a), y = evalDouble(b);
	    double ans = Math.pow(x, y);
	    retval = QSreal.make(ans);
	}
	else
	{
	    double x = evalDouble(a), y = evalDouble(b);
	    double ans = Math.pow(x, y);
	    retval = QSreal.make(ans);
	}
	return retval;
    }
};


