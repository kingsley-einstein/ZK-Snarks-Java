import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Prover {

  public String x;
  public String v;

  private String convertToHexString(byte[] bytes) {
    StringBuilder res = new StringBuilder();

    for (byte b : bytes) res.append(Integer.toHexString(0xff & b));

    return res.toString();
  }

  byte[] generateBytes(byte[] bytes) {
    SecureRandom random = new SecureRandom();

    random.nextBytes(bytes);

    return bytes;
  }

  void generateX() {
    int size = 120;
    byte[] bytes = new byte[size];

    bytes = generateBytes(bytes);
    this.x = convertToHexString(bytes);

    BigInteger X = new BigInteger(this.x, 16);

    while (X.intValue() < 0) {
      bytes = generateBytes(bytes);
      this.x = convertToHexString(bytes);
      X = new BigInteger(this.x, 16);
    }
  }

  void generateV() {
    byte[] bytes = new byte[120];

    bytes = generateBytes(bytes);

    this.v = convertToHexString(bytes);

    BigInteger V = new BigInteger(this.v, 16);

    while (V.intValue() < 0) {
      bytes = generateBytes(bytes);
      this.v = convertToHexString(bytes);
      V = new BigInteger(this.v, 16);
    }
  }

  String computeY(BigInteger g) {
    BigInteger X = new BigInteger(this.x, 16);
    BigInteger Y = g.pow(X.intValue());
    return Y.toString(16);
  }

  String computeT(BigInteger g) {
    BigInteger V = new BigInteger(this.v, 16);
    BigInteger T = g.pow(V.intValue());
    return T.toString(16);
  }

  String computeC(BigInteger g, String y, String t) throws Exception {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] bytes = md.digest((g.toString(16) + y + t).getBytes());
      StringBuilder res = new StringBuilder();

      for (byte b : bytes) res.append(Integer.toHexString(0xff & b));

      return res.toString();
    } catch (NoSuchAlgorithmException exc) {
      throw new Exception(exc.getMessage());
    }
  }

  String computeR(String c) {
    BigInteger V = new BigInteger(this.v, 16);
    BigInteger C = new BigInteger(c, 16);
    BigInteger X = new BigInteger(this.x, 16);
    return V.subtract(C.multiply(X)).toString(16);
  }
}
