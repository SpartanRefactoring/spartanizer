package il.org.spartan.zoomer.zoomin.expanders;

import static il.org.spartan.bloater.bloaters.BloatingTestUtilities.*;

import java.util.*;

import org.junit.*;

import il.org.spartan.spartanizer.meta.*;

/** Test class for issue #965
 * @author Dor Ma'ayan <tt>dor.d.ma@gmail.com</tt>
 * @since 2016-12-20 */
public class Issue0965 {
  @Test public void test0() {
    bloatingOf(new TestClass()) //
        .givesWithBinding("public String check1(){return lst.toString();}", "check1")//
        .staysWithBinding();
  }

  class TestClass extends MetaFixture {
    final List<Integer> lst = new ArrayList<>();

    public String check1() {
      return lst + "";
    }

    public void check2() {
      //
    }

    public void check3() {
      //
    }
  }
}