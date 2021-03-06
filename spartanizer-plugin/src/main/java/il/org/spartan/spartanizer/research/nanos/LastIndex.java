package il.org.spartan.spartanizer.research.nanos;

import static il.org.spartan.spartanizer.ast.navigate.step.parent;
import static il.org.spartan.spartanizer.research.TipperFactory.patternTipper;

import java.util.List;

import org.eclipse.jdt.core.dom.InfixExpression;

import fluent.ly.as;
import il.org.spartan.spartanizer.ast.safety.az;
import il.org.spartan.spartanizer.research.UserDefinedTipper;
import il.org.spartan.spartanizer.research.nanos.common.NanoPatternTipper;
import il.org.spartan.spartanizer.tipping.Tip;

/** @nano last index in collection, lisp style
 * @author orimarco {@code marcovitch.ori@gmail.com}
 * @since 2016-12-20 */
public final class LastIndex extends NanoPatternTipper<InfixExpression> {
  private static final long serialVersionUID = -0x4FFF6C945B72AF85L;
  private static final List<UserDefinedTipper<InfixExpression>> tippers = as.list(patternTipper("$X.size()-1", "lastIndex($X)", "lisp: lastIndex"));
  static final Last rival = new Last();

  @Override public boolean canTip(final InfixExpression ¢) {
    return anyTips(tippers, ¢) && rival.cantTip(az.methodInvocation(parent(¢)));
  }
  @Override public Tip pattern(final InfixExpression ¢) {
    return firstTip(tippers, ¢);
  }
  @Override public Category category() {
    return Category.Functional;
  }
  @Override public String description() {
    return "Index of last element in collection";
  }
  @Override public String example() {
    return firstPattern(tippers);
  }
  @Override public String symbolycReplacement() {
    return firstReplacement(tippers);
  }
}
