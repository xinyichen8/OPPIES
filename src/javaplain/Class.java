package javaplain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Class {
    private String name;
    private String assV;
    private List<Method> method;
    private List<DataMem> dm;
    private List<String> agg;
    private List<String> ass;
    private List<String> parent;
    private boolean protect;
    private boolean fin;
    private boolean strifp;
    private boolean isfinal;
    private boolean isAbs;
    private boolean isstatic;

    private String extend;
    private List<String> child;
    private List<String> implement;


    public Class() {
        this.name = "";
        this.extend = "";
        this.parent = new ArrayList<String>();
        this.child = new ArrayList<String>();
        this.implement = new ArrayList<String>();
        this.isfinal = false;
        this.isstatic = false;
        this.method = new ArrayList<Method>();
        this.dm = new ArrayList<DataMem>();
        this.agg = new ArrayList<String>();
        this.ass = new ArrayList<>();
        this.assV = "";
    }

    public Class(String name, String assV, List<Method> method, List<DataMem> dm, List<String> agg, List<String> ass, List<String> parent, boolean protect, boolean fin, boolean strifp, boolean isfinal, boolean isAbs, boolean isstatic) {
        this.name = name;
        this.assV = assV;
        this.method = method;
        this.dm = dm;
        this.agg = agg;
        this.ass = ass;
        this.parent = parent;
        this.protect = protect;
        this.fin = fin;
        this.strifp = strifp;
        this.isfinal = isfinal;
        this.isAbs = isAbs;
        this.isstatic = isstatic;
    }

    public Class(String name, String assV, List<Method> method, List<DataMem> dm, List<String> agg, List<String> ass, List<String> parent, boolean protect, boolean fin, boolean strifp, boolean isfinal, boolean isAbs, boolean isstatic, String extend, List<String> child, List<String> implement) {
        this.name = name;
        this.assV = assV;
        this.method = method;
        this.dm = dm;
        this.agg = agg;
        this.ass = ass;
        this.parent = parent;
        this.protect = protect;
        this.fin = fin;
        this.strifp = strifp;
        this.isfinal = isfinal;
        this.isAbs = isAbs;
        this.isstatic = isstatic;
        this.extend = extend;
        this.child = child;
        this.implement = implement;
    }

    public void addAssV(String name) {
        this.assV = name;
    }

    public String getAssV() {
        return this.assV;
    }

    public void addAss(String a) {
        ass.add(a);
    }

    ;

    public List<String> getAss() {
        return this.ass;
    }

    public void setAss(List<String> ass) {
        this.ass = ass;
    }

    ;

    public void addAgg(String a) {
        agg.add(a);
    }

    public List<String> getAgg() {
        return this.agg;
    }

    public void addName(String name) {
        this.name = name;
    }

    public void addMethod(Method m) {
        method.add(m);
    }

    public void addDM(DataMem d) {
        this.dm.add(d);
    }


    public boolean isfinal() {
        return isfinal;
    }

    @JsonProperty("static")
    public boolean isstatic() {
        return isstatic;
    }

    public void setfinal(boolean afinal) {
        isfinal = afinal;
    }

    @JsonProperty("static")
    public void setIsstatic(boolean aStatic) {
        isstatic = aStatic;
    }

    public void setExtend(String e) {
        this.extend = e;
        addParent(this.extend);
    }

    public void addImplement(String i) {
        this.implement.add(i);
    }

    public List<Method> getMethod() {
        return this.method;
    }

    public List<DataMem> getDM() {
        return this.dm;
    }

    public List<String> getParent() {

        return this.parent;
    }

    public void setParent(List<String> parent) {
        this.parent = parent;
    }

    public void addParent(String s) {
        this.parent.add(s);
    }

    public void setChild(String c) {
        this.child.add(c);
    }

    public List<String> child() {
        return this.child;
    }

    public String getName() {

        if (implement.size() != 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < implement.size(); i++) {
                if (i < implement.size() - 1) {
                    sb.append(implement.get(i) + ", ");
                } else {
                    sb.append(implement.get(i));
                }
            }
            if (extend.equals("")) {
                return this.name + " implements " + sb.toString();
            } else {
                return this.name + " extends " + this.extend + " implements " + sb.toString();
            }
        } else if (implement.size() == 0) {
            if (!extend.equals(""))
                return this.name + " extends " + this.extend;
        }
        return this.name;
    }

    public boolean isProtect() {
        return protect;
    }

    public void setProtect(boolean protect) {
        this.protect = protect;
    }

    public boolean isFin() {
        return fin;
    }

    public void setFin(boolean fin) {
        this.fin = fin;
    }

    public boolean isStrifp() {
        return strifp;
    }

    public void setStrifp(boolean strifp) {
        this.strifp = strifp;
    }

    public boolean isAbs() {
        return isAbs;
    }

    public void setAbs(boolean isAbs) {
        this.isAbs = isAbs;
    }
}
