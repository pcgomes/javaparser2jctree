package stave.java.ast;

import com.sun.tools.javac.tree.JCTree.JCCatch;
//import import com.sun.tools.javac.tree.JCTree;
//import com.sun.tools.javac.util.List;
//import com.sun.tools.javac.util.Name;
//import com.sun.tools.javac.code.Symbol;
//import com.sun.tools.javac.code.Scope.ImportScope;
//import com.sun.tools.javac.code.BoundKind;

public class AJCCatch extends JCCatch implements JavaParserComments {

    public String comment;

    public AJCCatch(JCVariableDecl param, JCBlock body) {
        super(param, body);
    }

    public AJCCatch(JCCatch ltree) {
        super(ltree.param, ltree.body);
    }

    public AJCCatch(JCCatch ltree, String lcomment) {
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

