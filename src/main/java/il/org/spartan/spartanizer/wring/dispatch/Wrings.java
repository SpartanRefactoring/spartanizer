package il.org.spartan.spartanizer.wring.dispatch;

import static il.org.spartan.lisp.*;
import static il.org.spartan.spartanizer.ast.step.*;
import static org.eclipse.jdt.core.dom.ASTNode.*;

import java.util.*;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.*;
import org.eclipse.text.edits.*;

import il.org.spartan.*;
import il.org.spartan.spartanizer.assemble.*;
import il.org.spartan.spartanizer.ast.*;
import il.org.spartan.spartanizer.engine.*;
import il.org.spartan.spartanizer.java.*;

/** A number of utility functions common to all wrings.
 * @author Yossi Gil
 * @since 2015-07-17 */
public enum Wrings {
  ;
  public static void addAllReplacing(final List<Statement> to, final List<Statement> from, final Statement substitute, final Statement by1,
      final List<Statement> by2) {
    for (final Statement s : from)
      if (s != substitute)
        duplicate.into(s, to);
      else {
        duplicate.into(by1, to);
        duplicate.into(by2, to);
      }
  }

  public static IfStatement blockIfNeeded(final IfStatement s, final ASTRewrite r, final TextEditGroup g) {
    if (!iz.blockRequired(s))
      return s;
    final Block b = subject.statement(s).toBlock();
    r.replace(s, b, g);
    return (IfStatement) first(statements(b));
  }

  public static Expression eliminateLiteral(final InfixExpression x, final boolean b) {
    final List<Expression> operands = extract.allOperands(x);
    wizard.removeAll(b, operands);
    switch (operands.size()) {
      case 0:
        return x.getAST().newBooleanLiteral(b);
      case 1:
        return duplicate.of(first(operands));
      default:
        return subject.operands(operands).to(x.getOperator());
    }
  }

  public static boolean endsWithSequencer(final Statement ¢) {
    return iz.sequencer(hop.lastStatement(¢));
  }

  public static ListRewrite insertAfter(final Statement where, final List<Statement> what, final ASTRewrite r, final TextEditGroup g) {
    final ListRewrite $ = r.getListRewrite(where.getParent(), Block.STATEMENTS_PROPERTY);
    for (int i = what.size() - 1; i >= 0; --i)
      $.insertAfter(what.get(i), where, g);
    return $;
  }

  public static ListRewrite insertBefore(final Statement where, final List<Statement> what, final ASTRewrite r, final TextEditGroup g) {
    final ListRewrite $ = r.getListRewrite(where.getParent(), Block.STATEMENTS_PROPERTY);
    for (final Statement s : what)
      $.insertBefore(s, where, g);
    return $;
  }

  public static IfStatement invert(final IfStatement ¢) {
    return subject.pair(elze(¢), then(¢)).toNot(¢.getExpression());
  }

  public static IfStatement makeShorterIf(final IfStatement s) {
    final List<Statement> then = extract.statements(then(s));
    final List<Statement> elze = extract.statements(elze(s));
    final IfStatement $ = invert(s);
    if (then.isEmpty())
      return $;
    final IfStatement main = duplicate.of(s);
    if (elze.isEmpty())
      return main;
    final int rankThen = Wrings.sequencerRank(lisp.last(then));
    final int rankElse = Wrings.sequencerRank(lisp.last(elze));
    return rankElse > rankThen || rankThen == rankElse && !Wrings.thenIsShorter(s) ? $ : main;
  }

  public static boolean mixedLiteralKind(final List<Expression> xs) {
    if (xs.size() <= 2)
      return false;
    int previousKind = -1;
    for (final Expression e : xs)
      if (e instanceof NumberLiteral || e instanceof CharacterLiteral) {
        final int currentKind = new LiteralParser(e + "").type().ordinal();
        assert currentKind >= 0;
        if (previousKind == -1)
          previousKind = currentKind;
        else if (previousKind != currentKind)
          return true;
      }
    return false;
  }

  public static void rename(final SimpleName oldName, final SimpleName newName, final ASTNode region, final ASTRewrite r, final TextEditGroup g) {
    new Inliner(oldName, r, g).byValue(newName)//
        .inlineInto(Collect.usesOf(oldName).in(region).toArray(new Expression[] {}));
  }

  public static ASTRewrite replaceTwoStatements(final ASTRewrite r, final Statement what, final Statement by, final TextEditGroup g) {
    final Block parent = az.block(what.getParent());
    final List<Statement> siblings = extract.statements(parent);
    final int i = siblings.indexOf(what);
    siblings.remove(i);
    siblings.remove(i);
    siblings.add(i, by);
    final Block $ = parent.getAST().newBlock();
    duplicate.into(siblings, step.statements($));
    r.replace(parent, $, g);
    return r;
  }

  public static boolean shoudlInvert(final IfStatement s) {
    final int rankThen = sequencerRank(hop.lastStatement(then(s)));
    final int rankElse = sequencerRank(hop.lastStatement(elze(s)));
    return rankElse > rankThen || rankThen == rankElse && !Wrings.thenIsShorter(s);
  }

  public static boolean thenIsShorter(final IfStatement s) {
    final Statement then = then(s);
    final Statement elze = elze(s);
    if (elze == null)
      return true;
    final int s1 = metrics.lineCount(then);
    final int s2 = metrics.lineCount(elze);
    if (s1 < s2)
      return true;
    if (s1 > s2)
      return false;
    assert s1 == s2;
    final int n2 = extract.statements(elze).size();
    final int n1 = extract.statements(then).size();
    if (n1 < n2)
      return true;
    if (n1 > n2)
      return false;
    assert n1 == n2;
    final IfStatement $ = invert(s);
    return positivePrefixLength($) >= positivePrefixLength(invert($));
  }

  private static int positivePrefixLength(final IfStatement $) {
    return metrics.length($.getExpression(), then($));
  }

  private static int sequencerRank(final ASTNode ¢) {
    return iz.index(¢.getNodeType(), BREAK_STATEMENT, CONTINUE_STATEMENT, RETURN_STATEMENT, THROW_STATEMENT);
  }
}
