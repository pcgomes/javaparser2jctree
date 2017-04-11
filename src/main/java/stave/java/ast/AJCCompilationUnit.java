package stave.java.ast;

import com.sun.tools.javac.code.Scope.ImportScope;
import com.sun.tools.javac.code.Scope.StarImportScope;
import com.sun.tools.javac.code.Symbol.PackageSymbol;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;
import com.sun.tools.javac.util.List;
import javax.tools.JavaFileObject;

public class AJCCompilationUnit extends JCCompilationUnit implements JavaParserComments {

    public String comment;

    public AJCCompilationUnit(List<JCAnnotation> packageAnnotations,
                              JCExpression pid,
                              List<JCTree> defs,
                              JavaFileObject sourcefile,
                              PackageSymbol packge,
                              ImportScope namedImportScope,
                              StarImportScope starImportScope) {
        super(packageAnnotations, pid, defs, sourcefile, packge, namedImportScope, starImportScope);
    }

    public AJCCompilationUnit(JCCompilationUnit ltree) {
        super(ltree.packageAnnotations, ltree.pid, ltree.defs, ltree.sourcefile, ltree.packge, ltree.namedImportScope, ltree.starImportScope);
    }

    public AJCCompilationUnit(JCCompilationUnit ltree, String lcomment) {
        this(ltree);
        setComment(lcomment);
    }

    public boolean hasComment() {
        return comment != null;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String lcomment) {
        comment = lcomment;
    }
}

