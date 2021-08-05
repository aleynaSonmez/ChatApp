package com.example.chatappproje;

public class Veriler {

    String id;
    String numara;
    String name;

    public Veriler(String id , String numara,String name) {
        super();
        this.id=id;
        this.numara= numara;
        this.name=name;
    }

    public Veriler(String numara)
    {
        this.numara=numara;

    }
    public Veriler()
    {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumara() {
        return numara;
    }

    public void setNumara(String numara) {
        this.numara = numara;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
