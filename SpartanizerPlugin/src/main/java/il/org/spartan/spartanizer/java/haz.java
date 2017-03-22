package il.org.spartan.spartanizer.java;

import static il.org.spartan.spartanizer.ast.navigate.step.*;

import java.util.*;
import java.util.function.*;

import org.eclipse.jdt.core.dom.*;

import il.org.spartan.*;
import il.org.spartan.spartanizer.ast.navigate.*;
import il.org.spartan.spartanizer.ast.safety.*;
import il.org.spartan.spartanizer.engine.*;
import il.org.spartan.spartanizer.engine.nominal.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** An empty {@code enum} for fluent programming. The name should say it all:
 * The name, followed by a dot, followed by a method name, should read like a
 * sentence phrase.
 * @author Yossi Gil {@code Yossi.Gil@GMail.COM}
 * @since 2016-09-12 */
public enum haz {
  ;
  public static boolean annotation(@NotNull final VariableDeclarationFragment ¢) {
    return annotation((VariableDeclarationStatement) ¢.getParent());
  }

  public static boolean annotation(final VariableDeclarationStatement ¢) {
    return !extract.annotations(¢).isEmpty();
  }

  /** @param ¢ JD
   * @return */
  public static boolean anyStatements(@Nullable final MethodDeclaration ¢) {
    return ¢ != null && statements(¢) != null && !statements(¢).isEmpty();
  }

  public static boolean binding(@Nullable final ASTNode ¢) {
    return ¢ != null && ¢.getAST() != null && ¢.getAST().hasResolvedBindings();
  }

  /** Determines whether the method's return type is boolean.
   * @param ¢ method
   * @return */
  public static boolean booleanReturnType(@Nullable final MethodDeclaration ¢) {
    return ¢ != null && returnType(¢) != null && iz.booleanType(returnType(¢));
  }

  public static boolean cent(final ASTNode ¢) {
    return !collect.usesOf(namer.it).inside(¢).isEmpty();
  }

  /** Determine whether an {@link ASTNode} contains as a children a
   * {@link ContinueStatement}
   * @param ¢ JD
   * @return {@code true } iff ¢ contains any continue statement
   * @see {@link convertWhileToFor} */
  @SuppressWarnings("boxing") public static boolean continueStatement(@Nullable final ASTNode ¢) {
    return ¢ != null
        && new Recurser<>(¢, 0).postVisit(λ -> λ.getRoot().getNodeType() != ASTNode.CONTINUE_STATEMENT ? λ.getCurrent() : λ.getCurrent() + 1) > 0;
  }

  public static boolean dollar(final ASTNode ¢) {
    return !collect.usesOf("$").inside(¢).isEmpty();
  }

  public static boolean dollar(@NotNull final Collection<SimpleName> ns) {
    return ns.stream().anyMatch(λ -> "$".equals(identifier(λ)));
  }

  /** @param ¢ JD
   * @return */
  public static boolean expression(@Nullable final MethodInvocation ¢) {
    return ¢ != null && step.expression(¢) != null;
  }

  public static boolean final¢(@NotNull final Collection<IExtendedModifier> ms) {
    return ms.stream().anyMatch(λ -> IExtendedModifiersRank.find(λ) == IExtendedModifiersRank.FINAL);
  }

  static boolean hasAnnotation(@NotNull final Collection<IExtendedModifier> ¢) {
    return ¢.stream().anyMatch(IExtendedModifier::isAnnotation);
  }

  public static boolean hasNoModifiers(@NotNull final BodyDeclaration ¢) {
    return !¢.modifiers().isEmpty();
  }

