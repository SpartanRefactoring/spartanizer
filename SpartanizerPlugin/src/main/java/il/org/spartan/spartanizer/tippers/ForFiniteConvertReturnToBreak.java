package il.org.spartan.spartanizer.tippers;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.*;
import org.eclipse.text.edits.*;

import il.org.spartan.spartanizer.ast.navigate.*;
import il.org.spartan.spartanizer.ast.safety.*;
import il.org.spartan.spartanizer.patterns.*;
import il.org.spartan.spartanizer.tipping.*;
import il.org.spartan.utils.*;
import il.org.spartan.utils.fluent.*;

/** Convert Finite loops with return sideEffects to shorter ones : toList
 * Convert {@code for (..) { does(something); return XX; } return XX; } to :
 * {@code for (..) { does(something); break; } return XX; }
 * @author Dor Ma'ayan
 * @since 2016-09-07 */
public final class ForFiniteConvertReturnToBreak extends ForStatementPattern//
    implements TipperCategory.CommnonFactoring {
  private static final long serialVersionUID = -0x307C6039058B998DL;

  public ForFiniteConvertReturnToBreak() {
    andAlso("Loop must be finite", //
        () -> iz.finiteLoop(current));
    andAlso("Loop must be followed by return", //
        () -> not.null¢(nextReturn = //
            az.returnStatement(nextStatement)));
    andAlso("Loop a return that can be converted to break", //
        () -> not.null¢(convertToBreak = //
            compute//
                .returns(body)//
                .stream().filter(λ -> wizard.eq(λ, nextReturn))//
                .findFirst()//
                .orElse(null)//
        ));
  }

  @Override public String description() {
    return "Convert " + convertToBreak + " to loop break";
  }

  @Override public Examples examples() {
    return convert("for (;x();) { if(x) return XX; } return XX;")//
        .to("for (;x();) { if(x) break; } return XX; }");
  }

  @Override protected ASTRewrite go(final ASTRewrite r, final TextEditGroup g) {
    r.replace(convertToBreak, current.getAST().newBreakStatement(), g);
    return r;
  }

  @Override protected ASTNode highlight() {
    return convertToBreak;
  }

  private ReturnStatement convertToBreak;
  private ReturnStatement nextReturn;
}
