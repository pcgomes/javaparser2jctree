package stave.java.visitor;

import com.sun.tools.javac.tree.JCTree.JCAnnotation;
import com.sun.tools.javac.tree.JCTree.JCArrayAccess;
import com.sun.tools.javac.tree.JCTree.JCArrayTypeTree;
import com.sun.tools.javac.tree.JCTree.JCAssert;
import com.sun.tools.javac.tree.JCTree.JCAssign;
import com.sun.tools.javac.tree.JCTree.JCAssignOp;
import com.sun.tools.javac.tree.JCTree.JCBinary;
import com.sun.tools.javac.tree.JCTree.JCBlock;
import com.sun.tools.javac.tree.JCTree.JCBreak;
import com.sun.tools.javac.tree.JCTree.JCCase;
import com.sun.tools.javac.tree.JCTree.JCCatch;
import com.sun.tools.javac.tree.JCTree.JCClassDecl;
import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;
import com.sun.tools.javac.tree.JCTree.JCConditional;
import com.sun.tools.javac.tree.JCTree.JCContinue;
import com.sun.tools.javac.tree.JCTree.JCDoWhileLoop;
import com.sun.tools.javac.tree.JCTree.JCEnhancedForLoop;
import com.sun.tools.javac.tree.JCTree.JCErroneous;
import com.sun.tools.javac.tree.JCTree.JCExpressionStatement;
import com.sun.tools.javac.tree.JCTree.JCFieldAccess;
import com.sun.tools.javac.tree.JCTree.JCForLoop;
import com.sun.tools.javac.tree.JCTree.JCIdent;
import com.sun.tools.javac.tree.JCTree.JCIf;
import com.sun.tools.javac.tree.JCTree.JCImport;
import com.sun.tools.javac.tree.JCTree.JCInstanceOf;
import com.sun.tools.javac.tree.JCTree.JCLabeledStatement;
import com.sun.tools.javac.tree.JCTree.JCLiteral;
import com.sun.tools.javac.tree.JCTree.JCMethodDecl;
import com.sun.tools.javac.tree.JCTree.JCMethodInvocation;
import com.sun.tools.javac.tree.JCTree.JCModifiers;
import com.sun.tools.javac.tree.JCTree.JCNewArray;
import com.sun.tools.javac.tree.JCTree.JCNewClass;
import com.sun.tools.javac.tree.JCTree.JCParens;
import com.sun.tools.javac.tree.JCTree.JCPrimitiveTypeTree;
import com.sun.tools.javac.tree.JCTree.JCReturn;
import com.sun.tools.javac.tree.JCTree.JCSkip;
import com.sun.tools.javac.tree.JCTree.JCSwitch;
import com.sun.tools.javac.tree.JCTree.JCSynchronized;
import com.sun.tools.javac.tree.JCTree.JCThrow;
import com.sun.tools.javac.tree.JCTree.JCTry;
import com.sun.tools.javac.tree.JCTree.JCTypeApply;
import com.sun.tools.javac.tree.JCTree.JCTypeCast;
import com.sun.tools.javac.tree.JCTree.JCTypeParameter;
import com.sun.tools.javac.tree.JCTree.JCTypeUnion;
import com.sun.tools.javac.tree.JCTree.JCUnary;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;
import com.sun.tools.javac.tree.JCTree.JCWhileLoop;
import com.sun.tools.javac.tree.JCTree.JCWildcard;
import com.sun.tools.javac.tree.JCTree.LetExpr;
import com.sun.tools.javac.tree.JCTree.TypeBoundKind;
import com.sun.tools.javac.tree.Pretty;
import java.io.IOException;
import java.io.Writer;
import stave.java.ast.AJCAnnotation;
import stave.java.ast.AJCArrayAccess;
import stave.java.ast.AJCArrayTypeTree;
import stave.java.ast.AJCAssert;
import stave.java.ast.AJCAssign;
import stave.java.ast.AJCAssignOp;
import stave.java.ast.AJCBinary;
import stave.java.ast.AJCBlock;
import stave.java.ast.AJCBreak;
import stave.java.ast.AJCCase;
import stave.java.ast.AJCCatch;
import stave.java.ast.AJCClassDecl;
import stave.java.ast.AJCCompilationUnit;
import stave.java.ast.AJCConditional;
import stave.java.ast.AJCContinue;
import stave.java.ast.AJCDoWhileLoop;
import stave.java.ast.AJCEnhancedForLoop;
import stave.java.ast.AJCErroneous;
import stave.java.ast.AJCExpressionStatement;
import stave.java.ast.AJCFieldAccess;
import stave.java.ast.AJCForLoop;
import stave.java.ast.AJCIdent;
import stave.java.ast.AJCIf;
import stave.java.ast.AJCImport;
import stave.java.ast.AJCInstanceOf;
import stave.java.ast.AJCLabeledStatement;
import stave.java.ast.AJCLiteral;
import stave.java.ast.AJCMethodDecl;
import stave.java.ast.AJCMethodInvocation;
import stave.java.ast.AJCModifiers;
import stave.java.ast.AJCNewArray;
import stave.java.ast.AJCNewClass;
import stave.java.ast.AJCParens;
import stave.java.ast.AJCPrimitiveTypeTree;
import stave.java.ast.AJCReturn;
import stave.java.ast.AJCSkip;
import stave.java.ast.AJCSwitch;
import stave.java.ast.AJCSynchronized;
import stave.java.ast.AJCThrow;
import stave.java.ast.AJCTry;
import stave.java.ast.AJCTypeApply;
import stave.java.ast.AJCTypeCast;
import stave.java.ast.AJCTypeParameter;
import stave.java.ast.AJCTypeUnion;
import stave.java.ast.AJCUnary;
import stave.java.ast.AJCVariableDecl;
import stave.java.ast.AJCWhileLoop;
import stave.java.ast.AJCWildcard;
import stave.java.ast.ALetExpr;
import stave.java.ast.ATypeBoundKind;

