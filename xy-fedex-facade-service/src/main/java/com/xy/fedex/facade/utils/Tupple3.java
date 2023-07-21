package com.xy.fedex.facade.utils;

import java.io.Serializable;

public class Tupple3<T1,T2,T3> implements Serializable {
    private T1 _1;
    private T2 _2;
    private T3 _3;

    public Tupple3(T1 _1,T2 _2,T3 _3) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
    }

    public T1 get_1() {
        return _1;
    }

    public T2 get_2() {
        return _2;
    }

    public T3 get_3() {
        return _3;
    }
}
