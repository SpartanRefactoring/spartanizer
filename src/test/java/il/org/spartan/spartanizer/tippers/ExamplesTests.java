package il.org.spartan.spartanizer.tippers;

import static il.org.spartan.spartanizer.testing.TestsUtilsTrimmer.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import org.eclipse.jdt.core.dom.*;
import org.jetbrains.annotations.*;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;
import org.junit.runners.Parameterized.*;

import il.org.spartan.spartanizer.dispatch.*;
import il.org.spartan.spartanizer.tipping.*;
import il.org.spartan.utils.*;
import il.org.spartan.utils.Example.*;

/** A test generator for {@link Tipper}s, creating tests from their declared
 * examples (see {@link Tipper#examples}).
 * @author Ori Roth {@code ori.rothh@gmail.com}
 * @since 2017-03-09 */
@RunWith(Parameterized.class)
public class ExamplesTests {
  /** Current tipper, loaded dynamically. */
  private final Tipper<? extends ASTNode> tipper;

  /** Redirects examples to tests according to type */
  @Test public void converts() {
    Stream.of(tipper.examples()).filter(Converts.class::isInstance).map(Converts.class::cast).forEachOrdered(this::converts);
  }

  /** Redirects examples to tests according to type */
  @Test public void ignores() {
    Stream.of(tipper.examples()).filter(Ignores.class::isInstance).map(Ignores.class::cast).forEachOrdered(this::ignores);
  }

  private void ignores(@NotNull final Ignores ¢) {
    wrap(() -> trimmingOf(¢.get()).stays());
  }

  private void converts(@NotNull final Converts ¢) {
    wrap(() -> trimmingOf(¢.from()).gives(¢.to()));
  }

  private void wrap(@NotNull final Runnable test) {
    try {
      test.run();
    } catch (@NotNull final AssertionError x) {
      throw new AssertionError("Example failure at " + tipper.nanoName() + ":\n" + x.getMessage().trim(), x.getCause());
    }
  }

  public ExamplesTests(final Tipper<? extends ASTNode> tipper, @SuppressWarnings("unused") final String name) {
    this.tipper = tipper;
  }

  @Parameters(name = "{index}. {1}") //
  public static Collection<Object[]> data() {
    return allTippers().stream().map(λ -> new Object[] { λ, system.className(λ) }).collect(Collectors.toList());
  }

  /** Get all tippers from {@link Toolbox}. Removes duplicate tippers (same
   * class, different templates).
   * @return
   * @return all tippers to be tested */
  @SuppressWarnings("rawtypes") private static Collection<?> allTippers() {
    return Toolbox.freshCopyOfAllTippers().getAllTippers() //
        .stream()
        .collect(Collectors.toMap((Function<Tipper<? extends ASTNode>, ? extends Class<? extends Tipper>>) Tipper<? extends ASTNode>::getClass,
            λ -> λ, (t1, t2) -> t1))
        .values();
  }
}
