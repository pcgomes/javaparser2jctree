package stave.java.ast;

import com.sun.tools.javac.code.BoundKind;
import com.sun.tools.javac.tree.JCTree.TypeBoundKind;

//import import com.sun.tools.javac.tree.JCTree;
//import com.sun.tools.javac.util.List;
//import com.sun.tools.javac.util.Name;
//import com.sun.tools.javac.code.Scope.ImportScope;

public class ATypeBoundKind extends TypeBoundKind implements JavaParserComments {

    public String comment;

    public ATypeBoundKind(BoundKind kind) {
        super(kind);
    }

    public ATypeBoundKind(TypeBoundKind ltree) {
        super(ltree.kind);
    }

    public ATypeBoundKind(TypeBoundKind ltree, String lcomment) {
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

