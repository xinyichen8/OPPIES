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
		for(Method s: parser.c.getMethod())
		{
			System.out.println(s.getp()+" " +s.getType()+" "+ s.getName());
		}
		System.out.println("===========================");
		System.out.println(parser.c.getName());
		for(DataMem dataMem : parser.c.getDM())
			System.out.println(dataMem.getp()+" " + dataMem.getType()+" " +dataMem.getName());
		System.out.println("isStatic: " + parser.c.iss());
		
	}
}
