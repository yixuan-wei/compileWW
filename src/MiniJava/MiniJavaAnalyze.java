package MiniJava;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import java.io.*;
import java.util.ArrayList;

public class MiniJavaAnalyze {
    static ArrayList<String> Parent = new ArrayList<String>();
    static ArrayList<String> Sequence = new ArrayList<String>();
    static ArrayList<String> Type = new ArrayList<String>();
    static ArrayList<String> Text = new ArrayList<String>();
    static ArrayList<String> SymbolCnt = new ArrayList<String>();
    static String[] rules;

    public static void main(String[] args) throws Exception{
        String inputFile = null;
        if ( args.length>0 ) inputFile = args[0];
        InputStream is = System.in;
        if ( inputFile!=null ) {
            is = new FileInputStream(inputFile);
        }
        ANTLRInputStream input = new ANTLRInputStream(is);
        MiniJavaLexer lexer = new MiniJavaLexer(input);
        rules = MiniJavaLexer.ruleNames;
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MiniJavaParser parser = new MiniJavaParser(tokens);
        //TODO error handle
        parser.removeErrorListeners();
        parser.addErrorListener(new ErrorListener());
        parser.getInterpreter().setPredictionMode(PredictionMode.LL_EXACT_AMBIG_DETECTION);
        //generate AST
        ParserRuleContext ctx = parser.goal();
        //generateAST(ctx, false, 0);
        //TODO utilize graphviz to print graph for AST
        PrintStream out;
        try{
            out=new PrintStream("src/draw/test.dot");
            System.setOut(out);
            System.out.println("digraph AST{");
            //toDot();
            generateAST(ctx,false,"00","");
            System.out.println("}");
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        System.setOut(System.out);
        String path = "/Users/weiyixuan/Desktop/复旦/课程/大四上/编译/project/src/";
        //path = "";
        String command = path+"draw/dot -Tpng -o "+path+"graph.png "+path+"draw/test.dot";
        String openCmd = "open "+path+"graph.png";
        Process p = Runtime.getRuntime().exec(command);

        InputStream is2 = p.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is2));
        p.waitFor();
        if (p.exitValue() != 0) {
            //TODO error handle
            //说明命令执行失败
            //可以进入到错误处理步骤中
        }
        p = Runtime.getRuntime().exec(openCmd);
        p.waitFor();
        if(p.exitValue()!=0){
            //TODO error handle
        }
        String s = null;
        while ((s = reader.readLine()) != null) {
            System.out.println(s);
        }
    }

    private static void generateAST(RuleContext ctx, boolean verbose, String indentation, String preIndentation) {
        boolean ignored = !verbose && ctx.getChildCount() == 1 && ctx.getChild(0) instanceof ParserRuleContext;
        if (!ignored) {
            String ruleName = MiniJavaParser.ruleNames[ctx.getRuleIndex()];
            //System.out.println("ruleName "+ruleName);
            System.out.println(indentation+"[label=\""+ruleName+"\n"+ctx.getText()+"\"]");
            if(!preIndentation.equals("")) {
                //Parent.add(preIndentation);
                System.out.println(preIndentation+"->"+indentation);
                //Sequence.add(indentation);
                //Type.add(ruleName);
                //Text.add(ctx.getText());
            }
        }
        int tempIndentation = Integer.valueOf(indentation);
        for (int i = 0; i < ctx.getChildCount(); i++) {
            ParseTree element = ctx.getChild(i);
            //System.out.println(element.getText());
            //TODO switch among different context
            if (element instanceof RuleContext) {
                //System.out.println("is rule context");
                //System.out.println(((RuleContext) element).getRuleIndex());
                generateAST((RuleContext) element, verbose, String.valueOf(tempIndentation+10),indentation);
            }
            /*else if(element instanceof TerminalNodeImpl){
                Token t = ((TerminalNodeImpl) element).getSymbol();
                String lexerName = MiniJavaLexer.VOCABULARY.getSymbolicName(t.getType());
                if(lexerName!=null) {
                    System.out.println(lexerName);
                    System.out.println("indentation: "+indentation);
                    Sequence.add(Integer.toString(indentation+1));
                    tempSymbolCount++;
                    Type.add(lexerName);
                    Text.add(element.getText());
                }
            }*/
        }
    }

    private static void toDot(){
        printLabel();
        int pos=0;
        for (int i=1; i<Sequence.size();i++){
            pos = Integer.parseInt(Sequence.get(i))-1;
            System.out.println(pos+Integer.toString(pos)+"->"+Sequence.get(i)+i);
        }
    }

    private static void printLabel(){
        for(int i=0;i<Sequence.size();i++){
            System.out.println(Sequence.get(i)+i+"[label=\""+Type.get(i));
            System.out.println("\\n "+Text.get(i));
            System.out.println("\"]");
        }
    }

}



