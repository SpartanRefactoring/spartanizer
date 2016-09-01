package il.org.spartan.refactoring.wring;

import static org.eclipse.jdt.core.dom.PrefixExpression.Operator.*;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.InfixExpression.*;

import il.org.spartan.refactoring.ast.*;
import il.org.spartan.refactoring.builder.*;
import il.org.spartan.refactoring.engine.*;

/** Converts <code>x.size()==0</code> to <code>x.isEmpty()</code>,
 * <code>x.size()!=0 </code> and <code>x.size()>=1</code>
 * <code>!x.isEmpty()</code>, <code>x.size()<0</code> to <code><b>false</b>,and
 * <code>x.size()>=0</code> to <code><b>true</b>.
 * @author Ori Roth <code><ori.rothh [at] gmail.com></code>
 * @author Yossi Gil
 * @author Dor Ma'ayan<code><dor.d.ma [at] gmail.com></code>
 * @author Niv Shalmon <code><shalmon.niv [at] gmail.com></code>
 * @author Stav Namir <code><stav1472 [at] gmail.com></code>
 * @since 2016-04-24 */
public final class InfixComparisonSizeToZero extends Wring.ReplaceCurrentNode<InfixExpression> implements Kind.Canonicalization {
  private static NumberLiteral getNegativeNumber(final PrefixExpression ¢) {
    return ¢.getOperator() != PrefixExpression.Operator.MINUS || !(¢.getOperand() instanceof NumberLiteral) ? null : (NumberLiteral) ¢.getOperand();
  }

  private static NumberLiteral getNegativeNumber(final Expression ¢) {
    return !(¢ instanceof PrefixExpression) ? null : getNegativeNumber((PrefixExpression) ¢);
  }

  private static boolean isNumber(final Expression ¢) {
    return ¢ instanceof NumberLiteral || getNegativeNumber(¢) != null;
  }

  static boolean validTypes(final Expression ¢1, final Expression ¢2) {
    return ¢2 instanceof MethodInvocation && isNumber(¢1) //
        || isNumber(¢2) && ¢1 instanceof MethodInvocation;
  }

  private static ASTNode replacement(final InfixExpression e, Operator o, final MethodInvocation i, final Expression n) {
    if (!"size".equals(step.name(i).getIdentifier()))
      return null;
    int sign = -1;
    NumberLiteral l = getNegativeNumber(n);
    if (l == null) {
      /* should be unnecessary since validTypes uses isNumber so n is either a
       * NumberLiteral or an PrefixExpression which is a negative number */
      if (!(n instanceof NumberLiteral))
        return null;
      l = (NumberLiteral) n;
      sign = 1;
    }
    final Expression receiver = step.receiver(i);
    /* In case binding is available, uses it to ensure that isEmpty() is
     * accessible from current scope. Currently untested */
    if (e.getAST().hasResolvedBindings()) {
      final CompilationUnit u = hop.compilationUnit(e);
      if (u == null)
        return null;
      final IMethodBinding b = BindingUtils.getVisibleMethod(receiver == null ? BindingUtils.container(e) : receiver.resolveTypeBinding(), "isEmpty",
          null, e, u);
      if (b == null)
        return null;
      final ITypeBinding t = b.getReturnType();
      if (!"boolean".equals("" + t) && !"java.lang.Boolean".equals(t.getBinaryName()))
        return null;
    }
    final MethodInvocation $ = subject.operand(receiver).toMethod("isEmpty");
    int threshold = sign * Integer.parseInt(l.getToken());
    Expression notOf = make.notOf($);
    BooleanLiteral TRUE = e.getAST().newBooleanLiteral(true);
    BooleanLiteral FALSE = e.getAST().newBooleanLiteral(false);
    if (o == Operator.GREATER_EQUALS) {
      o = Operator.GREATER;
      --threshold;
    }
    if (o == Operator.LESS_EQUALS) {
      o = Operator.LESS;
      ++threshold;
    }
    if (o == Operator.EQUALS)
      return threshold == 0 ? $ : threshold < 0 ? FALSE : null;
    if (o == Operator.NOT_EQUALS)
      return threshold == 0 ? notOf : threshold >= 0 ? null : TRUE;
    if (o == Operator.GREATER)
      return threshold < 0 ? TRUE : threshold == 0 ? $ : null;
    if (o == Operator.LESS)
      return threshold <= 0 ? FALSE : threshold == 1 ? $ : null;
    assert false : o + ": uncrecognized";
    return null;
  }

  private static String descriptionAux(final Expression e) {
    return e == null ? "Use isEmpty()" : "Use " + e + ".isEmpty()";
  }

  @Override String description(final InfixExpression e) {
    final Expression right = step.right(e);
    final Expression left = step.left(e);
    return descriptionAux(left instanceof MethodInvocation ? left : right);
  }

  @Override ASTNode replacement(final InfixExpression e) {
    final Operator o = e.getOperator();
    if (!iz.comparison(o))
      return null;
    final Expression right = step.right(e);
    assert right != null;
    final Expression left = step.left(e);
    assert left != null;
    return !validTypes(right, left) ? null
        : left instanceof MethodInvocation ? //
            replacement(e, o, (MethodInvocation) left, right) //
            : replacement(e, wizard.conjugate(o), (MethodInvocation) right, left)//
    ;
  }
}
