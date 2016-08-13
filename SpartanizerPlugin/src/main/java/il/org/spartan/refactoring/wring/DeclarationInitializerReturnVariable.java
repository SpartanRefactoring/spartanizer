package il.org.spartan.refactoring.wring;

import static il.org.spartan.refactoring.utils.Funcs.*;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.*;
import org.eclipse.text.edits.*;

import il.org.spartan.refactoring.preferences.PluginPreferencesResources.*;
import il.org.spartan.refactoring.utils.*;

/** A {@link Wring} to convert
 *
 * <pre>
 * int a = 3;
 * return a;
 * </pre>
 *
 * into
 *
 * <pre>
 * return a;
 * </pre>
 *
 * https://docs.oracle.com/javase/tutorial/java/nutsandbolts/op1.html
 * @author Yossi Gil
 * @since 2015-08-07 */
public final class DeclarationInitializerReturnVariable extends Wring.VariableDeclarationFragementAndStatement {
  @Override String description(final VariableDeclarationFragment f) {
    return "Eliminate temporary " + f.getName() + " and return its value";
  }
  @Override ASTRewrite go(final ASTRewrite r, final VariableDeclarationFragment f, final SimpleName n, final Expression initializer,
      final Statement nextStatement, final TextEditGroup g) {
    if (initializer == null || hasAnnotation(f) || initializer instanceof ArrayInitializer)
      return null;
    final ReturnStatement s = asReturnStatement(nextStatement);
    if (s == null)
      return null;
    final Expression returnValue = extract.expression(s);
    if (returnValue == null || !same(n, returnValue))
      return null;
    eliminate(f, r, g);
    r.replace(s, Subject.operand(initializer).toReturn(), g);
    return r;
  }
  @Override WringGroup wringGroup() {
    return WringGroup.ELIMINATE_TEMP;
  }
}