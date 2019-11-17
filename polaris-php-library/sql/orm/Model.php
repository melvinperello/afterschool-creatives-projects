<?php
/**
 * Created by PhpStorm.
 * User: Jhon Melvin
 * Date: 04/28/2018
 * Time: 10:08 AM
 */

namespace polaris\sql\orm;

use polaris\sql\builder\QueryBuilder;

interface Model
{
    public function insert($connection): bool;


    public function update($connection): bool;


    public function updateFull($connection): bool;


    public function delete($connection): bool;


    public function find($connection, $id): bool;


    public function findQuery($connection, QueryBuilder $queryBuilder): bool;


    public function findMany($connection, QueryBuilder $queryBuilder): array;

}