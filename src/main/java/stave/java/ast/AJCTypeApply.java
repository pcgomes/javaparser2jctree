package stave.java.ast;

import com.sun.tools.javac.tree.JCTree.JCTypeApply;
import com.sun.tools.javac.util.List;

//import import com.sun.tools.javac.tree.JCTree;
//import com.sun.tools.javac.util.Name;
//import com.sun.tools.javac.code.Symbol;
//import com.sun.tools.javac.code.Scope.ImportScope;
//import com.sun.tools.javac.code.BoundKind;

public class AJCTypeApply extends JCTypeApply implements JavaParserComments {

    public String comment;

    public AJCTypeApply(JCExpression clazz, List<JCExpression> arguments) {
        super(clazz, arguments);
    }

    public AJCTypeApply(JCTypeApply ltree) {
        super(ltree.clazz, ltree.arguments);
    }

    public AJCTypeApply(JCTypeApply ltree, String lcomment) {
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

