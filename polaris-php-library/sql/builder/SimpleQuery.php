<?php
/**
 * Created by PhpStorm.
 * User: Jhon Melvin
 * Date: 04/28/2018
 * Time: 10:19 AM
 */

namespace polaris\sql\builder;
class SimpleQuery extends QueryBuilder
{
    /**
     * SimpleQuery constructor. Construct Simple Query call parent constructor.
     */
    public function __construct()
    {
        parent::__construct();
    }


    public function addStatementWithParameter(string $statement, array $parameters): SimpleQuery
    {
        $this->addStatement($statement);
        $this->addParameter($parameters);
        return $this;
    }

    public function addStatement(string $statement): SimpleQuery
    {
        $this->queryString .= (" " . $statement . " ");
        return $this;
    }

    public function addParameter(array $values): SimpleQuery
    {
        $this->parameterList = array_merge($this->parameterList, $values);
        return $this;
    }

}