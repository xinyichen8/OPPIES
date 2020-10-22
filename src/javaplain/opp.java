package javaplain;

import java.io.IOException;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

public class opp {
	public static void main(String[] args) throws IOException, RecognitionException 
	{
		JavaPlainLexer lexer = new JavaPlainLexer(new ANTLRFileStream(args[0]));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		JavaPlainParser parser = new JavaPlainParser(tokens);
		parser.compilationUnit();
		
		
		
		System.out.println("Class: " + parser.c.getName());
		System.out.println("Parent: " + parser.parent.get(0));
	}
}
