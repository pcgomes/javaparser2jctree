package stave.java.ast;

import com.sun.tools.javac.tree.JCTree.JCThrow;
//import com.sun.tools.javac.util.List;
//import com.sun.tools.javac.util.Name;
//import com.sun.tools.javac.code.Symbol;
//import com.sun.tools.javac.code.Scope.ImportScope;
//import com.sun.tools.javac.code.BoundKind;

public class AJCThrow extends JCThrow implements JavaParserComments {

    public String comment;

    public AJCThrow(JCExpression expr) {
        super(expr);
    }

    public AJCThrow(JCThrow ltree) {
        super(ltree.expr);
    }

    public AJCThrow(JCThrow ltree, String lcomment) {
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

