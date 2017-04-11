package stave.java.ast;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCWildcard;
//import com.sun.tools.javac.util.List;
//import com.sun.tools.javac.util.Name;
//import com.sun.tools.javac.code.Symbol;
//import com.sun.tools.javac.code.Scope.ImportScope;
//import com.sun.tools.javac.code.BoundKind;

public class AJCWildcard extends JCWildcard implements JavaParserComments {

    public String comment;

    public AJCWildcard(TypeBoundKind kind, JCTree inner) {
        super(kind, inner);
    }

    public AJCWildcard(JCWildcard ltree) {
        super(ltree.kind, ltree.inner);
    }

    public AJCWildcard(JCWildcard ltree, String lcomment) {
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

