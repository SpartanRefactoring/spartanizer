package il.org.spartan.spartanizer.tippers;

import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.ReturnStatement;

import il.org.spartan.spartanizer.ast.navigate.containing;

/** Specializes {@link NodeMatcher} for return statement with value.
 * @author Yossi Gil
 * @since 2017-04-22 */
public abstract class ReturnValuePattern extends NodeMatcher<ReturnStatement> {
  private static final long serialVersionUID = 1;
  protected Expression value;
  protected MethodDeclaration methodDeclaration;

  public ReturnValuePattern() {
    super.notNil("Extract returned value", //
        () -> value = current.getExpression() //
    ).notNil("Return is from a method", //
        () -> methodDeclaration = containing.methodDeclaration(current));
  }
}