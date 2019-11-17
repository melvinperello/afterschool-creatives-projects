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
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Jhon Melvin
 */
public class E4_SQLite {

    public static void main(String[] args) {
        /**
         * Please download the JDBC Driver for SQLITE
         * 
         * https://bitbucket.org/xerial/sqlite-jdbc/downloads/
         */
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // select the database that you are using.
        connectionFactory.setConnectionDriver(ConnectionFactory.Driver.SQLite);
        connectionFactory.setSQLiteURL("C:\\Users\\Jhon Melvin\\Desktop\\sample.db");

        /**
         * You can create connection and use that connection to execute your
         * statements.
         */
        try {
            Connection con = connectionFactory.createConnection();
            if (con.isClosed()) {
                System.out.println("Failed: Connection is Closed");
            } else {
                System.out.println("Success: Database Connected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
