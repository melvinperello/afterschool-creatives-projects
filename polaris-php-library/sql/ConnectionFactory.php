<?php
/**
 * Created by PhpStorm.
 * User: DOST-3
 * Date: 04/25/2018
 * Time: 8:27 AM
 */

namespace polaris\sql;

class ConnectionFactory
{
    /**
     * ConnectionFactory constructor.
     */
    public function __construct()
    {
        $this->host = null;
        $this->databasePort = null;
        $this->databaseUsername = null;
        $this->databasePassword = null;
        $this->databaseName = null;
        $this->databaseNamedPipe = null;
        //
        $this->connection = null;
    }

    /**
     * Destruct Method.
     */
    public function __destruct()
    {
        // Close the connetion if was already set.
        if (isset($this->connection)) {
            mysqli_close($this->connection);
        }
    }
    #--------------------------------------------------
    # Connection Factory Properties
    #--------------------------------------------------
    private $host;
    private $databasePort;
    private $databaseUsername;
    private $databasePassword;
    private $databaseName;
    private $databaseNamedPipe;
    #--------------------------------------------------
    # Class Properties
    #--------------------------------------------------
    private $connection;
    #--------------------------------------------------
    # Getters
    #--------------------------------------------------
    /**
     * @return mixed
     */
    public function getHost()
    {
        return $this->host;
    }

    /**
     * @return mixed
     */
    public function getDatabasePort()
    {
        return $this->databasePort;
    }

    /**
     * @return mixed
     */
    public function getDatabaseUsername()
    {
        return $this->databaseUsername;
    }

    /**
     * @return mixed
     */
    public function getDatabasePassword()
    {
        return $this->databasePassword;
    }

    /**
     * @return mixed
     */
    public function getDatabaseName()
    {
        return $this->databaseName;
    }

    /**
     * @return mixed
     */
    public function getDatabaseNamedPipe()
    {
        return $this->databaseNamedPipe;
    }
    #--------------------------------------------------
    # Setter
    #--------------------------------------------------
    /**
     * @param mixed $host
     */
    public function setHost(string $host)
    {
        $this->host = $host;
    }

    /**
     * @param mixed $databasePort
     */
    public function setDatabasePort(int $databasePort)
    {
        $this->databasePort = $databasePort;
    }

    /**
     * @param mixed $databaseUsername
     */
    public function setDatabaseUsername(string $databaseUsername)
    {
        $this->databaseUsername = $databaseUsername;
    }

    /**
     * @param mixed $databasePassword
     */
    public function setDatabasePassword(string $databasePassword)
    {
        $this->databasePassword = $databasePassword;
    }

    /**
     * @param mixed $databaseName
     */
    public function setDatabaseName(string $databaseName)
    {
        $this->databaseName = $databaseName;
    }

    /**
     * @param mixed $databaseNamedPipe
     */
    public function setDatabaseNamedPipe(string $databaseNamedPipe)
    {
        $this->databaseNamedPipe = $databaseNamedPipe;
    }
    #--------------------------------------------------
    # Class Method
    #--------------------------------------------------
    public function getConnection()
    {
        if (!isset($this->connection)) {
            $this->connection = mysqli_connect($this->getHost(), $this->getDatabaseUsername(), $this->getDatabasePassword(), $this->getDatabaseName(), $this->getDatabasePort(), $this->getDatabaseNamedPipe());
        }
        return $this->connection;
    }


}