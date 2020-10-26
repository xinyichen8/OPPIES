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
			System.out.println(s.getp()+" " +s.getType()+" "+ s.getName()+" "+s.gets());
			if(s.getParam().size()>0)
			{
				for(Param p:s.getParam())
				{
					System.out.println("    "+p.getParam());
				}
			}
			if(s.getVar().size()>0) {
				for(DataMem d : s.getVar())
				{
					System.out.println("  "+d.getp()+" " + d.getType()+ " " + d.getName());
				}
			}
			if(s.getCall().size()>0)
			{
				for(Call c : s.getCall())
				{
					System.out.println(c.getCall());
				}
			}
			
		}
		System.out.println("===========================");
		System.out.println(parser.c.getName());
		for(DataMem dataMem : parser.c.getDM())
			System.out.println(dataMem.getp()+" " + dataMem.getType()+" " +dataMem.getName()+" "+dataMem.gets());
		System.out.println("isAbs: " + parser.c.isAbs());
		
		
		
	}
}
