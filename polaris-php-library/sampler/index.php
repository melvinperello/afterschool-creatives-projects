<?php
/**
 * Created by PhpStorm.
 * User: Jhon Melvin
 * Date: 04/28/2018
 * Time: 10:35 AM
 */

include_once '../Polaris.php';

$builder = new polaris\sql\builder\SimpleQuery();
$builder->addStatement("SELECT")
    ->addStatement("*")
    ->addStatement("FROM")
    ->addStatement("STUDENTS")
    ->addStatementWithParameter("WHERE ID = ? and NAME = ?", ['12']);
