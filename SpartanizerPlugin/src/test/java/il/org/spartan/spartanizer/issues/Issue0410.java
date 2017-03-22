package il.org.spartan.spartanizer.issues;

import static il.org.spartan.azzert.*;
import static il.org.spartan.spartanizer.cmdline.GuessedContext.*;

import org.jetbrains.annotations.*;
import org.junit.*;

import il.org.spartan.*;
import il.org.spartan.spartanizer.cmdline.*;
import il.org.spartan.spartanizer.engine.nominal.*;

/** Test class for {@link GuessedContext} .
 * @since 2016 */
@Ignore
@SuppressWarnings("static-method")
public class Issue0410 {
  @Test public void dealWithBothKindsOfComment() {
    similar("if (b) {\n", "if (b) {;} { throw new Exception(); }");
  }

  @Test public void findVariable() {
    azzert.that(find("i"), is(EXPRESSION_LOOK_ALIKE));
  }

  @Test public void removeCommentsTest() {
    similar(trivia.removeComments("if (b) {\n"), "if (b) {} else { throw new Exception(); }");
  }

  private void similar(@NotNull final String s1, @NotNull final String s2) {
    azzert.that(trivia.essence(s2), is(trivia.essence(s1)));
  }
}
