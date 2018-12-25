package MiniJava;

import org.antlr.v4.runtime.tree.*;
import java.util.Map;
import java.util.HashMap;

public class MyMiniJavaListener extends MiniJavaBaseListener{
    ParseTreeProperty<Integer> values = new ParseTreeProperty<Integer>();
    Map<String,String> identifiers = new HashMap<String,String>();
    MiniJavaParser parser;
    public MyMiniJavaListener(MiniJavaParser parser) {this.parser = parser;}
    @Override
    public void enterMainClass(MiniJavaParser.MainClassContext ctx){
        System.out.println("entering main class parser:"+ctx.Identifier(0).getText()+"\n");
        identifiers.put(ctx.toString(), ctx.Identifier(0).getText());
        identifiers.put(ctx.toString(),ctx.Identifier(1).getText());
    }
}