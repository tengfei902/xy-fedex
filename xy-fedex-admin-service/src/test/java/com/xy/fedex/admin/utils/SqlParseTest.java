package com.xy.fedex.admin.utils;

import com.facebook.presto.sql.parser.ParsingOptions;
import com.facebook.presto.sql.parser.SqlParser;
import com.facebook.presto.sql.tree.Query;
import com.facebook.presto.sql.tree.QueryBody;
import com.facebook.presto.sql.tree.Statement;
import org.junit.jupiter.api.Test;

public class SqlParseTest {

    @Test
    public void testParseSql() {
        Statement statement = new SqlParser().createStatement("select sum(a) as a,sum(b) as b,c,d from t where dt = 20230101 group by c,d", ParsingOptions.builder().build());
        if(statement instanceof Query) {
            Query query = (Query) statement;
            QueryBody queryBody = query.getQueryBody();
        }
    }
}
