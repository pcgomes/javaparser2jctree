package stave.java.ast;

import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.JCTree.JCFieldAccess;
import com.sun.tools.javac.util.Name;

//import import com.sun.tools.javac.tree.JCTree;
//import com.sun.tools.javac.util.List;
//import com.sun.tools.javac.code.Scope.ImportScope;
//import com.sun.tools.javac.code.BoundKind;

public class AJCFieldAccess extends JCFieldAccess implements JavaParserComments {

    public String comment;

    public AJCFieldAccess(JCExpression selected, Name name, Symbol sym) {
        super(selected, name, sym);
    }

    public AJCFieldAccess(JCFieldAccess ltree) {
        super(ltree.selected, ltree.name, ltree.sym);
    }

    public AJCFieldAccess(JCFieldAccess ltree, String lcomment) {
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

