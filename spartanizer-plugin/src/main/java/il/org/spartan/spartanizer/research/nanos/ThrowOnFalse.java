package il.org.spartan.spartanizer.research.nanos;

import static il.org.spartan.spartanizer.research.TipperFactory.patternTipper;

import java.util.List;

import org.eclipse.jdt.core.dom.IfStatement;

import fluent.ly.as;
import il.org.spartan.spartanizer.research.UserDefinedTipper;
import il.org.spartan.spartanizer.research.nanos.common.NanoPatternTipper;
import il.org.spartan.spartanizer.tipping.Tip;

/** if(X) throw Exception;
 * @author Ori Marcovitch
 * @since Jan 8, 2017 */
public final class ThrowOnFalse extends NanoPatternTipper<IfStatement> {
  private static final long serialVersionUID = 0x7EF186D12E11879EL;
  private static final ThrowOnNull rival = new ThrowOnNull();
  private static final List<UserDefinedTipper<IfStatement>> tippers = as.list(//
      patternTipper("if($X1) throw $X2;", "holds(!($X1)).orThrow(()->$X2);", "IfThrow pattern. Go fluent!"));

  @Override public boolean canTip(final IfStatement ¢) {
    return anyTips(tippers, ¢) && rival.cantTip(¢);
  }
  @Override public Tip pattern(final IfStatement ¢) {
    return firstTip(tippers, ¢);
  }
  @Override public String description() {
    return "Throw if condition doesn't hold";
  }
}
