package il.org.spartan.spartanizer.issues;

import static il.org.spartan.spartanizer.testing.TestsUtilsTrimmer.*;

import org.junit.*;
import org.junit.runners.*;

import il.org.spartan.spartanizer.tippers.*;

/** Unit tests for {@link CastToLong2Multiply1L}
 * @author Niv Shalmon
 * @since 2016 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SuppressWarnings({ "static-method", "javadoc" })
public final class Issue0216 {
  @Test public void issue216_01() {
    topDownTrimming("(long)1.0")//
        .stays();
  }

  @Test public void issue216_02() {
    topDownTrimming("(long)1f")//
        .stays();
  }

  @Test public void issue216_03() {
    topDownTrimming("(long)x")//
        .stays();
  }

  @Test public void issue216_04() {
    topDownTrimming("(long)1")//
        .gives("1L*1")//
        .gives("1L")//
        .stays();
  }

  @Test public void issue216_05() {
    topDownTrimming("(long)'a'")//
        .gives("1L*'a'")//
        .stays();
  }

  @Test public void issue216_06() {
    topDownTrimming("(long)new Integer(5)")//
        .gives("1L*new Integer(5)")//
        .gives("1L*Integer.valueOf(5)")//
        .stays();
  }
}
