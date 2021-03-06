/* TODO Yossi Gil Document Class
 *
 * @author Yossi Gil
 *
 * @since Sep 25, 2016 */
package il.org.spartan.spartanizer.testing;

import static fluent.ly.azzert.instanceOf;

import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.Statement;
import org.junit.Test;

import fluent.ly.azzert;
import il.org.spartan.spartanizer.ast.navigate.findFirst;
import il.org.spartan.spartanizer.ast.safety.iz;
import il.org.spartan.spartanizer.engine.parse;
import il.org.spartan.spartanizer.tippers.IfEmptyThen;

@SuppressWarnings({ "javadoc", "static-method" })
public final class IfEmptyThenTest {
  private static final Statement INPUT = parse.s("{if (b) ; else ff();}");
  private static final IfStatement IF = findFirst.ifStatement(INPUT);
  private static final IfEmptyThen TIPPER = new IfEmptyThen();

  @Test public void eligible() {
    assert TIPPER.check(IF);
  }
  @Test public void emptyThen() {
    assert iz.vacuousThen(IF);
  }
  @Test public void extractFirstIf() {
    assert IF != null;
  }
  @Test public void inputType() {
    azzert.that(INPUT, instanceOf(Block.class));
  }
  @Test public void scopeIncludes() {
    assert TIPPER.check(IF);
  }
}
