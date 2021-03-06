package il.org.spartan.spartanizer.tippers;

import java.util.List;

import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Assignment.Operator;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.text.edits.TextEditGroup;

import il.org.spartan.spartanizer.ast.factory.misc;
import il.org.spartan.spartanizer.ast.navigate.extract;
import il.org.spartan.spartanizer.ast.navigate.step;
import il.org.spartan.spartanizer.ast.safety.az;
import il.org.spartan.spartanizer.tipping.EagerTipper;
import il.org.spartan.spartanizer.tipping.Tip;
import il.org.spartan.spartanizer.tipping.categories.Nominal;

/** Rename parameter names in constructor, to match fields, if they are assigned
 * to them for example: <br/>
 * {@code class A {int x;A(int y,int z) {this.x = z;}}} <br/>
 * to: <br/>
 * {@code class A {int x;A(int y,int x) {this.x = x;}}} <br/>
 * @author dormaayn <tt>dor.d.ma@gmail.com</tt>
 * @since 2017-03-28 */
public class ConstructorRenameParameters extends EagerTipper<MethodDeclaration> implements Nominal.Fields {
  private static final long serialVersionUID = -0x6A3AE2731FF74B0BL;

  @Override public Tip tip(final MethodDeclaration d) {
    if (!d.isConstructor())
      return null;
    final List<String> parameterNames = step.parametersNames(d);
    final List<Statement> statements = extract.statements(d.getBody());
    for (final Statement s : statements) {
      final Assignment a = az.assignment(step.expression(az.expressionStatement(s)));
      if (a == null || a.getOperator() != Operator.ASSIGN)
        continue;
      final SimpleName ret = az.simpleName(step.from(a));
      if (ret == null || !parameterNames.contains(ret + ""))
        continue;
      final FieldAccess fieldAccess = az.fieldAccess(step.to(a));
      if (fieldAccess == null)
        continue;
      final SimpleName field = fieldAccess.getName();
      if (!parameterNames.contains(field + ""))
        return new Tip(description(d), getClass(), d) {
          @Override public void go(final ASTRewrite r, final TextEditGroup g) {
            final SimpleName to1 = d.getAST().newSimpleName(field + ""), $ = d.getAST().newSimpleName(ret + "");
            for (final SingleVariableDeclaration q : step.parameters(d))
              misc.rename($, to1, q, r, g);
            misc.rename($, to1, d.getBody(), r, g);
          }
        };
    }
    return null;
  }
  @Override public String description(@SuppressWarnings("unused") final MethodDeclaration __) {
    return "Rename constructor parameters to match fields names";
  }
}