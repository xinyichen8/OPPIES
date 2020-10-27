package javaplain;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class  opp {
	public static void main(String[] args) throws IOException, RecognitionException {
		JavaPlainLexer lexer = new JavaPlainLexer(new ANTLRFileStream(args[0]));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		JavaPlainParser parser = new JavaPlainParser(tokens);
		parser.compilationUnit();
		ObjectMapper mapper = new ObjectMapper();

		Class newClass = new Class();
		newClass = parser.c;
		//save class into json file
		File file = new File("test.json");
		try{
			mapper.writeValue(file,newClass);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		//load json file
//		try{
//			Class a = mapper.readValue(file,Class.class);
//			System.out.println(a.getMethod());
//		}catch(IOException e){
//			e.printStackTrace();
//		}

		//print Data Members
		System.out.println("Class: " +newClass.getName().split(" ")[0]);
		System.out.println("       Data Members:");
		for(DataMem dataMem : newClass.getDM()) {
				System.out.println("              "+dataMem.getp() + " " + dataMem.getType() + " " + dataMem.getName() + " " );
		}

		//print Method memebers
		System.out.println("       Method Members:");
		for(Method m : newClass.getMethod()){
			if(m.getName().equals("main")){
				System.out.print("              main(");
			}else{
				System.out.print("              "+  m.getp()+" "+m.getType()+" "+m.getName() + "(" );
			}
			int count = 0;
			for(Param p:m.getParam()){
				if(count>0 && count<m.getParam().size()){
					System.out.print(", ");
				}
				if(m.getName().equals("main")){
					System.out.print(p.getParam().split(" ")[1]);
				}else{
					System.out.print(p.getParam());
				}
				count ++;
			}
			System.out.println(")");
		}


		//print Descendent/ancestor
		if(!newClass.parent().equals("")){
			System.out.println("       Ancestor Classes:");
			System.out.println("              "+newClass.parent());
		}

		System.out.println();


//
		for(Method s: parser.c.getMethod())
		{
//			System.out.println(s.getp()+" " +s.getType()+" "+ s.getName()+" "+s.gets());
//			if(s.getParam().size()>0)
//			{
//				for(Param p:s.getParam())
//				{
//					System.out.println("    "+p.getParam());
//				}
//			}
			if(s.getVar().size()>0) {
				for(DataMem d : s.getVar())
				{
					System.out.println("222222" +d.getp()+" " + d.getType()+ " " + d.getName());
					if(s.getCall().size()>0)
					{
						for(Call c : s.getCall())
						{
							if(!c.getCall().equals("System.out.println") && c.getCall().contains("System.out.println")){
								System.out.println(c.getCall().substring(18));
								
							}else{
								System.out.println("3     "+c.getCall());
							}
						}
					}
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
