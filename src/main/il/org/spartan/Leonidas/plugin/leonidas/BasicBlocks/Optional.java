package il.org.spartan.Leonidas.plugin.leonidas.BasicBlocks;

import com.intellij.psi.PsiElement;
import il.org.spartan.Leonidas.auxilary_layer.az;
import il.org.spartan.Leonidas.auxilary_layer.iz;
import il.org.spartan.Leonidas.auxilary_layer.step;
import il.org.spartan.Leonidas.plugin.leonidas.KeyDescriptionParameters;
import il.org.spartan.Leonidas.plugin.leonidas.Pruning;

/**
 * @author Oren Afek, michalcohen
 * @since 11-05-2017.
 */
public class Optional extends GenericMethodCallBasedBlock {
    private static final String TEMPLATE = "optional";
    Encapsulator internal;
    boolean active = true;

    public Optional(PsiElement e, Encapsulator i) {
        super(e, TEMPLATE);
        internal = i;
    }

    public Optional() {
        super(TEMPLATE);
    }

    @Override
    public boolean generalizes(Encapsulator e) {
        return iz.conforms(internal, e);
    }

    @Override
    protected boolean goUpwards(Encapsulator prev, Encapsulator next) {
        return iz.generic(internal) && az.generic(internal).goUpwards(prev, next);
    }

    @Override
    public int extractId(PsiElement e) {
        assert iz.generic(internal);
        return az.generic(internal).extractId(step.firstParameterExpression(az.methodCallExpression(e)));
    }

    @Override
    public Optional create(Encapsulator e) {
        PsiElement p = step.firstParameterExpression(az.methodCallExpression(e.getInner()));
        return new Optional(e.getInner(), Pruning.prune(Encapsulator.buildTreeFromPsi(p)));
    }

    public void setActivate(boolean b) {
        active = b;
    }

    @Override
    public boolean isGeneric() {
        return internal.isGeneric();
    }

    @Override
    public Encapsulator prune(Encapsulator e) {
        assert conforms(e.getInner());
        Optional o = create(e);
        Encapsulator upperElement = o.getConcreteParent(e);
        o.inner = upperElement.inner;
        if (o.isGeneric())
            upperElement.putUserData(KeyDescriptionParameters.ID, o.extractId(e.getInner()));//o
        return upperElement.getParent() == null ? upperElement : upperElement.generalizeWith(o);
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public boolean exists() {
        return active;
    }
}
