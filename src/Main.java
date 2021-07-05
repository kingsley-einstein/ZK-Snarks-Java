import java.math.BigInteger;

public class Main {

  public static void main(String... args) throws Exception {
    Prover prover = new Prover();
    Verifier verifier = new Verifier();

    prover.generateX();
    prover.generateV();

    System.out.println(String.format("X: %s \n V: %s", prover.x, prover.v));

    BigInteger g = BigInteger.ONE;
    String y = prover.computeY(g);

    System.out.println(String.format("Y: %s", y));

    String t = prover.computeT(g);

    System.out.println(String.format("T: %s", t));

    String c = prover.computeC(g, y, t);

    System.out.println(String.format("C: %s", c));

    String r = prover.computeR(c);

    System.out.println(String.format("R: %s", r));

    boolean validity = verifier.computeValidity(g, t, r, y, c);

    System.out.println("Validity: " + validity);
  }
}
