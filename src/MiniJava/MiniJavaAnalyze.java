package MiniJava;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class MiniJavaAnalyze {
    static ArrayList<String> Sequence = new ArrayList<String>();
    static ArrayList<String> Type = new ArrayList<String>();
    static ArrayList<String> Text = new ArrayList<String>();

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
        PrintStream out;
        try{
            out=new PrintStream("src/draw/test.dot");
            System.setOut(out);
            System.out.println("digraph AST{");
            toDot();
            System.out.println("}");
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private static void generateAST(RuleContext ctx, boolean verbose, int indentation) {
        boolean ignored = !verbose && ctx.getChildCount() == 1 && ctx.getChild(0) instanceof ParserRuleContext;

        if (!ignored) {
            String ruleName = MiniJavaParser.ruleNames[ctx.getRuleIndex()];
            //System.out.println("ruleName "+ruleName);
            Sequence.add(Integer.toString(indentation));
            Type.add(ruleName);
            Text.add(ctx.getText());
        }
        for (int i = 0; i < ctx.getChildCount(); i++) {
            ParseTree element = ctx.getChild(i);
            //TODO switch among different context
            if (element instanceof RuleContext) {
                //System.out.println(element.getText());
                generateAST((RuleContext) element, verbose, indentation + (ignored ? 0 : 1));
            }
        }
    }

    private static void toDot(){
        //TODO print detail : printLabel();
        int pos=0;
        for (int i=1; i<Sequence.size();i++){
            pos = Integer.parseInt(Sequence.get(i))-1;
            System.out.println(pos+Integer.toString(pos)+"->"+Sequence.get(i)+i);
        }
    }

}



