/**
 *
 * Polaris Java Library Examples - Afterschool Creatives "Captivating Creativity"
 *
 * Copyright 2018 Jhon Melvin Perello
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package org.afterschoolcreatives.polaris.examples.java.sql;

import org.afterschoolcreatives.polaris.java.sql.ConnectionFactory;
import org.afterschoolcreatives.polaris.java.sql.ConnectionManager;
import org.afterschoolcreatives.polaris.java.sql.DataRow;
import org.afterschoolcreatives.polaris.java.sql.DataSet;
import org.afterschoolcreatives.polaris.java.sql.builder.SimpleQuery;
import java.sql.SQLException;

/**
 *
 * @author Jhon Melvin
 */
public class E3_QueryBuilder {

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // select the database that you are using.
        connectionFactory.setConnectionDriver(ConnectionFactory.Driver.MariaDB);
        connectionFactory.setHost("localhost");
        connectionFactory.setPort("3306");
        connectionFactory.setDatabaseName("afterschool_toolkit");
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("root");

        /**
         * You can use Connection Manager to easily execute statements.this is a
         * try catch with resource it will close your connection manager after
         * the execution, automatically.
         */
        try (ConnectionManager connectionManager = connectionFactory.createConnectionManager()) {

            SimpleQuery query = new SimpleQuery();
            query.addStatementWithParameter("SELECT") // operation
                    .addStatementWithParameter("name, graduated") // fields to get
                    .addStatementWithParameter("FROM student") // from what table
                    .addStatementWithParameter("WHERE graduated = ?", 0) // condition 1
                    .addStatementWithParameter("AND gender = ?", "FEMALE"); // condition 2

            DataSet ds = connectionManager.fetch(query);

            // get number of entries
            System.out.println("Result: " + ds.size());

            // display entries
            for (int x = 0; x < ds.size(); x++) {
                DataRow row = ds.get(x);
                System.out.println(String.valueOf(x + 1) + ".\t" + row.get("name"));
            }
            // automatically closes the connection manager at the end.
        } catch (SQLException e) {
            // Operation Exception here catch it.
            e.printStackTrace();
        }
    }
}
