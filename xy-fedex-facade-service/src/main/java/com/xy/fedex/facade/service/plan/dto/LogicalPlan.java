package com.xy.fedex.facade.service.plan.dto;

import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LogicalPlan {

    private SQLSelect logicalSelect;

    private Node node;

    public LogicalPlan(SQLSelect logicalSelect) {
        this.logicalSelect = logicalSelect;
    }

    public void add(Node node) {
        this.node = node;
    }

    public List<Node> lrd() {
        List<Node> nodes = new ArrayList<>();
        postOrder(this.node,nodes);
        return nodes;
    }

    private void postOrder(Node x, List<Node> nodes) {
        if(Objects.isNull(x)) {
            return;
        }
        postOrder(x.left,nodes);
        postOrder(x.right,nodes);
        nodes.add(x);
    }

    @Data
    public static class Node {
        private SQLSelect sql;
        private String alias;
        private Node left;
        private Node right;

        public Node(SQLSelect sql) {
            this.sql = sql;
        }

        public Node(SQLSelect sql, String alias) {
            this.sql = sql;
            this.alias = alias;
        }

        public static Node newNode(String sql, String alias) {
            return null;
        }

        public static Node newNode(SQLSelect sql, String alias) {
            return new Node(sql,alias);
        }

        public void add(Node x) {
            if(Objects.isNull(this.left)) {
                this.left = x;
            } else {
                this.right = x;
            }
        }
    }
}
