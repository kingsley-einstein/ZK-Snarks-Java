import java.math.BigInteger;

public class Verifier {

  boolean computeValidity(
    BigInteger g,
    String t,
    String r,
    String y,
    String c
  ) {
    BigInteger T = new BigInteger(t, 16);
    BigInteger R = new BigInteger(r, 16);
    BigInteger Y = new BigInteger(y, 16);
    BigInteger C = new BigInteger(c, 16);
    BigInteger eq = g.pow(R.intValue()).multiply(Y.pow(C.intValue()));

    System.out.println("T in verification: " + T);
    System.out.println("EQ in verification: " + eq);
    return T.equals(eq);
  }
}
