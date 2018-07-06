package com.fivefivelike.androidprojectdemo.entity;

import java.io.Serializable;

/**
 * Created by liugongce on 2017/12/7.
 */

public class PathEntity implements Serializable {
    String thumilpath;
    String path;

    public PathEntity(String thumilpath, String path) {
        this.thumilpath = thumilpath;
        this.path = path;
    }

    public String getThumilpath() {
        return thumilpath;
    }

    public void setThumilpath(String thumilpath) {
        this.thumilpath = thumilpath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        PathEntity that = (PathEntity) o;

        if (thumilpath != null ? !thumilpath.equals(that.thumilpath) : that.thumilpath != null)
            return false;
        return path != null ? path.equals(that.path) : that.path == null;
    }

    @Override
    public int hashCode() {
        int result = thumilpath != null ? thumilpath.hashCode() : 0;
        result = 31 * result + (path != null ? path.hashCode() : 0);
        return result;
    }


}
