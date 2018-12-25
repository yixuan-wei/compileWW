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
        ParseTree tree = parser.; // parse
        ParseTreeWalker walker = new ParseTreeWalker();
        MyMiniJavaListener listener = new MyMiniJavaListener(parser);
        walker.walk(listener,tree);
    }

}



