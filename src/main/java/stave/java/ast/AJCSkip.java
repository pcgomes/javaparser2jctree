package stave.java.ast;

import com.sun.tools.javac.tree.JCTree.JCSkip;
//import import com.sun.tools.javac.tree.JCTree;
//import com.sun.tools.javac.util.List;
//import com.sun.tools.javac.util.Name;
//import com.sun.tools.javac.code.Symbol;
//import com.sun.tools.javac.code.Scope.ImportScope;
//import com.sun.tools.javac.code.BoundKind;

public class AJCSkip extends JCSkip implements JavaParserComments {

    public String comment;

    public AJCSkip() {
        super();
    }

    public AJCSkip(String lcomment) {
        this();
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

