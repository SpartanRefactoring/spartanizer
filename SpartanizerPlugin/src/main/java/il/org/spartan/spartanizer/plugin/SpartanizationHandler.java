package il.org.spartan.spartanizer.plugin;

import static il.org.spartan.spartanizer.plugin.Eclipse.*;
import static il.org.spartan.utils.fluent.English.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.*;

import org.eclipse.core.commands.*;
import org.eclipse.core.resources.*;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jface.dialogs.*;
import org.eclipse.ui.*;

import il.org.spartan.*;
import il.org.spartan.spartanizer.dispatch.*;
import il.org.spartan.utils.*;
import il.org.spartan.utils.fluent.*;

/** Both {@link AbstractHandler} and {@link IMarkerResolution} implementations
 * that uses {@link BatchApplicator} as its applicator.
 * @author Ori Roth
 * @since 2.6 */
public class SpartanizationHandler extends AbstractHandler implements IMarkerResolution {
  private static final English.Activity OPERATION_ACTIVITY = English.Activity.simple("Spartaniz");
  public static final int PASSES = 20;
  private static final int DIALOG_THRESHOLD = 2;

  @Override public Object execute(@SuppressWarnings("unused") final ExecutionEvent __) {
    final BatchApplicator a = applicator().defaultSelection();
    a.passes(a.selection().textSelection != null ? 1 : PASSES);
    a.go();
    return null;
  }

  @Override public String getLabel() {
    return "Apply";
  }

  @Override public void run(final IMarker ¢) {
    applicator().passes(1).selection(Selection.Util.by(¢)).go();
  }

  public static BatchApplicator applicator() {
    return applicator(OPERATION_ACTIVITY);
  }

  /** Creates and configures an applicator, without configuring the selection.
   * @return applicator for this handler */
  public static BatchApplicator applicator(final English.Activity activityNamer) {
    final BatchApplicator $ = new BatchApplicator();
    final Trimmer t = new Trimmer();
    final ProgressMonitorDialog d = Dialogs.progress(false);
    $.runContext(r -> {
      try {
        d.run(true, true, __ -> r.run());
      } catch (final InvocationTargetException ¢) {
        note.bug(¢);
      } catch (final InterruptedException ¢) {
        note.cancel(¢);
      }
    });
    $.defaultRunAction(t);
    $.listener(new Listener() {
      static final int DIALOG_CREATION = 1;
      static final int DIALOG_PROCESSING = 2;
      int level;
      boolean dialogOpen;
      int passes;
      int compilationUnitCount;
      long startTime;

      @Override public void tick(final Object... ¢) {
        runAsynchronouslyInUIThread(() -> {
          d.getProgressMonitor().subTask(English.trim(separate.these(¢).by(English.SEPARATOR)));
          d.getProgressMonitor().worked(1);
          if (d.getProgressMonitor().isCanceled())
            $.stop();
        });
        if (passes == 1)
          ++compilationUnitCount;
      }

      @Override public void push(final Object... ¢) {
        switch (++level) {
          case DIALOG_CREATION:
            if ($.selection().size() >= DIALOG_THRESHOLD)
              if (!Dialogs.ok(Dialogs.messageUnsafe(separate.these(¢).by(English.SEPARATOR))))
                $.stop();
              else {
                dialogOpen = true;
                runAsynchronouslyInUIThread(d::open);
              }
            startTime = System.nanoTime();
            break;
          case DIALOG_PROCESSING:
            if (dialogOpen)
              runAsynchronouslyInUIThread(() -> {
                d.getProgressMonitor().beginTask(English.trim($.name()) + " : " + separate.these(¢).by(English.SEPARATOR), $.selection().size());
                if (d.getProgressMonitor().isCanceled())
                  $.stop();
              });
            ++passes;
            break;
          default:
            break;
        }
      }

      /** see issue #467 */
      @Override public void pop(final Object... ¢) {
        if (level-- == DIALOG_CREATION && dialogOpen)
          Dialogs.messageUnsafe(separate.these(message.title.get(separate.these(¢).by(English.SEPARATOR)),
              message.passes.get(activityNamer.getEd(), Integer.valueOf(compilationUnitCount), Integer.valueOf(passes)),
              message.time.get(English.time(System.nanoTime() - startTime))).by("\n")).open();
      }
    });
    $.operationName(OPERATION_ACTIVITY);
    return $;
  }

