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
package org.afterschoolcreatives.polaris.examples.java.sql.orm;

import java.sql.SQLException;
import java.util.List;
import org.afterschoolcreatives.polaris.java.sql.ConnectionFactory;
import org.afterschoolcreatives.polaris.java.sql.ConnectionManager;
import org.afterschoolcreatives.polaris.java.sql.builder.SimpleQuery;

/**
 *
 * @author Jhon Melvin
 */
public class ORM_Example {

    public static void main(String[] args) {

        /**
         * You must have a connection source. in this case we have our
         * connection factory instance.
         */
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // select the database that you are using.
        connectionFactory.setConnectionDriver(ConnectionFactory.Driver.MariaDB);
        connectionFactory.setHost("localhost");
        connectionFactory.setPort("3306");
        connectionFactory.setDatabaseName("afterschool_toolkit");
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("root");

        /**
         * SELECT Statements.
         */
        try (ConnectionManager con = connectionFactory.createConnectionManager()) {
            /**
             * Sample # 1 using primary key ID
             */
            Student student = new Student(); // create instance
            if (student.find(con, 1)) { // check if has result.
                // print the result values
                System.out.println("--------------------------------------------");
                System.out.println("Sample # 1 Using Primary Key");
                System.out.println("--------------------------------------------");
                System.out.println(student.getId());
                System.out.println(student.getName());
                System.out.println(student.getGender());
                System.out.println(student.getGraduated());
                System.out.println(student.getDate());
            } else {
                System.err.println("CANNOT FIND");
            }

            /**
             * Sample # 2 using user defined query.
             */
            SimpleQuery query = new SimpleQuery(); // create a query builder
            query.addStatement("SELECT")
                    .addStatement(Student.NAME)
                    .addStatement("FROM")
                    // always use the static variable name for robustness
                    // and consistency
                    .addStatement(Student.TABLE) // never use hard coded field names
                    .addStatement("WHERE")
                    .addStatementWithParameter(Student.ID, 1) // never use hard coded field names
                    // optimization to only retrieve one result
                    .addStatement("LIMIT 1"); // assures that there is only one result
            // create instance
            Student student2 = new Student();
            // check if has resutl
            if (student2.findQuery(con, query)) {
                // print the results
                // fields not included in SELECT will be null
                System.out.println("\n\n");
                System.out.println("--------------------------------------------");
                System.out.println("Sample # 2 Using User Defined Query");
                System.out.println("--------------------------------------------");
                System.out.println(student2.getId());
                System.out.println(student2.getName());
                System.out.println(student2.getGender());
                System.out.println(student2.getGraduated());
                System.out.println(student2.getDate());
            } else {
                System.err.println("CANNOT FIND");
            }

            //------------------------------------------------------------------
            /**
             * Sample # 3 Retrieving multiple results.
             */
            SimpleQuery querySample = new SimpleQuery();
            querySample.addStatement("SELECT")
                    .addStatement(Student.NAME) // get name
                    .addStatement("," + Student.GENDER) // get gender
                    .addStatement("FROM")
                    // always use the static variable name for robustness
                    // and consistency
                    .addStatement(Student.TABLE); // never use hard coded field name
            // create a caller instance
            Student student3 = new Student();
            // create a list that will hold the result
            List<Student> list = student3.findMany(con, querySample);
            /**
             * student3 instance will hold no value it is just used to call find
             * many. findMany will return a list of students that satisfies the
             * query.
             */
            System.out.println("");
            System.out.println("--------------------------------------------");
            System.out.println("Sample # 3 Retrieving multiple results.");
            System.out.println("--------------------------------------------");
            for (Student stud : list) {
                System.out.println(stud.getId());
                System.out.println(stud.getGender());
                System.out.println(stud.getName());
                System.out.println(stud.getGraduated());
                System.out.println(stud.getDate());
                System.out.println("");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        /**
         * INSERT, UPDATE and DELETE.
         */
        try (ConnectionManager con = connectionFactory.createConnectionManager();) {
            System.out.println("INSERT");
            Student a = new Student();
            a.setName("PERELLO, JHON MELVIN NIETO");
            a.setGraduated("GRADUATING");
            a.setGender("MALE");
            //------------------------------------------------------------------
            /**
             * Insert.
             */
            boolean isThereAGeneratedKey = a.insert(con);
            //------------------------------------------------------------------
            if (isThereAGeneratedKey) {
                /**
                 * insert method returns true if there is a generated key. the
                 * key will be then transferred to the primary key field. in
                 * this case getId.
                 */
                System.out.println("AUTO_INCREMENT: " + a.getId());
            }

            /**
             * Update.
             */
            a.setGraduated("OUT OF SCHOOL YOUTH");
            //------------------------------------------------------------------
            // UPDATE
            boolean isSomethingAffected = a.update(con);
            /**
             * The update method relies on the primary key. if there is no
             * primary key or the value is null an exception will be thrown.
             */
            //------------------------------------------------------------------
            if (isSomethingAffected) {
                // if there was a affected record upon executing the update.
                System.out.println("A Record Was Updated");
            }

            //------------------------------------------------------------------
            /**
             * Delete. also relies on the primary key.
             */
            boolean deleted = a.delete(con);
            //------------------------------------------------------------------
            System.out.println("DELETED: " + deleted);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }

        // TRANSACTION SAMPLE
//        ConnectionManager con = null;
//        try {
//            con = connectionFactory.createConnectionManager();
//            con.transactionStart(); // start transaction
//
//            Student a = new Student();
//            a.setName("PERELLO, JHON MELVIN NIETO");
//            a.setGraduated("GRADUATING");
//            a.setGender("MALE");
//            boolean isThereAGeneratedKey = a.insert(con);
//            if (isThereAGeneratedKey) {
//                // print the generated key
//                System.out.println(a.getId());
//            }
//            a.setGraduated("OUT OF SCHOOL YOUTH");
//            a.update(con);
//            // commit changes
//            con.transactionCommit();
//        } catch (SQLException e) {
//            // rollback if there is an error
//            try {
//                if (con != null) {
//                    con.transactionRollBack();
//                }
//            } catch (SQLException roolbackEx) {
//                // cannot rollback changes
//            }
//        } finally {
//            // make sures that the connection is closed
//            if (con != null) {
//                con.closeQuietly(); // quiet, ignores error
//            }
//        }
    }
}
