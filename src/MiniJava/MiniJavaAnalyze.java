package MiniJava;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.InputStream;
import java.io.FileInputStream;

public class MiniJavaAnalyze {
    public static void main(String[] args) throws Exception{
        String inputFile = null;
        if ( args.length>0 ) inputFile = args[0];
        InputStream is = System.in;
        if ( inputFile!=null ) {
            is = new FileInputStream(inputFile);
        }
        ANTLRInputStream input = new ANTLRInputStream(is);
        MiniJavaLexer lexer = new MiniJavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MiniJavaParser parser = new MiniJavaParser(tokens);
        ParserRuleContext ctx = parser.goal();
        generateAST(ctx, false, 0);
        //TODO utilize graphviz to print graph for AST
    }

    private static void generateAST(RuleContext ctx, boolean verbose, int indentation) {
        boolean ignored = !verbose && ctx.getChildCount() == 1 && ctx.getChild(0) instanceof ParserRuleContext;

        if (!ignored) {
            String ruleName = MiniJavaParser.ruleNames[ctx.getRuleIndex()];
            System.out.println("ruleName "+ruleName);
            //Sequence.add(Integer.toString(indentation));
            //Type.add(ruleName);
            //Text.add(ctx.getText());
            //TODO show with graphviz
        }
        for (int i = 0; i < ctx.getChildCount(); i++) {
            ParseTree element = ctx.getChild(i);
            //TODO switch among different context
            if (element instanceof RuleContext) {
                System.out.println(element.getText());
                generateAST((RuleContext) element, verbose, indentation + (ignored ? 0 : 1));
            }
        }
    }

}



