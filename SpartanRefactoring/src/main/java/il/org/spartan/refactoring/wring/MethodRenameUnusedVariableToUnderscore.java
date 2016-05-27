package il.org.spartan.refactoring.wring;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import il.org.spartan.refactoring.preferences.PluginPreferencesResources.WringGroup;
import il.org.spartan.refactoring.utils.Funcs;
import il.org.spartan.refactoring.wring.Wring.ReplaceCurrentNodeExclude;

/**
 * A {@link Wring} to change name of unused variable to double underscore "__"
 * TODO Ori: (maybe) inherent VariableChangeName instead of
 * ReplaceCurrentNodeExclude
 *
 * @author Ori Roth <code><ori.rothh [at] gmail.com></code>
 * @since 2016-05-08
 */
@SuppressWarnings("javadoc") public class MethodRenameUnusedVariableToUnderscore
    extends ReplaceCurrentNodeExclude<SingleVariableDeclaration> {
  // true iff renaming annotated variables only
  final static boolean BY_ANNOTATION = true;

  public static class IsUsed extends ASTVisitor {
    boolean c = true;
    String n;

    public IsUsed(SimpleName sn) {
      n = sn.getIdentifier();
    }
    public IsUsed(String sn) {
      n = sn;
    }
    public boolean conclusion() {
      return !c;
    }
    @Override public boolean visit(final SimpleName sn) {
      if (n.equals(sn.getIdentifier()))
        c = false;
      return c;
    }
    @Override public final boolean visit(@SuppressWarnings("unused") final AnnotationTypeDeclaration _) {
      return false;
    }
    @Override public final boolean visit(@SuppressWarnings("unused") final AnonymousClassDeclaration _) {
      return false;
    }
    @Override public final boolean visit(@SuppressWarnings("unused") final EnumDeclaration _) {
      return false;
    }
    @Override public final boolean visit(@SuppressWarnings("unused") final TypeDeclaration _) {
      return false;
    }
    @Override public boolean preVisit2(@SuppressWarnings("unused") ASTNode __) {
      return c;
    }
  }

  public static boolean isUsed(MethodDeclaration d, SimpleName n) {
    final IsUsed u = new IsUsed(n);
    d.getBody().accept(u);
    return u.conclusion();
  }
  @SuppressWarnings("unchecked") public static boolean suppressedUnused(SingleVariableDeclaration d) {
    for (final IExtendedModifier m : (Iterable<IExtendedModifier>) d.modifiers())
      if (m instanceof SingleMemberAnnotation && "SuppressWarnings".equals(((SingleMemberAnnotation) m).getTypeName().toString())) {
        final Expression e = ((SingleMemberAnnotation) m).getValue();
        if (e instanceof StringLiteral)
          return "unused".equals(((StringLiteral) e).getLiteralValue());
        for (final Expression x : (Iterable<Expression>) ((ArrayInitializer) ((SingleMemberAnnotation) m).getValue()).expressions())
          return x instanceof StringLiteral && "unused".equals(((StringLiteral) x).getLiteralValue());
        break;
      }
    return false;
  }
  @SuppressWarnings("unchecked") @Override ASTNode replacement(SingleVariableDeclaration n, final ExclusionManager m) {
    final ASTNode p = n.getParent();
    if (p == null || !(p instanceof MethodDeclaration))
      return null;
    final MethodDeclaration d = (MethodDeclaration) p;
    for (final SingleVariableDeclaration svd : (Iterable<SingleVariableDeclaration>) d.parameters())
      if (unusedVariableName().equals(svd.getName().getIdentifier()))
        return null;
    if (BY_ANNOTATION && !suppressedUnused(n) || isUsed(d, n.getName()))
      return null;
    if (m != null)
      for (final SingleVariableDeclaration svd : (Iterable<SingleVariableDeclaration>) d.parameters())
        if (!n.equals(svd))
          m.exclude(svd);
    final SingleVariableDeclaration $ = n.getAST().newSingleVariableDeclaration();
    $.setName(n.getAST().newSimpleName(unusedVariableName()));
    $.setFlags($.getFlags());
    $.setInitializer($.getInitializer());
    $.setType(Funcs.duplicate(n.getType()));
    // scalpel.duplicateInto(n.modifiers(), $.modifiers());
    return $;
  }
  private static String unusedVariableName() {
    return "__";
  }
  @Override String description(SingleVariableDeclaration d) {
    return "Change name of unused variable " + d.getName().getIdentifier() + " to __";
  }
  @Override WringGroup wringGroup() {
    return WringGroup.RENAME_PARAMETERS;
  }
}
