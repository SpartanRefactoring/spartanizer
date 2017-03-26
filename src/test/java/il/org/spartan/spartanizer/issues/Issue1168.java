package il.org.spartan.spartanizer.issues;

import org.junit.*;

import static il.org.spartan.spartanizer.testing.TestsUtilsTrimmer.*;

/** TODO Ori Roth: document class
 * @author Ori Roth <tt>ori.rothh@gmail.com</tt>
 * @since 2017-03-26 [[SuppressWarningsSpartan]] */
@SuppressWarnings("static-method")
public class Issue1168 {
  @Test public void a() {
    trimmingOf("" //
        + "a = b;\n" //
        + "a += c;\n" //
        + "a = 1;").stays();
  }

  @Test public void b() {
    trimmingOf("" //
        + "a = b;\n" //
        + "a = a * 5;")
            .gives("" //
                + "a = b * 5;");
  }
}
