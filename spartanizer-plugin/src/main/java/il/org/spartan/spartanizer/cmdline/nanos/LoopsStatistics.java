package il.org.spartan.spartanizer.cmdline.nanos;

import static org.eclipse.jdt.core.dom.ASTNode.DO_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.ENHANCED_FOR_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.FOR_STATEMENT;
import static org.eclipse.jdt.core.dom.ASTNode.WHILE_STATEMENT;

import org.eclipse.jdt.core.dom.ASTNode;

import fluent.ly.unbox;
import il.org.spartan.spartanizer.ast.navigate.wizard;
import il.org.spartan.spartanizer.utils.format;

/** Collects statistics of loops, including hits by nano patterns.
 * @author orimarco <marcovitch.ori@gmail.com>
 * @since Jan 21, 2017 */
public class LoopsStatistics extends NanoPatternsOccurencesStatistics {
  private static final long serialVersionUID = -0x2E8F85DE0AF69FEBL;

  public int total() {
    return wizard.loopTypes.stream().mapToInt(λ -> total(unbox.it(λ))).sum();
  }
  public int covered() {
    return wizard.loopTypes.stream().mapToInt(λ -> covered(unbox.it(λ))).sum();
  }
  public double coverage() {
    return format.perc(covered(), total());
  }
  @Override public void logNode(final ASTNode ¢) {
    countNode(¢);
  }
  public int totalDoWhile() {
    return total(DO_STATEMENT);
  }
  public int totalWhile() {
    return total(WHILE_STATEMENT);
  }
  public int totalFor() {
    return total(FOR_STATEMENT);
  }
  public int totalEnhanced() {
    return total(ENHANCED_FOR_STATEMENT);
  }
}
