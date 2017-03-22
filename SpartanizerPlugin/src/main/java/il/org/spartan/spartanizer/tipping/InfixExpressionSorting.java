/* TODO Yossi Gil {@code Yossi.Gil@GMail.COM} please add a description
 *
 * @author Yossi Gil {@code Yossi.Gil@GMail.COM}
 *
 * @since Sep 25, 2016 */
package il.org.spartan.spartanizer.tipping;

import java.util.*;

import org.eclipse.jdt.core.dom.*;
import org.jetbrains.annotations.NotNull;

abstract class InfixExpressionSorting extends ReplaceCurrentNode<InfixExpression> {
  private static final long serialVersionUID = 2767714386379462412L;

  @NotNull
  @Override public final String description(@NotNull final InfixExpression ¢) {
    return "Reorder operands of " + ¢.getOperator();
  }

  protected abstract boolean sort(List<Expression> operands);

  protected abstract boolean suitable(InfixExpression x);
}
