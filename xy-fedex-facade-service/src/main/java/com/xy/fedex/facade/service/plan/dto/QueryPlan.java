package com.xy.fedex.facade.service.plan.dto;

import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import lombok.Data;

public class QueryPlan {

    private SQLSelect logicalSelect;

    private Node root;

    public QueryPlan(SQLSelect logicalSelect) {
        this.logicalSelect = logicalSelect;
    }

    public void add(Node node) {

    }

    @Data
    public static class Node {
        private MySqlSelectQueryBlock sql;
        private String alias;
        private Node left;
        private Node right;

        public Node(MySqlSelectQueryBlock sql) {
            this.sql = sql;
        }

        public Node(MySqlSelectQueryBlock sql,String alias) {
            this.sql = sql;
            this.alias = alias;
        }

        public static Node newNode(String sql,String alias) {
            return null;
        }

        public static Node newNode(MySqlSelectQueryBlock sql, String alias) {
            return null;
        }

        public void add(Node x) {

        }
    }
}
