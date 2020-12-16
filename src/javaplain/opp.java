package javaplain;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


public class opp {
    public static List<Class> ListOfClass;
    public static List<Class> pre;
    public static List<Class> post;

    public static void main(String[] args) throws IOException, RecognitionException {
        Class newClass = new Class();
        ListOfClass = new ArrayList<>();
        File sfile = new File("test.json");
        ObjectMapper mapper = new ObjectMapper();
        List<String> callNames = new ArrayList<>();
        File folder = new File(args[0]);
        File[] listOfFiles = folder.listFiles();

        
        for (int i = 0; i < listOfFiles.length; i++) {
            File file = listOfFiles[i];
            if (file.isFile() && file.getName().endsWith(".java")) {
                JavaPlainLexer lexer = new JavaPlainLexer(new ANTLRFileStream(file.getAbsolutePath()));
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                JavaPlainParser parser = new JavaPlainParser(tokens);
                parser.compilationUnit();

                newClass = parser.c;
                ListOfClass.add(newClass);
            }
        }

        
        
        //save class into json file
        try {
            mapper.writeValue(sfile, ListOfClass);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

		//load json file
        List<Class> classList = new ArrayList<>();
		try{
			classList =  Arrays.asList(mapper.readValue(sfile,Class[].class));
		}catch(IOException e){
			e.printStackTrace();
		}

        //move class to previous
        pre = classList;
        post = new ArrayList<>();
        //String post_dir = "C:\\Users\\xinyi\\Desktop\\547\\project\\samples\\add_delete_method\\post";
        String post_dir = "C:\\Users\\xinyi\\Desktop\\547\\project\\samples\\datamem\\post";
        //String post_dir = "C:\\Users\\xinyi\\Desktop\\547\\project\\samples\\change_access\\post";
        File post_folder = new File(post_dir);
        File[] post_file = post_folder.listFiles();

        for (int i = 0; i < post_file.length; i++) {
            File file = post_file[i];
            if (file.isFile() && file.getName().endsWith(".java")) {
                JavaPlainLexer lexer = new JavaPlainLexer(new ANTLRFileStream(file.getAbsolutePath()));
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                JavaPlainParser parser = new JavaPlainParser(tokens);
                parser.compilationUnit();
                newClass = parser.c;
                post.add(newClass);

            }
        }
        
        StringBuilder sbw;
        //comparison
        for(Class i : pre)
        {
        	for(Class j:post)
        	{
        		if (i.getName().equals(j.getName()))
        		{
        			sbw=new StringBuilder();
        			sbw.append("//autodoc\n");
        			sbw.append(compDM(i.getDM(),j.getDM()));
        			//compare Method
        			sbw.append(compMethod(i.getMethod(),j.getMethod()));
        			sbw.append("\n//end autodoc\n");
        			File f = new File(post_dir+"\\"+i.getName().split(" ")[0]+".java");
        			BufferedReader br = new BufferedReader(new FileReader(f));
        			String temp="";
        			String line = "";
        			while((line=br.readLine())!=null)
        			{
        				temp=temp+line+"\n";
        			}
        			temp=sbw.toString()+temp;
        			f.delete();
        			FileWriter out = new FileWriter(f);
        			out.write(temp);
        			out.close();
        			
        		}
        	}
        }
        
        
        
        //store child into parent class
        for (int i = 0; i < ListOfClass.size(); i++) {
            callNames.add(ListOfClass.get(i).getName().split(" ")[0]);
            if (ListOfClass.get(i).getParent().size()>0) {
                int childIndex = getIndexOf(ListOfClass, ListOfClass.get(i).getName());
                if (childIndex != -1) {
                    int parentIndex = getIndexOf(ListOfClass, ListOfClass.get(i).getParent().get(0));
                    if (parentIndex != -1) {
                        checkAncestors(ListOfClass,childIndex,parentIndex);
                        ListOfClass.get(parentIndex).setChild(ListOfClass.get(i).getName().split(" ")[0]);
                        for(String child: ListOfClass.get(i).child()){
                            ListOfClass.get(parentIndex).setChild(child);
                        }

                    }
                }
            }
        }

        
        //Aggreagtes
        for (int i = 0; i < ListOfClass.size(); i++) {
            for (DataMem dataMem : ListOfClass.get(i).getDM()) {
                if (callNames.contains(dataMem.getType())) {
                    ListOfClass.get(i).addAgg(dataMem.getType());
                }else if(dataMem.getp().equals("") || dataMem.getType().equals("Base2")){
                    ListOfClass.get(i).addAgg(dataMem.getType());
                }
            }

        }

        //Associates
        for (int i = 0; i < ListOfClass.size(); i++) {
            for (Method m : ListOfClass.get(i).getMethod()) {
                if (m.getVar().size() > 0 || m.getParam().size()>0) {
                    for (DataMem d : m.getVar()) {
                    	if (callNames.contains(d.getType())) {
                            ListOfClass.get(i).addAss(d.getType());
                            ListOfClass.get(i).addAssV(d.getName());
                        }
                    }
                    for (Param p : m.getParam()) 
                    {
                         if (callNames.contains(p.getParam().split(" ")[0])||((!p.getParam().split(" ")[0].equals("String"))&&(!p.getParam().split(" ")[0].equals("int"))&&(!p.getParam().split(" ")[0].equals("boolean")))) {
                            ListOfClass.get(i).addAss(p.getParam().split(" ")[0]);
                            ListOfClass.get(i).addAssV(p.getParam().split(" ")[1]);
                        }
                    }
                }
            }
        }
        
        


        for (int i = 0; i < ListOfClass.size(); i++) {

            printClass(ListOfClass.get(i));
            printCall(ListOfClass.get(i));

        }





    }
    
    //compare change in class data member information
    private static String compDM(List<DataMem> pre, List<DataMem> post)
    {
    	//compare if deletion or addition happens
    	StringBuilder sb = new StringBuilder();
    	HashSet<String> pr = new HashSet<String>();
    	HashSet<String> po = new HashSet<String>();
    	HashSet<String> pr1 = new HashSet<String>();
    	
    	for(DataMem d:pre)
    	{
    		pr.add(d.getName());
    		
    	}
    	for(DataMem d:post)
    	{
    		po.add(d.getName());
    	}
    	pr1.addAll(pr);

    	sb.append("//Removed param: \n");
    	pr.removeAll(po);
    	for(String d:pr)
    	{
    		sb.append("//    "+d+"\n");
    	}
    	sb.append("\n//Added param: \n");
    	
    	po.removeAll(pr1);
    	
    	for(String d:po)
    	{
    		sb.append("//    "+d+"\n");
    	}
    	
    	//compare if access change
    	sb.append("\n//Change in access or type: \n");
    	for(DataMem d: pre)
    	{
    		for(DataMem e:post)
    		{
    			if (d.getName().equals(e.getName()))
    			{
    				if(!d.getp().equals(e.getp()))
    				{
    					sb.append("//    Data Member "+d.getName()+" change from "+d.getp()+" to "+e.getp()+"\n");
    					
    				}
    				if(!d.getType().equals(e.getType()))
    				{
    					sb.append("//    Data Member "+d.getName()+" change from "+d.getType()+" to "+e.getType()+"\n");
    				}
    			}
    		}
    	}
    	
    	
		return sb.toString();
    	
    }
    
    //compare change in method information
    private static String compMethod(List<Method> pre, List<Method> post)
    {
    	//compare if method got deleted or added
    	HashSet<String> pr = new HashSet<>();
    	HashSet<String> po = new HashSet<>();
    	HashSet<String> pr1 = new HashSet<>();
    	StringBuilder sb1; 
    	StringBuilder sb2 = new StringBuilder();
    	for(Method m:pre)
    	{
    		sb1= new StringBuilder();
    		sb1.append(m.getName()+"!");
    		for(Param p:m.getParam())
    		{
    			sb1.append(p.getParam());
    		}
    		pr.add(sb1.toString());
    	}
    	pr1.addAll(pr);
    	for(Method m:post)
    	{
    		sb1= new StringBuilder();
    		sb1.append(m.getName()+"!");
    		for(Param p:m.getParam())
    		{
    			sb1.append(p.getParam());
    		}
    		po.add(sb1.toString());
    	}
    	
    	sb2.append("//Removed Method: \n");
    	pr.removeAll(po);
    	for(String d:pr)
    	{
    		String s[]=d.split("!");
    		if(s.length>1)
    		{
    			sb2.append("//    "+s[0]+"("+s[1]+")"+"\n");
    		}
    		else
    		{
    			sb2.append("//    "+s[0]+"()\n");
    		}
    	}
    	sb2.append("\n//Added Method: \n");
    	
    	po.removeAll(pr1);
    	
    	for(String d:po)
    	{
    		String s[]=d.split("!");
    		if(s.length>1)
    		{
    			sb2.append("//    "+s[0]+"("+s[1]+")"+"\n");
    		}
    		else
    		{
    			sb2.append("//    "+s[0]+"()\n");
    		}
    	}
    	
	
    	//compare if the type/access of method changed
    	sb2.append("\n//Change in access or type of method: \n");
    	for(Method m1:pre)
    	{
    		for(Method m2:post)
    		{
    			if (m1.getName().equals(m2.getName()))
    			{
    				if(m1.getParam().equals(m2.getParam()))
    				{
    					if(!m1.getp().equals(m2.getp()))
    					{
    						sb2.append("//    Method "+m1.getName()+" change from "+m1.getp()+" to "+m2.getp()+"\n");
    					}
    					if(!m1.getType().equals(m2.getType()))
    					{
    						sb2.append("//    Method "+m1.getName()+" change from "+m1.getType()+" to "+m2.getType()+"\n");
    					}
    				}
    			}
    		}
    	}
    	
    	return sb2.toString();
    }

    
    
    
    private static void checkAncestors(List<Class> LC, int childIndex, int parentIndex){
        if(LC.get(parentIndex).getParent().size()==0){
            return;
        }
        int index = getIndexOf(LC,LC.get(parentIndex).getName().split(" ")[0]);
        if(index!=-1){
            for(String s: LC.get(parentIndex).getParent()) {
            int Aindex = getIndexOf(LC,s);
            if(Aindex!=-1){
                LC.get(childIndex).addParent(s);
                checkAncestors(LC,childIndex,Aindex);
                }
            }
        }
        return;
    }
    

    private static int getIndexOf(List<Class> c, String name) {
        int pos = 0;
        for (Class a : c) {
            if (name.split(" ")[0].equals(a.getName().split(" ")[0])) {
                return pos;
            }
            pos++;
        }
        return -1;
    }

    private static void printClass(Class c) {
        //print Data Members
        System.out.println("Class: " + c.getName());

        //print Descendent/ancestor
        if (c.getParent().size()>=1) {
            System.out.println("       Ancestor Classes:");
            List<String> checker = new ArrayList<>();
            for(String s: c.getParent()){
                if(!checker.contains(s)){
                    checker.add(s);
                    System.out.println("              " + s);
                }
            }

        }

        if (c.child().size()>0) {
            System.out.println("       Descendent Classes:");
            for(String s:c.child()){
                System.out.println("              " + s);
            }
        }else{
            System.out.println("       No descendants");
        }


        //print stats
        if (c.isAbs()) {
            System.out.println("is abstract");
        }
        if (c.isfinal()) {
            System.out.println("is final");
        }
        if (c.isstatic()) {
            System.out.println("is static");
        }
        if (c.isProtect()) {
            System.out.println("is protected");

        }

        System.out.println("Aggreagte: ");
        if (c.getAgg().size() > 0) {
            for (String s : c.getAgg()) {
                System.out.println(s);
            }
        } else {
            System.out.println("None");

        }

        System.out.println("Associates: ");
        if (c.getAss().size() > 0) {
            for (String s : c.getAss()) {
                System.out.println(s);
            }
        } else {
            System.out.println("None");
        }

        System.out.println("       Data Members:");
        for (DataMem dataMem : c.getDM()) {
            System.out.print("              ");
            if(dataMem.gets()){
                System.out.print("static ");
            }
            System.out.println(dataMem.getp() + " " + dataMem.getType() + " " + dataMem.getName() + " ");
        }

        //print Method memebers
        System.out.println("       Method Members:");
        for (Method m : c.getMethod()) {
//            if (m.getName().equals("main")) {
//                System.out.print("              main(");
//            } else {
                System.out.print("              " + m.getName() + " returns " + m.getType() + " is " + m.getp());
                if(m.getabs()){
                    System.out.println(" abstract");
                }else{
                    System.out.println();
                }
                //System.out.print("              " + m.getp() + " " + m.getType() + " " + m.getName() + "(");
//            }

            if(m.getParam().size()>0){
                System.out.print("                  Parameters: ");

            for (Param p : m.getParam()) {
                System.out.print(p.getParam());
            }
            System.out.println();
            }

            if (c.getAss().size() > 0) {
                System.out.print("                  Local vars: ");
                for (String s : c.getAss()) {
                    System.out.println(s + " " +c.getAssV());
                }
            }
        }

        System.out.println();


    }


    private static void printCall(Class c) {

        for (Method s : c.getMethod()) {
            String newS = "";

            if (s.getCall().size() > 0) {

                System.out.println("Method Calls: " + s.getName());

                if (s.getVar().size() >= 0) {


                    for (Call call : s.getCall()) {
                        if (!call.getCall().equals("System.out.println") && call.getCall().contains("System.out.println")) {
                            newS = call.getCall().substring(18);
                            System.out.println("       -->" + newS);

                        } else {
                            System.out.println("       -->" + call.getCall() + "  ");
                            newS = call.getCall();
                        }
                        if (newS.contains(".") && newS != "") {

                            String vName = newS.split("\\.")[1];

                            String cName = "";
                            for (DataMem d : s.getVar()) {
                                if (s.getVar().size() > 0) {
                                    if (d.getName().equals(vName)) ;
                                    {
                                        cName = d.getType();
                                    }

                                }
                            }

                            newS = newS.split("\\.")[1];
                            if (cName != "") {
                                if (getIndexOf(ListOfClass, cName) >= 0) {
                                    c = ListOfClass.get(getIndexOf(ListOfClass, cName));
                                }
                            }
                        }
//                        int index = getCallIndex(c, newS);
//                        if (index >= 0) {
//                            printInnerCall(c.getMethod().get(index));
//                        }
                    }

                }
            }
        }
        System.out.println("==============================================================");
        System.out.println();

    }

    private static int getCallIndex(Class c, String name) {
        int pos = 0;
        for (Method m : c.getMethod()) {
            if (name.equals(m.getName())) {
                return pos;
            }
            pos++;
        }
        return -1;
    }

    private static void printInnerCall(Method s) {

        if (s.getCall().size() >= 0) {
            if (s.getVar().size() >= 0) {

                for (Call call : s.getCall()) {
                    if (!call.getCall().equals("System.out.println") && call.getCall().contains("System.out.println")) {
                        System.out.println("          -->" + call.getCall().substring(18));

                    } else {
                        System.out.println("          -->" + call.getCall() + "  ");
                    }
                }

            }
        }

    }


}
