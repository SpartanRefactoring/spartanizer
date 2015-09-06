package org.spartan.refactoring.wring;

import static org.eclipse.jdt.core.dom.Assignment.Operator.ASSIGN;
import static org.spartan.refactoring.utils.Funcs.*;
import static org.spartan.refactoring.wring.Wrings.size;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.text.edits.TextEditGroup;
import org.spartan.refactoring.utils.Extract;
import org.spartan.refactoring.wring.LocalNameReplacer.LocalNameReplacerWithValue;

/**
 * A {@link Wring} to convert <code>int a;
 * a = 3;</code> into <code>int a = 3;</code>
 *
 * @author Yossi Gil
 * @since 2015-08-07
 */
public final class DeclarationInitialiazerAssignment extends Wring.VariableDeclarationFragementAndStatement {
  @Override ASTRewrite go(final ASTRewrite r, final VariableDeclarationFragment f, final SimpleName n, final Expression initializer, final Statement nextStatement,
      final TextEditGroup g) {
        if (initializer == null)
          return null;
        final Assignment a = Extract.assignment(nextStatement);
        if (a == null || !same(n, left(a)) || a.getOperator() != ASSIGN)
          return null;
        final Expression newInitializer = duplicate(right(a));
        if (doesUseForbiddenSiblings(f, newInitializer))
          return null;
        final LocalNameReplacerWithValue i = new LocalNameReplacer(n, r, g).byValue(initializer);
        if (!i.canInlineInto(newInitializer) || i.replacedSize(newInitializer) - size(nextStatement, initializer) > 0)
          return null;
        r.replace(initializer, newInitializer, g);
        i.inlineInto(newInitializer);
        r.remove(nextStatement, g);
        return r;
      }
  @Override String description(final VariableDeclarationFragment n) {
    return "Consolidate declaration of " + n.getName() + " with its subsequent initialization";
  }
}