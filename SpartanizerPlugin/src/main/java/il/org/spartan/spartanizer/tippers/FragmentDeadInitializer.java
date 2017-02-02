package il.org.spartan.spartanizer.tippers;

import org.eclipse.jdt.core.dom.*;

import static il.org.spartan.spartanizer.ast.navigate.wizard.*;

import static il.org.spartan.spartanizer.ast.navigate.step.*;

import il.org.spartan.spartanizer.ast.factory.*;
import il.org.spartan.spartanizer.ast.navigate.*;
import il.org.spartan.spartanizer.ast.safety.*;
import il.org.spartan.spartanizer.dispatch.*;
import il.org.spartan.spartanizer.tipping.*;
import org.jetbrains.annotations.NotNull;

/** TODO: Yossi Gil <yossi.gil@gmail.com> please add a description
 * @author Yossi Gil <yossi.gil@gmail.com>
 * @since Jan 24, 2017 */
public final class FragmentDeadInitializer extends ReplaceCurrentNode<VariableDeclarationFragment>
    //
    implements TipperCategory.SyntacticBaggage {
  @Override public String description() {
    return "Remove default values initiliazing field";
  }

  @NotNull
  @Override public String description(@NotNull final VariableDeclarationFragment ¢) {
    return "Remove default initializer " + ¢.getInitializer() + " of field " + ¢.getName();
  }

  @Override public VariableDeclarationFragment replacement(@NotNull final VariableDeclarationFragment f) {
    final FieldDeclaration parent = az.fieldDeclaration(parent(f));
    if (parent == null || Modifier.isFinal(parent.getModifiers()))
      return null;
    final Expression e = f.getInitializer();
    if (e == null || !iz.literal(e) || wizard.isDefaultLiteral(e) || isBoxedType(parent.getType() + "") && !iz.nullLiteral(e)
        || iz.interface¢(containing.typeDeclaration(parent)))
      return null;
    final VariableDeclarationFragment $ = copy.of(f);
    $.setInitializer(null);
    return $;
  }
}