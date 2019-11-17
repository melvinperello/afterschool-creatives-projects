<?php
/**
 * Created by PhpStorm.
 * User: DOST-3
 * Date: 04/25/2018
 * Time: 8:37 AM
 */

namespace polaris\sql;


class Connection
{
    public static function connect()
    {
        $factory = new ConnectionFactory();
        $factory->setHost("127.0.0.1");
        $factory->setDatabaseUsername("root");
        $factory->setDatabasePassword("root");
        $factory->setDatabaseName("onlineshopping");
        $factory->setDatabasePort(3306);
        return $factory;
    }
}