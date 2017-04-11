package stave.java.ast;

import com.sun.tools.javac.tree.JCTree.JCTypeParameter;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;

//import import com.sun.tools.javac.tree.JCTree;
//import com.sun.tools.javac.code.Symbol;
//import com.sun.tools.javac.code.Scope.ImportScope;
//import com.sun.tools.javac.code.BoundKind;

public class AJCTypeParameter extends JCTypeParameter implements JavaParserComments {

    public String comment;

    public AJCTypeParameter(Name name, List<JCExpression> bounds) {
        super(name, bounds, List.<JCAnnotation>nil());
    }

    public AJCTypeParameter(JCTypeParameter ltree) {
        super(ltree.name, ltree.bounds, ltree.annotations);
    }

    public AJCTypeParameter(JCTypeParameter ltree, String lcomment) {
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

