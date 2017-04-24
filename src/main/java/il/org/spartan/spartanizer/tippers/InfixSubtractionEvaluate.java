package il.org.spartan.spartanizer.tippers;

import static il.org.spartan.lisp.*;

import java.util.*;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.InfixExpression.*;

import il.org.spartan.spartanizer.ast.safety.*;
import il.org.spartan.spartanizer.engine.*;
import il.org.spartan.spartanizer.engine.type.Primitive.*;
import nano.ly.*;

/** Evaluate the subtraction of numbers according to the following rules {@code
 * int - int --> int
 * double - double --> double
 * long - long --> long
 * int - double --> double
 * int - long --> long
 * long - double --> double
 * }
 * @author Dor Ma'ayan
 * @since 2016 */
public final class InfixSubtractionEvaluate extends $EvaluateInfixExpression {
  private static final long serialVersionUID = 0x5D0DBA6EE527B4BL;

  @Override double evaluateDouble(final List<Expression> xs) {
    double $ = 0;
    try {
      $ = az.throwing.double¢(the.first(xs)) - az.stream(the.rest(xs)).mapToDouble(az.throwing::double¢).sum();
    } catch (final NumberFormatException ¢) {
      note.bug(this, ¢);
    }
    return $;
  }

  @Override int evaluateInt(final List<Expression> xs) {
    int $ = 0;
    try {
      $ = az.throwing.int¢(the.first(xs));
      for (final Expression ¢ : the.rest(xs)) {
        if (type.of(¢) == Certain.DOUBLE || type.of(¢) == Certain.LONG)
          throw new NumberFormatException();
        $ -= az.throwing.int¢(¢);
      }
    } catch (final NumberFormatException ¢) {
      note.bug(this, ¢);
    }
    return $;
  }

  @Override long evaluateLong(final List<Expression> xs) {
    long $ = 0;
    try {
      $ = az.throwing.long¢(the.first(xs));
      for (final Expression ¢ : the.rest(xs)) {
        if (type.of(¢) == Certain.DOUBLE)
          throw new NumberFormatException();
        $ -= az.throwing.long¢(¢);
      }
    } catch (final NumberFormatException ¢) {
      note.bug(this, ¢);
    }
    return $;
  }

  @Override String operation() {
    return "subtraction";
  }

  @Override Operator operator() {
    return il.org.spartan.spartanizer.ast.navigate.op.MINUS2;
  }
}
