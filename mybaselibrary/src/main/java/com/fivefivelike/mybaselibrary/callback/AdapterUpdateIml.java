package com.fivefivelike.mybaselibrary.callback;

import java.util.List;

/**
 * Created by liugongce on 2018/1/19.
 */

public interface AdapterUpdateIml<T> {
    void updateDatas(List<? extends T> data);

    void addDatas(List<? extends T> data);

    void setDatas(List<T> data);

    List<? extends T> getDatas();

    void notifyChanged();

}
