package com.example.dbloc;

import java.io.Serializable;

public class Country implements Serializable
{
    private long ID;
    private String Name;
    private String Capital;

    public Country(long ID, String name, String capital) {
        this.ID = ID;
        Name = name;
        Capital = capital;
    }

    public long getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public String getCapital() {
        return Capital;
    }

}
