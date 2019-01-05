package MiniJava;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import java.io.*;
import java.util.ArrayList;

public class MiniJavaAnalyze {

    public static void main(String[] args) throws Exception{
        //Choose to display verbose or not
        System.out.println("please input your verbose choice: 1 for true, 0 for false");
        int b=System.in.read();
        while (b!=49&&b!=48) {
            System.out.println("please input your verbose choice: 1 for true, 0 for false");
            b = System.in.read();
        }
        boolean verbose = false;
        if(b==49) verbose=true;
        System.out.println("verbose is set to "+verbose);
        //Input Source File in minijava format
        String inputFile = null;
        if ( args.length>0 ) inputFile = args[0];
        InputStream is = System.in;
        if ( inputFile!=null ) {
            is = new FileInputStream(inputFile);
        }
        //Construct ANTLR analyze tools
        ANTLRInputStream input = new ANTLRInputStream(is);
        MiniJavaLexer lexer = new MiniJavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MiniJavaParser parser = new MiniJavaParser(tokens);
        //TODO error handle
        parser.removeErrorListeners();
        parser.addErrorListener(new ErrorListener());
        parser.getInterpreter().setPredictionMode(PredictionMode.LL_EXACT_AMBIG_DETECTION);
        //generate AST
        ParserRuleContext ctx = parser.goal();
        //TODO utilize graphviz to print graph for AST
        PrintStream out;
        try{
            out=new PrintStream("src/draw/test.dot");
            System.setOut(out);
            System.out.println("digraph AST{");
            generateAST(ctx,verbose,"0","");
            System.out.println("}");
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        //proceed graphviz command through Process
        System.setOut(System.out);
        String path = "/Users/weiyixuan/Desktop/复旦/课程/大四上/编译/project/src/";
        String command = path+"draw/dot -Tpng -o "+path+"graph.png "+path+"draw/test.dot";
        String openCmd = "open "+path+"graph.png";
        Process p = Runtime.getRuntime().exec(command);
        //Handle errors and output
        InputStream is2 = p.getInputStream();
        InputStream errorIS = p.getErrorStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is2));
        BufferedReader errReader = new BufferedReader(new InputStreamReader(errorIS));
        p.waitFor();
        if (p.exitValue() != 0) {
            System.out.println("ERROR: execute the generating graph.png order failed!");
        }
        p = Runtime.getRuntime().exec(openCmd);
        p.waitFor();
        if(p.exitValue()!=0){
            System.out.println("ERROR: execute the open graph.png order failed!");
        }
        String s = null;
        while ((s = reader.readLine()) != null) {
            System.out.println(s);
        }
        while((s=errReader.readLine())!=null){
            System.out.println(s);
        }
    }

    private static void generateAST(RuleContext ctx, boolean verbose, String indentation, String preIndentation) {
        boolean ignored = ctx.getChildCount() == 1 && ctx.getChild(0) instanceof ParserRuleContext;
        if (!ignored) {
            String ruleName = MiniJavaParser.ruleNames[ctx.getRuleIndex()];
            if(verbose)
                System.out.println(indentation+"[label=\""+ruleName+"\n"+ctx.getText()+"\"]");
            else
                System.out.println(indentation+"[label=\""+ruleName+"\"]");
            if(!preIndentation.equals("")) {
                System.out.println(preIndentation+"->"+indentation);
            }
        }
        int tempChildCnt=0;
        for (int i = 0; i < ctx.getChildCount(); i++) {
            ParseTree element = ctx.getChild(i);
            //TODO switch among different context
            if (element instanceof RuleContext) {
                generateAST((RuleContext) element, verbose, indentation+tempChildCnt,indentation);
                tempChildCnt++;
            }
            else if(element instanceof TerminalNodeImpl){
                Token t = ((TerminalNodeImpl) element).getSymbol();
                String lexerName = MiniJavaLexer.VOCABULARY.getSymbolicName(t.getType());
                if(lexerName!=null) {
                    System.out.println(indentation+tempChildCnt+"[label=\""+lexerName+"\n"+element.getText()+"\"]");
                    System.out.println(indentation+"->"+(indentation+tempChildCnt));
                    tempChildCnt++;
                }
            }
        }
    }

}



