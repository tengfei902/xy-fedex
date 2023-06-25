package com.xy.fedex.catalog.typehandlers;

import com.xy.fedex.catalog.po.MetricModelPO;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MetricModelTypeHandler implements TypeHandler<MetricModelPO> {

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, MetricModelPO metricModelPO, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public MetricModelPO getResult(ResultSet resultSet, String s) throws SQLException {
        return null;
    }

    @Override
    public MetricModelPO getResult(ResultSet resultSet, int i) throws SQLException {
        return null;
    }

    @Override
    public MetricModelPO getResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
