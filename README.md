# Hive Metastore REST API

This project provides some basic functionalities present in WebHCat, in order to facilitate 
the update process due to its [discontinuation](https://docs.cloudera.com/cdp-private-cloud-upgrade/latest/upgrade/topics/hive-unsupported.html) in
[Cloudera Data Platform](https://www.cloudera.com/products/cloudera-data-platform.html). 

It's strongly recommended to refactor the jobs that use WebHCat to adapt to new technologies as soon as possible.

In no way is this project intended to serve as a replacement for WebHCat.

**PLEASE, USE AT YOUR OWN RISK!**

## Application Setup

This application can work in two backend modes:

1. Any JPA compatible database like Oracle, MySQL, MariaDB, PostgreSQL etc.
2. [Cloudera's JDBC Driver for Hive](https://www.cloudera.com/downloads/connectors/hive/jdbc) using the **sys** database

### JPA Backend Setup

To set up a JPA backend as Hive Metastore Database, please add `jpa` to the profile list using the property 
`spring.profiles.active`.

MySQL Example: 
```
-Dspring.profiles.active=jpa \
-Dspring.datasource.url=jdbc:mysql://nightly7x-us-zx-1.nightly7x-us-zx.root.hwx.site:3306/hive1 \
-Dspring.datasource.username=hive1 \
-Dspring.datasource.password=hive1
```

PostgreSQL Example:
```
-Dspring.profiles.active=jpa \
-Dspring.datasource.url=jdbc:postgres://nightly7x-us-zx-1.nightly7x-us-zx.root.hwx.site:5432/hive1 \
-Dspring.datasource.username=hive1 \
-Dspring.datasource.password=hive1
```

### Hive Backend Setup

To set up Hive as Hive Metastore Database (using sys database), please add `jdbc` to the profile list using 
the property `spring.profiles.active`.

Example: 
```
-Dspring.profiles.active=jdbc \
-Dspring.datasource.url=jdbc:jdbc:hive2://mydatahub-gateway.ps-sandb.a465-9q4k.cloudera.site:443/sys;ssl=1;transportMode=http;httpPath=mydathub/cdp-proxy-api/hive \
-Dspring.datasource.username=username \
-Dspring.datasource.password=workload-password \
-Dspring.datasource.driver-class-name=com.cloudera.hive.jdbc.HS2Driver
```

> NOTE: You can download a compatible Hive Driver at [Cloudera Download Page](https://www.cloudera.com/downloads/connectors/hive/jdbc).

## Caching

Optionally it's possible to enable caching to relieve pressure on the backend.

To enable this feature, please add `cache` to the profile list using the property `spring.profiles.active`.

You can also specify how long (in seconds) the cached data lives before expiring (default is 300 seconds)

Example: 
```
-Dspring.profiles.active=jpa,cache \
-Dapp.cache.timeout=300
```

## Authentication

Authentication is disabled by default. To enable the authentication, please add `authentication` to the profile 
list using the property `spring.profiles.active`. It's also required to define the credentials file and using the 
`app.auth.credentialsFile` parameter.

Example: 
```
-Dspring.profiles.active=jpa,authentication,cache \
-Dapp.auth.credentialsFile=/path/to/credentials.properties
```

Credentials file format:
```
john=password123
matt=ultrasecret
```

## IP Access Filtering

Limiting the IP Access is disabled by default. To enable this feature, please add `ip-filter` to the profile list 
using the property `spring.profiles.active`. It's also required to define the credentials file and using the 
`app.auth.credentialsFile` parameter.

Example:
```
-Dspring.profiles.active=ip-filter \
-Dapp.auth.ip-white-list-file=/path/to/ip-whitelist
```

IP whitelist file format:
```
127.0.0.1
192.168.1.0/24
```

## Customizing the Application

Steps to customize the application:

1. Choose the correct JDBC version.
2. If your are using Hive JDBC, please [download the latest version from Cloudera](https://www.cloudera.com/downloads/connectors/hive/jdbc).
   After downloaded and decompress, put the `HiveJDBC42.jar` file in to `/lib` folder (you may create one if necessary). 
3. If your are using a direct connection to the Hive Metastore Database, provide the correct JDBC version. You can
   change the `pom.xml` file to take advantage of automatically download.
4. Compile the application according to your driver. You will need Java 8+ and Apache Maven. Use the command 
   `mvn -Phive clean package` or `mvn -Pmysql clean package` or `mvn -Ppostgresql clean package` 
5. When finished, the JAR application will be available as `target/metastore-rest-api.jar`.

## Running the Server Application

### JPA Mode

Example:
```
java \
    "-Dspring.profiles.active=jpa" \
    "-Dspring.datasource.url=jdbc:mysql://nightly7x-us-zo-1.nightly7x-us-zo.root.hwx.site:3306/hive1" \
    "-Dspring.datasource.username=hive1" \
    "-Dspring.datasource.password=hive1" \
    -jar "/opt/metastore-rest-api/metastore-rest-api.jar"
```

### JDBC Mode

Example:
```
java \
    "-Dspring.profiles.active=jdbc" \
    "-Dspring.datasource.url=jdbc:hive2://efranceschi-de-gateway.ps-sandb.a465-9q4k.cloudera.site:443/sys;ssl=1;transportMode=http;httpPath=efranceschi-de/cdp-proxy-api/hive" \
    "-Dspring.datasource.username=efranceschi" \
    "-Dspring.datasource.password=Clouder@123" \
    "-Dspring.datasource.driver-class-name=com.cloudera.hive.jdbc.HS2Driver" \
    -jar "/opt/metastore-rest-api/metastore-rest-api.jar"
```

### JPA + Cache + Secure Mode

Example:
```
java \
    "-Dspring.profiles.active=jpa,cache,authentication,ip-filter" \
    "-Dspring.datasource.url=jdbc:mysql://nightly7x-us-zo-1.nightly7x-us-zo.root.hwx.site:3306/hive1" \
    "-Dspring.datasource.username=hive1" \
    "-Dspring.datasource.password=hive1" \
    "-Dapp.cache.timeout=300" \
    "-Dapp.auth.ipWhiteListFile=/opt/metastore-rest-api/ip-whitelist.conf" \
    "-Dapp.auth.credentialsFile=/opt/metastore-rest-api/users.properties" \
    -jar "/opt/metastore-rest-api/metastore-rest-api.jar"
```

## Client usage

Example:
```
curl -u user:password http://localhost:8080/templeton/v1/ddl/database/default/table/web_logs/partition
```

Sample output:
```json
{
   "database" : "default",
   "partitions" : [
      {
         "name" : "date=2015-11-18",
         "values" : [
            {
               "columnName" : "date",
               "columnValue" : "2015-11-18"
            }
         ]
      },
      {
         "name" : "date=2015-11-20",
         "values" : [
            {
               "columnName" : "date",
               "columnValue" : "2015-11-20"
            }
         ]
      },
      {
         "name" : "date=2015-11-21",
         "values" : [
            {
               "columnName" : "date",
               "columnValue" : "2015-11-21"
            }
         ]
      },
      {
         "name" : "date=2015-11-19",
         "values" : [
            {
               "columnName" : "date",
               "columnValue" : "2015-11-19"
            }
         ]
      }
   ],
   "table" : "web_logs"
}
```
