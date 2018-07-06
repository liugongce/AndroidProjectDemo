package com.fivefivelike.androidprojectdemo.entity;

import java.util.List;

/**
 * Created by liugongce on 2017/10/9.
 */

public class WavebarEntity {

    /**
     * keylist : [{"id":"123479","name":"测试","companyname":"五五来客","sex":"1","type":"2"}]
     * namelist : ["C","Z"]
     * datalist : [{"name":"C","list":[{"id":"123479","name":"测试","companyname":"五五来客","sex":"1","letter":"C"}]},{"name":"Z","list":[{"id":"54","name":"在家","companyname":"是因为这","sex":"2","letter":"Z"}]}]
     * b_red : 0
     * s_red : 0
     */

    private List<ListBean> keylist;
    private List<String> namelist;
    private List<DatalistBean> datalist;

    public List<ListBean> getKeylist() {
        return keylist;
    }

    public void setKeylist(List<ListBean> keylist) {
        this.keylist = keylist;
    }

    public List<String> getNamelist() {
        return namelist;
    }

    public void setNamelist(List<String> namelist) {
        this.namelist = namelist;
    }

    public List<DatalistBean> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<DatalistBean> datalist) {
        this.datalist = datalist;
    }


    public static class DatalistBean {
        /**
         * name : C
         * list : [{"id":"123479","name":"测试","companyname":"五五来客","sex":"1","letter":"C"}]
         */

        private String name;
        private List<ListBean> list;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }


    }

    public static class ListBean {
        /**
         * id : 123479
         * name : 测试
         * companyname : 五五来客
         * sex : 1
         * letter : C
         */

        private String id;
        private String name;
        private String companyname;
        private String sex;
        private String letter;
        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCompanyname() {
            return companyname;
        }

        public void setCompanyname(String companyname) {
            this.companyname = companyname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getLetter() {
            return letter;
        }

        public void setLetter(String letter) {
            this.letter = letter;
        }
    }
}
