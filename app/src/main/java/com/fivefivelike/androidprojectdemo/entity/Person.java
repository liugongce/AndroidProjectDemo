package com.fivefivelike.androidprojectdemo.entity;

import java.util.List;

/**
 * Created by liugongce on 2017/10/13.
 */
public class Person {
    private String age;
    private String name;
    private String sex;

    private List<String> sexList;
    private int num;
    private boolean isCheck;
    public Person() {
    }

    public Person(String age, String name, String sex) {
        this.age = age;
        this.name = name;
        this.sex = sex;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<String> getSexList() {
        return sexList;
    }

    public void setSexList(List<String> sexList) {
        this.sexList = sexList;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public class  Student{

    }
}
