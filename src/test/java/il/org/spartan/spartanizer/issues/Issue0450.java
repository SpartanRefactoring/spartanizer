package il.org.spartan.spartanizer.issues;

import static il.org.spartan.spartanizer.testing.TestsUtilsTrimmer.*;

import org.eclipse.jdt.core.dom.*;
import org.junit.*;

import il.org.spartan.spartanizer.tippers.*;

/** see Issue0 #450 for more details This is a unit test for
 * {@link FragmentInitializerStatementTerminatingScope, @link
 * DeclarationFragmentInlineIntoNext}
 * @author Sapir Bismot
 * @since 16-11-30 */
@SuppressWarnings("static-method")
public class Issue0450 {
  private static final String SEPARATOR_CASE = "final Separator s = new Separator(\", \");" //
      + "for (final String a : args)" //
      + "System.out.print(s + a);"//
  ;

  @Test public void test0() {
    topDownTrimming(SEPARATOR_CASE)//
        .stays();
  }

  @Test public void test0a() {
    topDownTrimming(SEPARATOR_CASE)//
        .using(VariableDeclarationFragment.class, new LocalVariableIntializedStatementTerminatingScope())//
        .stays();
  }

  @Test public void test0b() {
    topDownTrimming(SEPARATOR_CASE)//
        .using(VariableDeclarationFragment.class, new LocalVariableIntializedInlineIntoNext()).stays();
  }

  @Test public void test1() {
    topDownTrimming("int x = 7;" //
        + "for (final String a : args)" //
        + "System.out.print(x+a.length());")
            .gives("for (final String a : args)" //
                + "System.out.print(7+a.length());");
  }

  @Test public void test2() {
    topDownTrimming("final Separator s = \"hello\";" //
        + "for (final String a : args)" //
        + "System.out.print(s + a);")
            .gives("for (final String a : args)" //
                + "System.out.print(\"hello\" + a);");
  }
}
