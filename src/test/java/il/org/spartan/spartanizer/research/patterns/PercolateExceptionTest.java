package il.org.spartan.spartanizer.research.patterns;

import static il.org.spartan.spartanizer.tippers.TrimmerTestsUtils.*;

import org.eclipse.jdt.core.dom.*;
import org.junit.*;

/** @author orimarco <tt>marcovitch.ori@gmail.com</tt>
 * @since 2017-01-05 */
@SuppressWarnings("static-method")
public class PercolateExceptionTest {
  @Test public void a() {
    trimmingOf(//
        "try {" + //
            "    A.a(b).c().d(e -> f[g++]=h(e));" + //
            "  }" + //
            " catch (  B i) { throw i;}"//
    ).using(CatchClause.class, new PercolateException())//
        .gives("{try{{A.a(b).c().d(e->f[g++]=h(e));}}catch(B i){percolate(i);};}")//
        .gives("try{{A.a(b).c().d(e->f[g++]=h(e));}}catch(B i){percolate(i);}")//
        .gives("try{A.a(b).c().d(e->f[g++]=h(e));}catch(B i){percolate(i);}")//
        .stays()//
    ;
  }

  @Test public void b() {
    trimmingOf("try{ thing(); } catch(A ¢){ throw ¢;}catch(B ¢){ throw ¢;}")//
        .gives("try{thing();}catch(B|A ¢){throw ¢;}")//
        .using(CatchClause.class, new PercolateException())//
        .gives("{try{{thing();}}catch(B|A ¢){percolate(¢);};}")//
        .gives("try{{thing();}}catch(B|A ¢){percolate(¢);}")//
        .gives("try{thing();}catch(B|A ¢){percolate(¢);}")//
        .stays();
  }
}
