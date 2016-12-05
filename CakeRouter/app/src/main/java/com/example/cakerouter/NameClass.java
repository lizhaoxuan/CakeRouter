package com.example.cakerouter;

import java.io.Serializable;

/**
 * Created by lizhaoxuan on 2016/12/5.
 */

public class NameClass implements Serializable {

    private String name;

    public NameClass() {
    }

    public NameClass(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
