package il.org.spartan.spartanizer.engine;

import static il.org.spartan.spartanizer.ast.navigate.step.body;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;

import fluent.ly.the;
import il.org.spartan.spartanizer.ast.navigate.step;

/** A utility class used to scan statements of a {@link MethodDeclaration}.
 * @author Ori Roth
 * @since 2016 */
public abstract class MethodScanner {
  protected final MethodDeclaration method;
  protected final List<Statement> statements;
  protected Statement currentStatement;
  protected int currentIndex;

  public MethodScanner(final MethodDeclaration method) {
    assert method != null;
    this.method = method;
    if (body(method) == null) {
      statements = null;
      currentStatement = null;
    } else {
      statements = step.statements(body(method));
      currentStatement = the.firstOf(statements);
    }
    currentIndex = -1;
  }
  /** @return List of available statements from the method to be scanned. */
  protected abstract List<Statement> availableStatements();
  /** @return List of available statements. Updates the current statement and
   *         the current index while looping. */
  public Iterable<Statement> statements() {
    return () -> new Iterator<>() {
      final Iterator<Statement> i = availableStatements().iterator();

      @Override public boolean hasNext() {
        return i.hasNext();
      }
      @Override public Statement next() {
        final Statement $ = i.next();
        ++currentIndex;
        return $;
      }
    };
  }
}