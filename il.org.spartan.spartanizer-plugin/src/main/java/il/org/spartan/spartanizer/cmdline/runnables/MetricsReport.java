package il.org.spartan.spartanizer.cmdline.runnables;

import static il.org.spartan.tide.*;

import java.util.*;

import org.eclipse.jdt.core.dom.*;

import fluent.ly.*;
import il.org.spartan.*;
import il.org.spartan.spartanizer.ast.navigate.*;
import il.org.spartan.spartanizer.ast.nodes.metrics.*;
import il.org.spartan.spartanizer.ast.safety.*;
import il.org.spartan.spartanizer.cmdline.runnables.ConfigurableReport.Settings.*;
import il.org.spartan.spartanizer.java.*;

/** Collects a set of metrics A wrapper for {@link CSVStatistics}
 * @author Matteo Orru'
 * @since Nov 14, 2016 */
// @SuppressWarnings("unused")
public class MetricsReport implements ConfigurableReport {
  List<ASTNode> l;
  private static final Settings settings = new Settings();

  public static void initialize() {
    if (settings.getInputFolder() == null)
      settings.setInputFolder(".");
    if (settings.getOutputFolder() == null)
      settings.setOutputFolder("/tmp");
    getSettings();
    Settings.setHeader("NEWmetrics");
    getSettings();
    Settings.setFileName("/tmp/NEWmetrics.CSV");
    settings.getAction().initialize();
  }
  public static Settings getSettings() {
    return settings;
  }

  @FunctionalInterface
  public interface ToIntFunction<R> {
    int run(R r);
  }

  @FunctionalInterface
  public interface BiFunction<T, R> {
    double apply(T t, R r);
  }


  public static Metric.Integral[] functions(final String id) {
    return as.array(Metric.named("length" + id).of((java.util.function.ToIntFunction<ASTNode>) λ -> (λ + "").length()), //
        Metric.named("essence" + id).of((java.util.function.ToIntFunction<ASTNode>) λ -> Essence.of(λ + "").length()), //
        Metric.named("tokens" + id).of((java.util.function.ToIntFunction<ASTNode>) λ -> Metrics.tokens(λ + "")), //
        Metric.named("nodes" + id).of((java.util.function.ToIntFunction<ASTNode>) countOf::nodes), //
        Metric.named("body" + id).of((java.util.function.ToIntFunction<ASTNode>) Metrics::bodySize), //
        Metric.named("methodDeclaration" + id).of((java.util.function.ToIntFunction<ASTNode>) λ -> az.methodDeclaration(λ) == null ? -1 : extract.statements(az.methodDeclaration(λ).getBody()).size()), //
        Metric.named("tide" + id).of((java.util.function.ToIntFunction<ASTNode>) λ -> clean(λ + "").length())); //
  }
  public static void write() {
    final Action wr = settings.getAction();
    wr.initialize();
    wr.go();
  }
  public static void generate() {
    write();
  }
}
