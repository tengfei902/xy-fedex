package com.xy.fedex.facade.utils;

import java.io.Serializable;
import java.util.Objects;

public class Tupple<T1,T2> implements Serializable {
    private T1 _1;
    private T2 _2;

    public Tupple (T1 _1,T2 _2) {
        this._1 = _1;
        this._2 = _2;
    }

    public T1 get_1() {
        return _1;
    }

    public T2 get_2() {
        return _2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tupple<?, ?> tupple = (Tupple<?, ?>) o;
        return Objects.equals(_1, tupple._1) && Objects.equals(_2, tupple._2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_1, _2);
    }
}
