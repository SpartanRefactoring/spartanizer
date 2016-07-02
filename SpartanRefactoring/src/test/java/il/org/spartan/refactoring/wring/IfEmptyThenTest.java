package il.org.spartan.refactoring.wring;

import static il.org.spartan.azzert.*;
import il.org.spartan.*;
import il.org.spartan.refactoring.utils.*;

import org.eclipse.jdt.core.dom.*;
import org.junit.*;

@SuppressWarnings({ "javadoc", "static-method" })//
public class IfEmptyThenTest {
  private static final IfEmptyThen WRING = new IfEmptyThen();
  private static final Statement INPUT = Into.s("{if (b) ; else ff();}");
  private static final IfStatement IF = extract.firstIfStatement(INPUT);

  @Test public void eligible() {
    azzert.that(WRING.eligible(IF), is(true));
  }
  @Test public void emptyThen() {
    azzert.that(Is.vacuousThen(IF), is(true));
  }
  @Test public void extractFirstIf() {
    that(IF, notNullValue());
  }
  @Test public void inputType() {
    that(INPUT, instanceOf(Block.class));
  }
  @Test public void scopeIncludes() {
    azzert.that(WRING.scopeIncludes(IF), is(true));
  }
}
