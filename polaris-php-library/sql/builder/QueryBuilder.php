<?php
/**
 * Created by PhpStorm.
 * User: Jhon Melvin
 * Date: 04/28/2018
 * Time: 10:15 AM
 */

namespace polaris\sql\builder;
abstract class QueryBuilder
{
    protected $queryString;
    protected $parameterList;


    public function __construct()
    {
        $this->queryString = "";
        $this->parameterList = [];
    }

    /**
     * @return string
     */
    public function getQueryString(): string
    {
        return $this->queryString;
    }

    /**
     * @return array
     */
    public function getParameterList(): array
    {
        return $this->parameterList;
    }


}