<?php
/**
 * Created by PhpStorm.
 * User: DOST-3
 * Date: 04/25/2018
 * Time: 8:44 AM
 */

class Polaris
{


    public static function load()
    {
        Polaris::loadSql();
    }

    /**
     * Auto Load Classes for Polaris Library.
     */
    public static function loadSql()
    {
        spl_autoload_register(function ($className) {
            // SQL
            include_once 'sql/Connection.php';
            include_once 'sql/ConnectionFactory.php';
            // Load builder
            include_once 'sql/builder/QueryBuilder.php';
            include_once 'sql/builder/SimpleQuery.php';
            // Load ORM
            include_once 'sql/orm/Model.php';

        });
    }
}

/**
 * Call Load Script.
 */
Polaris::load();