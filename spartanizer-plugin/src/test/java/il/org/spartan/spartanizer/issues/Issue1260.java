package il.org.spartan.spartanizer.issues;

import static org.hamcrest.CoreMatchers.is;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

import fluent.ly.azzert;
import fluent.ly.idiomatic;
import il.org.spartan.utils.BooleanOrReducer;
import il.org.spartan.utils.CountingReduce;
import il.org.spartan.utils.FirstNotNullReduce;
import il.org.spartan.utils.ReduceCollectionsAdd;

/** Tests for reducers
 * @author oran1248
 * @since 2017-04-20 */
@SuppressWarnings("static-method")
public class Issue1260 {
  @Test public void list() {
    final ReduceCollectionsAdd<Integer, List<Integer>> r = new ReduceCollectionsAdd<>() {
      // TODO Oran.--should the class be made non-abstract?
    };
    azzert.that(IntStream.range(1, 31).boxed().collect(Collectors.toList()), is(r.reduce(IntStream.range(1, 11).boxed().collect(Collectors.toList()),
        IntStream.range(11, 21).boxed().collect(Collectors.toList()), IntStream.range(21, 31).boxed().collect(Collectors.toList()))));
    azzert.isNull(r.reduce());
    final List<Integer> l = new LinkedList<>();
    azzert.that(l, is(r.reduce(l)));
  }
  @Test @SuppressWarnings("boxing") public void integer() {
    final CountingReduce r = new CountingReduce();
    azzert.that(idiomatic.box(0), is(r.reduce()));
    azzert.that(idiomatic.box(1), is(r.reduce(1)));
    azzert.that(idiomatic.box(3), is(r.reduce(1, 2)));
    azzert.that(idiomatic.box(6), is(r.reduce(1, 2, 3)));
  }
  @Test @SuppressWarnings("boxing") public void firstNonNull() {
    final FirstNotNullReduce<Integer> r = new FirstNotNullReduce<>();
    azzert.that(null, is(r.reduce()));
    azzert.that(idiomatic.box(1), is(r.reduce(1, null, 2, null)));
    azzert.that(idiomatic.box(2), is(r.reduce(null, null, 2, 3)));
  }
  @Test @SuppressWarnings("boxing") public void booleanOr() {
    final BooleanOrReducer r = new BooleanOrReducer();
    azzert.that(idiomatic.box(false), is(r.reduce()));
    azzert.that(idiomatic.box(false), is(r.reduce(null, null)));
    azzert.that(idiomatic.box(true), is(r.reduce(null, false, true, false)));
  }
}
