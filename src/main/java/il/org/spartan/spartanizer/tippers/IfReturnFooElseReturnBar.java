package il.org.spartan.spartanizer.tippers;

import static il.org.spartan.spartanizer.ast.navigate.step.*;

import org.eclipse.jdt.core.dom.*;
import org.jetbrains.annotations.*;

import il.org.spartan.spartanizer.ast.factory.*;
import il.org.spartan.spartanizer.ast.navigate.*;
import il.org.spartan.spartanizer.dispatch.*;
import il.org.spartan.spartanizer.tipping.*;

/** convert {@code
 * if (x)
 *   return b;
 * else
 *   return c;
 * } into {@code
 * return x? b : c
 * }
 * @author Yossi Gil {@code Yossi.Gil@GMail.COM}
 * @since 2015-07-29 */
public final class IfReturnFooElseReturnBar extends ReplaceCurrentNode<IfStatement>//
    implements TipperCategory.Ternarization {
  private static final long serialVersionUID = -7923427929124988553L;

  @Override public String description(@SuppressWarnings("unused") final IfStatement __) {
    return "Replace if with a return of a conditional statement";
  }

  @Override public boolean prerequisite(@Nullable final IfStatement ¢) {
    return ¢ != null && extract.returnExpression(then(¢)) != null && extract.returnExpression(elze(¢)) != null;
  }

  /** [[SuppressWarningsSpartan]] */
  @Override public Statement replacement(@NotNull final IfStatement s) {
    final Expression then = extract.returnExpression(then(s)), elze = extract.returnExpression(elze(s));
    return then == null || elze == null ? null : subject.operand(subject.pair(then, elze).toCondition(s.getExpression())).toReturn();
  }
}