/* TODO - The JC add elements to the AST that are not AJC-
* Thus, all methods should test for their coresponding AJC type before printing the comments,
* So, a non-AJC element can be visited without raising and Exception, caused by bad type-casting.
* Other option: create a visitor that checks if there are a non-AJC node, and convert it.
*/
public class APretty extends Pretty {
    public APretty(Writer out, boolean sourceOutput) {
        super(out, sourceOutput);
    }

    public void visitTopLevel(JCCompilationUnit that) {
        if (((AJCCompilationUnit) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCCompilationUnit) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitTopLevel(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitImport(JCImport that) {
        if (((AJCImport) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCImport) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitImport(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitClassDef(JCClassDecl that) {
        if (((AJCClassDecl) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCClassDecl) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitClassDef(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitMethodDef(JCMethodDecl that) {
        // Java adds a default constructor, which is not AJCMethodDecl. Adding test to this.

        if ((that instanceof AJCMethodDecl)) {
            if (((AJCMethodDecl) that).hasComment()) {
                try {
                    println();
                    print("/*" + ((AJCMethodDecl) that).getComment() + "*/");
                } catch (IOException e) {
                    System.err.println("Exception at line " +
                            Thread.currentThread().getStackTrace()[1].getLineNumber() +
                            ":" +
                            e.getMessage());
                }
            }

            try {
                super.visitMethodDef(that);
            } catch (Exception e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage() + ". Proceeding still...");
            }
        }
    }

    public void visitVarDef(JCVariableDecl that) {
        if (((AJCVariableDecl) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCVariableDecl) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitVarDef(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitSkip(JCSkip that) {
        if (((AJCSkip) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCSkip) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitSkip(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitBlock(JCBlock that) {
        if (((AJCBlock) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCBlock) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitBlock(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitDoLoop(JCDoWhileLoop that) {
        if (((AJCDoWhileLoop) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCDoWhileLoop) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitDoLoop(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitWhileLoop(JCWhileLoop that) {
        if (((AJCWhileLoop) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCWhileLoop) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitWhileLoop(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitForLoop(JCForLoop that) {
        if (((AJCForLoop) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCForLoop) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitForLoop(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitForeachLoop(JCEnhancedForLoop that) {
        if (((AJCEnhancedForLoop) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCEnhancedForLoop) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitForeachLoop(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitLabelled(JCLabeledStatement that) {
        if (((AJCLabeledStatement) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCLabeledStatement) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitLabelled(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitSwitch(JCSwitch that) {
        if (((AJCSwitch) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCSwitch) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitSwitch(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitCase(JCCase that) {
        if (((AJCCase) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCCase) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitCase(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitSynchronized(JCSynchronized that) {
        if (((AJCSynchronized) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCSynchronized) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitSynchronized(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitTry(JCTry that) {
        if (((AJCTry) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCTry) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitTry(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitCatch(JCCatch that) {
        if (((AJCCatch) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCCatch) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitCatch(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitConditional(JCConditional that) {
        if (((AJCConditional) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCConditional) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitConditional(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitIf(JCIf that) {
        if (((AJCIf) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCIf) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitIf(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitExec(JCExpressionStatement that) {
        if (that instanceof AJCExpressionStatement) {
            if (((AJCExpressionStatement) that).hasComment()) {
                try {
                    println();
                    print("/*" + ((AJCExpressionStatement) that).getComment() + "*/");
                } catch (IOException e) {
                    System.err.println("Exception at line " +
                            Thread.currentThread().getStackTrace()[1].getLineNumber() +
                            ":" +
                            e.getMessage());
                }
            }

            try {
                super.visitExec(that);
            } catch (Exception e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage() + ". Proceeding still...");
            }
        }
    }

    public void visitBreak(JCBreak that) {
        if (((AJCBreak) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCBreak) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitBreak(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitContinue(JCContinue that) {
        if (((AJCContinue) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCContinue) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitContinue(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitReturn(JCReturn that) {
        if (((AJCReturn) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCReturn) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitReturn(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitThrow(JCThrow that) {
        if (((AJCThrow) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCThrow) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitThrow(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitAssert(JCAssert that) {
        if (((AJCAssert) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCAssert) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitAssert(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitApply(JCMethodInvocation that) {
        if (((AJCMethodInvocation) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCMethodInvocation) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitApply(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitNewClass(JCNewClass that) {
        if (((AJCNewClass) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCNewClass) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitNewClass(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitNewArray(JCNewArray that) {
        if (((AJCNewArray) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCNewArray) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitNewArray(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitParens(JCParens that) {
        if (((AJCParens) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCParens) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitParens(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitAssign(JCAssign that) {
        if (((AJCAssign) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCAssign) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitAssign(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitAssignop(JCAssignOp that) {
        if (((AJCAssignOp) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCAssignOp) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitAssignop(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitUnary(JCUnary that) {
        if (((AJCUnary) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCUnary) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitUnary(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitBinary(JCBinary that) {
        if (((AJCBinary) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCBinary) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitBinary(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitTypeCast(JCTypeCast that) {
        if (((AJCTypeCast) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCTypeCast) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitTypeCast(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitTypeTest(JCInstanceOf that) {
        if (((AJCInstanceOf) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCInstanceOf) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitTypeTest(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitIndexed(JCArrayAccess that) {
        if (((AJCArrayAccess) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCArrayAccess) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitIndexed(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitSelect(JCFieldAccess that) {
        if (((AJCFieldAccess) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCFieldAccess) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitSelect(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitIdent(JCIdent that) {
        if (((AJCIdent) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCIdent) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitIdent(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitLiteral(JCLiteral that) {
        if (((AJCLiteral) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCLiteral) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitLiteral(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitTypeIdent(JCPrimitiveTypeTree that) {
        if (((AJCPrimitiveTypeTree) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCPrimitiveTypeTree) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitTypeIdent(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitTypeArray(JCArrayTypeTree that) {
        if (((AJCArrayTypeTree) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCArrayTypeTree) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitTypeArray(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitTypeApply(JCTypeApply that) {
        if (((AJCTypeApply) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCTypeApply) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitTypeApply(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitTypeUnion(JCTypeUnion that) {
        if (((AJCTypeUnion) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCTypeUnion) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitTypeUnion(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitTypeParameter(JCTypeParameter that) {
        if (((AJCTypeParameter) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCTypeParameter) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitTypeParameter(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitWildcard(JCWildcard that) {
        if (((AJCWildcard) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCWildcard) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitWildcard(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitTypeBoundKind(TypeBoundKind that) {
        if (((ATypeBoundKind) that).hasComment()) {
            try {
                println();
                print("/*" + ((ATypeBoundKind) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitTypeBoundKind(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitAnnotation(JCAnnotation that) {
        if (((AJCAnnotation) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCAnnotation) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitAnnotation(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitModifiers(JCModifiers that) {
        if (((AJCModifiers) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCModifiers) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitModifiers(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitErroneous(JCErroneous that) {
        if (((AJCErroneous) that).hasComment()) {
            try {
                println();
                print("/*" + ((AJCErroneous) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitErroneous(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }

    public void visitLetExpr(LetExpr that) {
        if (((ALetExpr) that).hasComment()) {
            try {
                println();
                print("/*" + ((ALetExpr) that).getComment() + "*/");
            } catch (IOException e) {
                System.err.println("Exception at line " +
                        Thread.currentThread().getStackTrace()[1].getLineNumber() +
                        ":" +
                        e.getMessage());
            }
        }

        try {
            super.visitLetExpr(that);
        } catch (Exception e) {
            System.err.println("Exception at line " +
                    Thread.currentThread().getStackTrace()[1].getLineNumber() +
                    ":" +
                    e.getMessage() + ". Proceeding still...");
        }
    }
}
