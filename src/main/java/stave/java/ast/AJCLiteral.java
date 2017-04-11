package stave.java.ast;

import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.tree.JCTree.JCLiteral;

//import import com.sun.tools.javac.tree.JCTree;
//import com.sun.tools.javac.util.List;
//import com.sun.tools.javac.util.Name;
//import com.sun.tools.javac.code.Scope.ImportScope;
//import com.sun.tools.javac.code.BoundKind;

public class AJCLiteral extends JCLiteral implements JavaParserComments {

    public String comment;

    public AJCLiteral(TypeTag typetag, Object value) {
        super(typetag, value);
    }

    public AJCLiteral(JCLiteral ltree) {
        super(ltree.typetag, ltree.value);
    }

    public AJCLiteral(JCLiteral ltree, String lcomment) {
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