  /** Creates and configures an applicator, without configuring the selection.
   * @return applicator for this handler */
  @Deprecated @SuppressWarnings("deprecation") public static BatchApplicator applicatorMapper() {
    final BatchApplicator $ = new BatchApplicator();
    final Trimmer t = new Trimmer();
    final ProgressMonitorDialog d = Dialogs.progress(false);
    final Bool openDialog = new Bool();
    $.listener(EventMapper.empty(event.class).expand(EventMapper.recorderOf(event.visit_cu).rememberBy(WrappedCompilationUnit.class).does((__, ¢) -> {
      if (openDialog.get())
        runAsynchronouslyInUIThread(() -> {
          d.getProgressMonitor().subTask(English.trim(the.nth($.selection().inner.indexOf(¢), $.selection().size()) + "\tSpartanizing " + ¢.name()));
          d.getProgressMonitor().worked(1);
          if (d.getProgressMonitor().isCanceled())
            $.stop();
        });
    })).expand(EventMapper.recorderOf(event.visit_node).rememberBy(ASTNode.class))
        .expand(EventMapper.recorderOf(event.visit_root).rememberLast(String.class))
        .expand(EventMapper.recorderOf(event.run_pass).counter().does(¢ -> {
          if (openDialog.get())
            runAsynchronouslyInUIThread(() -> {
              d.getProgressMonitor().beginTask(English.trim($.name()), $.selection().size());
              if (d.getProgressMonitor().isCanceled())
                $.stop();
            });
        })).expand(EventMapper.inspectorOf(event.run_start).does(λ -> {
          if ($.selection().size() >= DIALOG_THRESHOLD)
            if (!Dialogs.ok(Dialogs.message("Spartanizing " + unknownIfNull(λ.get(event.visit_root)))))
              $.stop();
            else {
              runAsynchronouslyInUIThread(d::open);
              openDialog.set();
            }
        })).expand(EventMapper.inspectorOf(event.run_finish).does(λ -> {
          if (openDialog.get())
            runAsynchronouslyInUIThread(d::close);
        }).does(¢ -> {
          if (openDialog.get())
            Dialogs.message("Done spartanizing " + unknownIfNull(¢.get(event.visit_root)) + "\nSpartanized " + unknownIfNull(¢.get(event.visit_root))
                + " with " + unknownIfNull((Collection<?>) ¢.get(event.visit_cu), λ -> Integer.valueOf(λ.size())) + " files in "
                + plurales("pass", (Int) ¢.get(event.run_pass))).open();
        })));
    $.runContext(r -> {
      try {
        d.run(true, true, __ -> r.run());
      } catch (final InvocationTargetException ¢) {
        note.bug(¢);
      } catch (final InterruptedException ¢) {
        note.cancel(¢);
      }
    });
    $.defaultRunAction(t);
    return $;
  }

  /** Printing definition.
   * @author Ori Roth
   * @since 2.6 */
  private enum message {
    title(1, λ -> λ[0] + ""), //
    passes(3, λ -> λ[0] + " " + λ[1] + " compilation units in " + English.plurales("pass", (Integer) λ[2])), //
    time(1, λ -> "Run time " + λ[0] + " seconds");
    private final int inputCount;
    private final Function<Object[], String> printing;

    message(final int inputCount, final Function<Object[], String> printing) {
      this.inputCount = inputCount;
      this.printing = printing;
    }

    public String get(final Object... ¢) {
      assert ¢.length == inputCount;
      return printing.apply(¢);
    }
  }
}