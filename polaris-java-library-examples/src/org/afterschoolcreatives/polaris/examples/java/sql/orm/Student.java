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

import java.util.Date;
import org.afterschoolcreatives.polaris.java.sql.orm.PolarisRecord;
import org.afterschoolcreatives.polaris.java.sql.orm.annotations.Column;
import org.afterschoolcreatives.polaris.java.sql.orm.annotations.PrimaryKey;
import org.afterschoolcreatives.polaris.java.sql.orm.annotations.Table;
import org.afterschoolcreatives.polaris.java.sql.orm.annotations.FetchOnly;

/**
 * A Simple Model Structure.
 *
 * @author Jhon Melvin
 */
@Table(Student.TABLE) // table name in the database
public class Student extends PolarisRecord {

    public Student() {
        //
    }
    //--------------------------------------------------------------------------
    // Declare Field Names
    //--------------------------------------------------------------------------
    public final static String TABLE = "student";
    public final static String ID = "id";
    public final static String NAME = "name";
    public final static String GENDER = "gender";
    public final static String GRADUATED = "graduated";
    public final static String DATE = "date";
    //--------------------------------------------------------------------------
    // Annotate Primary Key if any
    //--------------------------------------------------------------------------
    @FetchOnly
    @PrimaryKey
    @Column(Student.ID)
    private Integer id;
    //--------------------------------------------------------------------------
    // Annotate variables as database fields.
    //--------------------------------------------------------------------------
    @Column(Student.NAME)
    private String name;

    @Column(Student.GENDER)
    private String gender;

    @Column(Student.GRADUATED)
    private String graduated;

    @FetchOnly
    @Column(Student.DATE)
    private Date date;

    //--------------------------------------------------------------------------
    // Getters
    //--------------------------------------------------------------------------
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getGraduated() {
        return graduated;
    }

    public Date getDate() {
        return date;
    }

    //--------------------------------------------------------------------------
    // Setters
    //--------------------------------------------------------------------------
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setGraduated(String graduated) {
        this.graduated = graduated;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    //<@polaris:ignore>
    //</@polaris:ignore>
}
