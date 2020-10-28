package javaplain;

//import com.fasterxml.jackson.core.JsonGenerationException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class opp {
    public static List<Class> ListOfClass ;

    public static void main(String[] args) throws IOException, RecognitionException {
        Class newClass = new Class();
        ListOfClass = new ArrayList<>();
        File sfile = new File("test.json");
        //ObjectMapper mapper = new ObjectMapper();
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
                //save class into json file
//                try {
//                    mapper.writeValue(sfile, newClass);
//                } catch (JsonMappingException e) {
//                    e.printStackTrace();
//                } catch (JsonGenerationException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        }
        //store child into parent class
        for (int i = 0; i < ListOfClass.size(); i++) {
            callNames.add(ListOfClass.get(i).getName().split(" ")[0]);
            if (!ListOfClass.get(i).parent().equals("")) {
                int index = getIndexOf(ListOfClass, ListOfClass.get(i).getName());
                if (index != -1) {
                    int parentIndex = getIndexOf(ListOfClass, ListOfClass.get(i).parent());
                    if (parentIndex != -1) {
                        ListOfClass.get(parentIndex).setChild(ListOfClass.get(i).getName().split(" ")[0]);
                    }
                }
            }
        }

        //Aggreagtes
        for (int i = 0; i < ListOfClass.size(); i++) {
            for (DataMem dataMem : ListOfClass.get(i).getDM()) {
                if (dataMem.getp() == "" || callNames.contains(dataMem.getType())) {
//                    System.out.println(dataMem.getType());
                    ListOfClass.get(i).addAgg(dataMem.getType());
                }
            }

        }

        //Associates
        for (int i = 0; i < ListOfClass.size(); i++) {
            for (Method m : ListOfClass.get(i).getMethod()) {
                if (m.getVar().size() > 0) {
                    for (DataMem d : m.getVar()) {
                        for (Param p : m.getParam()) {
                            if (callNames.contains(d.getType())) {
                                ListOfClass.get(i).addAss(d.getType());
                            } else if (callNames.contains(m.getParam().get(0).getParam().split(" ")[0])) {
                                ListOfClass.get(i).addAss(p.getParam().split(" ")[0]);
                            }
                        }

                    }
                }
            }
        }


        for (int i = 0; i < ListOfClass.size(); i++) {

            printClass(ListOfClass.get(i));
        }

        for (int i = 0; i < ListOfClass.size(); i++) {

            printCall(ListOfClass.get(i));
        }

//		//load json file
//		try{
//			Class a = mapper.readValue(file,Class.class);
//			System.out.println(a.getMethod());
//		}catch(IOException e){
//			e.printStackTrace();
//		}


    }

    private static int getIndexOf(List<Class> c, String name) {
        int pos = 0;
        for (Class a : c) {
            if (name.equals(a.getName())) {
                return pos;
            }
            pos++;
        }
        return -1;
    }

    private static void printClass(Class c) {
        //print Data Members
        System.out.println("Class: " + c.getName().split(" ")[0]);
        if (c.isAbs()) {
            System.out.println("is absolute");
        }
        if (c.isf()) {
            System.out.println("is final");
        }
        if (c.iss()) {
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
            System.out.println("              " + dataMem.getp() + " " + dataMem.getType() + " " + dataMem.getName() + " ");
        }

        //print Method memebers
        System.out.println("       Method Members:");
        for (Method m : c.getMethod()) {
            if (m.getName().equals("main")) {
                System.out.print("              main(");
            } else {
                System.out.print("              " + m.getp() + " " + m.getType() + " " + m.getName() + "(");
            }
            int count = 0;
            for (Param p : m.getParam()) {
                if (count > 0 && count < m.getParam().size()) {
                    System.out.print(", ");
                }
                if (m.getName().equals("main")) {
                    System.out.print(p.getParam().split(" ")[1]);
                } else {
                    System.out.print(p.getParam());
                }
                count++;
            }
            System.out.println(")");
        }

        //print Descendent/ancestor
        if (!c.parent().equals("")) {
            System.out.println("       Ancestor Classes:");
            System.out.println("              " + c.parent());
        }
        if (!c.child().equals("")) {
            System.out.println("       Descendent Classes:");
            System.out.println("              " + c.child());
        }

        System.out.println("==============================================================");
    }


    private static void printCall(Class c) {

        for (Method s : c.getMethod()) {
            String newS="";

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
                        if (newS.contains(".") && newS!="") {

                            String vName = newS.split("\\.")[1];

                            String cName ="";
                            for(DataMem d:s.getVar()){
                                if(s.getVar().size()>0){
                                    if(d.getName().equals(vName));{
                                        cName = d.getType();
                                    }

                                }
                            }

                            newS = newS.split("\\.")[1];
                            if(cName!=""){
                                if(getIndexOf(ListOfClass,cName)>=0){
                                    c = ListOfClass.get(getIndexOf(ListOfClass,cName));
                                }
                            }
                        }
                        int index = getCallIndex(c, newS);
                        if (index >= 0) {
                            printInnerCall(c.getMethod().get(index));
                        }
                    }

                }
            }
        }
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