  public static boolean hidings(@NotNull final List<Statement> ss) {
    return new Predicate<List<Statement>>() {
      final Collection<String> dictionary = new HashSet<>();

      boolean ¢(@NotNull final CatchClause ¢) {
        return ¢(¢.getException());
      }

      boolean ¢(final ForStatement ¢) {
        return ¢(initializers(¢));
      }

      boolean ¢(@NotNull final Collection<Expression> xs) {
        return xs.stream().anyMatch(λ -> iz.variableDeclarationExpression(λ) && ¢(az.variableDeclarationExpression(λ)));
      }

      boolean ¢(final SimpleName ¢) {
        return ¢(identifier(¢));
      }

      boolean ¢(final SingleVariableDeclaration ¢) {
        return ¢(step.name(¢));
      }

      boolean ¢(final Statement ¢) {
        return ¢ instanceof VariableDeclarationStatement ? ¢((VariableDeclarationStatement) ¢) //
            : ¢ instanceof ForStatement ? ¢((ForStatement) ¢) //
                : ¢ instanceof TryStatement && ¢((TryStatement) ¢);
      }

      boolean ¢(final String ¢) {
        if (dictionary.contains(¢))
          return true;
        dictionary.add(¢);
        return false;
      }

      boolean ¢(final TryStatement ¢) {
        return ¢¢¢(resources(¢)) || ¢¢(catchClauses(¢));
      }

      boolean ¢(final VariableDeclarationExpression ¢) {
        return ¢¢¢¢(fragments(¢));
      }

      boolean ¢(final VariableDeclarationFragment ¢) {
        return ¢(step.name(¢));
      }

      boolean ¢(final VariableDeclarationStatement ¢) {
        return ¢¢¢¢(fragments(¢));
      }

      boolean ¢¢(@NotNull final Collection<CatchClause> cs) {
        return cs.stream().anyMatch(this::¢);
      }

      boolean ¢¢¢(@NotNull final Collection<VariableDeclarationExpression> xs) {
        return xs.stream().anyMatch(this::¢);
      }

      boolean ¢¢¢¢(@NotNull final Collection<VariableDeclarationFragment> fs) {
        return fs.stream().anyMatch(this::¢);
      }

      @Override public boolean test(@NotNull final List<Statement> ¢¢) {
        return ¢¢.stream().anyMatch(this::¢);
      }
    }.test(ss);
  }

  /** @param ¢ JD
   * @return */
  public static boolean methods(final AbstractTypeDeclaration ¢) {
    return step.methods(¢) != null && !step.methods(¢).isEmpty();
  }

  public static boolean sideEffects(final Expression ¢) {
    return !sideEffects.free(¢);
  }

  public static boolean unknownNumberOfEvaluations(final MethodDeclaration d) {
    @NotNull final Block $ = body(d);
    return $ != null && statements($).stream().anyMatch(λ -> Coupling.unknownNumberOfEvaluations(d, λ));
  }

  public static boolean variableDefinition(@NotNull final ASTNode n) {
    @NotNull final Wrapper<Boolean> $ = new Wrapper<>(Boolean.FALSE);
    n.accept(new ASTVisitor(true) {
      boolean continue¢(@NotNull final Collection<VariableDeclarationFragment> fs) {
        return fs.stream().anyMatch(λ -> continue¢(step.name(λ)));
      }

      boolean continue¢(@NotNull final SimpleName ¢) {
        if (iz.identifier("$", ¢))
          return false;
        $.set(Boolean.TRUE);
        return true;
      }

      @Override public boolean visit(final EnumConstantDeclaration ¢) {
        return continue¢(step.name(¢));
      }

      @Override public boolean visit(final FieldDeclaration node) {
        return continue¢(fragments(node));
      }

      @Override public boolean visit(@NotNull final SingleVariableDeclaration node) {
        return continue¢(node.getName());
      }

      @Override public boolean visit(final VariableDeclarationExpression node) {
        return continue¢(fragments(node));
      }

      @Override public boolean visit(final VariableDeclarationFragment ¢) {
        return continue¢(step.name(¢));
      }

      @Override public boolean visit(final VariableDeclarationStatement ¢) {
        return continue¢(fragments(¢));
      }
    });
    return $.get().booleanValue();
  }

  public static boolean hasObject(@Nullable final List<Type> ¢) {
    return ¢ != null && ¢.stream().anyMatch(wizard::isObject);
  }

  public static boolean hasSafeVarags(final MethodDeclaration d) {
    return extract.annotations(d).stream().anyMatch(λ -> iz.identifier("SafeVarargs", λ.getTypeName()));
  }
}
