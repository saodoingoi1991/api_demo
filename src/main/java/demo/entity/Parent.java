package demo.entity;

import java.util.List;

public class Parent {
    private int it;
    private String nameParent;
    private List<Greeting> lst;

    public Parent() {
    }

    public int getIt() {
        return it;
    }

    public void setIt(int it) {
        this.it = it;
    }

    public String getNameParent() {
        return nameParent;
    }

    public void setNameParent(String nameParent) {
        this.nameParent = nameParent;
    }

    public List<Greeting> getLst() {
        return lst;
    }

    public void setLst(List<Greeting> lst) {
        this.lst = lst;
    }
}
