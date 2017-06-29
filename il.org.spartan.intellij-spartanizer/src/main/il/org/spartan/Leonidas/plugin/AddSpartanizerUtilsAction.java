package il.org.spartan.Leonidas.plugin;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import il.org.spartan.Leonidas.auxilary_layer.Utils;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;

/**
 * @author Amir Sagiv
 * @since 14/06/2017
 */
public class AddSpartanizerUtilsAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent ¢) {
        new WriteCommandAction.Simple(¢.getProject()) {
            @Override
            protected void run() throws Throwable {
                createEnvironment(¢);
            }
        }.execute();

    }

    public void createEnvironment(AnActionEvent e) {
        PsiFile pf;
        try {
            pf = getPsiClassFromContext(e).getContainingFile();
        }catch (NullPointerException exception){
            return;
        }
        PsiDirectory srcDir = pf.getContainingDirectory();
        // creates the directory and adds the file if needed
        try {
            srcDir.checkCreateSubdirectory("spartanizer");
            pf = createUtilsFile(srcDir.createSubdirectory("spartanizer"));
        } catch (IncorrectOperationException x) {
            PsiDirectory pd = Arrays.stream(srcDir.getSubdirectories()).filter(λ -> "spartanizer".equals(λ.getName())).findAny().get();
            try {
                pf = Arrays.stream(pd.getFiles()).noneMatch(λ -> "SpartanizerUtils.java".equals(λ.getName()))
                        ? createUtilsFile(pd)
                        : Arrays.stream(pd.getFiles()).filter(λ -> "SpartanizerUtils.java".equals(λ.getName())).findFirst()
                        .get();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


    @Nullable
    private PsiClass getPsiClassFromContext(AnActionEvent ¢) {
        return PsiTreeUtil.getParentOfType(getPsiElementFromContext(¢), PsiClass.class);
    }


    @Nullable
    private PsiElement getPsiElementFromContext(AnActionEvent e) {
        PsiFile $ = e.getData(LangDataKeys.PSI_FILE);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        return $ == null || editor == null ? null : $.findElementAt(editor.getCaretModel().getOffset());
    }

    @SuppressWarnings({"OptionalGetWithoutIsPresent", "ResultOfMethodCallIgnored"})
    private PsiFile createUtilsFile(PsiDirectory d) throws IOException {
        URL is = getClass().getResource("/spartanizer/SpartanizerUtils.java");
        File file = new File(is.getPath());
        FileType type = FileTypeRegistry.getInstance().getFileTypeByFileName(file.getName());
        file.setReadable(true, false);
        PsiFile $ = PsiFileFactory.getInstance(Utils.getProject()).createFileFromText("SpartanizerUtils.java", type, IOUtils.toString(new BufferedReader(
				new InputStreamReader(getClass().getResourceAsStream("/spartanizer/SpartanizerUtils.java")))));
        d.add($);
        Arrays.stream(d.getFiles()).filter(λ -> "SpartanizerUtils.java".equals(λ.getName())).findFirst().get().getVirtualFile().setWritable(false);
        Toolbox.getInstance().excludeFile($);
        return $;
    }
}