# _MFA_
![github](https://img.shields.io/github/stars/gadelhati/mfa-back?style=social "Github")
![java](https://img.shields.io/badge/java-21-2145E8 "Java")
![postgresql](https://img.shields.io/badge/postgresql-15.1.1-6495ED "PostgreSQL")
![springboot](https://img.shields.io/badge/springboot-3.2.3-53D05D "Spring Boot")

### Necessary Tech stack:
|     Name     | Source |                 File name version | Link for download                                                                 |
|:------------:|:------:|----------------------------------:|:----------------------------------------------------------------------------------|
|  `intellij`  |  IDE   |               idealC-2024.1.1.exe | https://www.jetbrains.com/idea/download/download-thanks.html?platform=windows     |
|    `java`    |  JDK   |        jdk-21_windows-x64_bin.exe | https://download.oracle.com/java/21/latest/jdk-21_windows-x64_bin.exe             |
| `postgresql` |  SGBD  | postgresql-16.3.1-windows-x64.exe | https://sbp.enterprisedb.com/getfile.jsp?fileid=1259019                           |
|  `pgadmin4`  |        |              pgadmin4-8.6-x64.exe | https://ftp.postgresql.org/pub/pgadmin/pgadmin4/v8.6/windows/pgadmin4-8.6-x64.exe |

## Description
Authentication and authorization.

### Roadmap
#### in development
- [ ] @PostFilter(hasPermission('')), hasAuthority('Admin')
#### in concept
- [ ] service to provide sidebar access
- [x] change application.properties to [dev using h2, and prod using postgres]
- [ ] add Internationalization to ValidationMessages.properties
- [ ] service to provide color palete
- [x] improve refresh token

## Summary
* [How to work with this project](#how-to-work-with-this-project)
* [Configuration](#configuration)
  - [application-properties](#application-properties)
  - [JDBC URL on h2](#jdbc-url-on-h2)
  - [Dependencies](#dependencies)
* [Deploy](#deploy)
* [Links](#links)
* [Git tips](#git-tips)
* [HTTP Status code list](#http-status-code-list)
* [Developers](#developers)
* [Licence](#licence)

## How to work with this project

create this project
> [https://start.spring.io/](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.0.2&packaging=war&jvmVersion=19&groupId=br.eti.gadelha&artifactId=mfa&name=mfa&description=Gadelha's%20Spring%20Boot%20Project&packageName=br.eti.gadelha.mfa&dependencies=lombok,h2,security,data-jpa,postgresql,actuator,validation)

type in intellij terminal tab
```
# clone the project
git clone https://github.com/gadelhati/mfa-back

# install dependencies
mvn dependency:copy-dependencies

# run project
mvn spring-boot:run

# how to stop application on localhost
netstat -a -n -o
tskill "NÚMERO DO PID"

# how to create file war
mvn clean package
# ...two files with the extension .war will be created, the one with the shortest name will be used.

# how to select page on get request
{{mfa-local}}/user/?page=2&size=5
{{mfa-local}}/user/?sort=name,desc
```

## Configuration
### application properties
Open `src/main/resources/application.properties`

```properties
spring.application.name = mfa
server.servlet.context-path = /mfa

spring.datasource.url= jdbc:postgresql://localhost:5432/mfa
spring.datasource.username= postgres
spring.datasource.password= password
spring.datasource.driver-class-name = org.postgresql.Driver
spring.datasource.platform = postgres
spring.datasource.initialization-mode = always

spring.jpa.show-sql = false
spring.jpa.properties.hibernate.dialect = org.hibernate.spatial.dialect.postgis.PostgisPG95Dialect
spring.jpa.properties.hibernate.default_schema = mfa
spring.jpa.hibernate.ddl-auto= create

```
### JDBC URL on h2:
```
2021-04-18 21:44:01.317  INFO 7560 --- [  restartedMain] o.s.b.a.h2.H2ConsoleAutoConfiguration    : H2 console available at '/h2-ui'. Database available at 'jdbc:h2:mem:testdb'
```
in case:
```
jdbc:h2:mem:testdb
```
### Dependencies
```xml
<dependencies>
		<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-actuator -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
			<version>${springframework.version}</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
			<version>${springframework.version}</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
			<version>${springframework.version}</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-validation -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
			<version>${springframework.version}</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
			<version>${springframework.version}</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/com.h2database/h2 -->
		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<version>2.2.224</version>
			<scope>test</scope>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.postgresql/postgresql -->
		<dependency>
			<groupId>org.postgresql</groupId>
			<artifactId>postgresql</artifactId>
			<version>42.7.1</version>
			<scope>runtime</scope>
		</dependency>
		<!-- https://mvnrepository.com/artifact/net.postgis/postgis-jdbc -->
		<dependency>
			<groupId>net.postgis</groupId>
			<artifactId>postgis-jdbc</artifactId>
			<version>2023.1.0</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<version>1.18.30</version>
			<scope>provided</scope>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.apache.tomcat.embed/tomcat-embed-websocket -->
		<dependency>
			<groupId>org.apache.tomcat.embed</groupId>
			<artifactId>tomcat-embed-websocket</artifactId>
			<version>11.0.0-M15</version>
			<scope>provided</scope>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<version>${springframework.version}</version>
			<scope>test</scope>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.springframework.security/spring-security-test -->
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-test</artifactId>
			<version>6.2.0</version>
			<scope>test</scope>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.hibernate.orm/hibernate-envers -->
		<dependency>
			<groupId>org.hibernate.orm</groupId>
			<artifactId>hibernate-envers</artifactId>
			<version>6.4.0.Final</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-api -->
		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt-api</artifactId>
			<version>0.11.5</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-impl -->
		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt-impl</artifactId>
			<version>0.11.5</version>
			<scope>runtime</scope>
		</dependency>
		<!-- https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-jackson -->
		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt-jackson</artifactId>
			<version>0.11.5</version>
			<scope>runtime</scope>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.mfatruct/mfatruct -->
		<dependency>
			<groupId>org.mfatruct</groupId>
			<artifactId>mfatruct</artifactId>
			<version>1.5.5.Final</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.hibernate.orm/hibernate-core -->
		<dependency>
			<groupId>org.hibernate.orm</groupId>
			<artifactId>hibernate-core</artifactId>
			<version>6.4.0.Final</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.hibernate.orm/hibernate-spatial -->
		<dependency>
			<groupId>org.hibernate.orm</groupId>
			<artifactId>hibernate-spatial</artifactId>
			<version>6.4.0.Final</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui -->
		<dependency>
			<groupId>org.springdoc</groupId>
			<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
			<version>2.3.0</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.locationtech.jts/jts-core -->
		<dependency>
			<groupId>org.locationtech.jts</groupId>
			<artifactId>jts-core</artifactId>
			<version>1.19.0</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/com.graphhopper.external/jackson-datatype-jts -->
		<dependency>
			<groupId>com.graphhopper.external</groupId>
			<artifactId>jackson-datatype-jts</artifactId>
			<version>2.14</version>
		</dependency>
	</dependencies>
```

## Deploy
### Deploy on Tomcat Server
Type in your linux server
```
service tomcat stop
rm /opt/tomcat/webapps/<old_version>.war
rm -Rfv /opt/tomcat/webapps/<old_version>
cp /home/<user>/<application_name>.war /opt/tomcat/webapps/
chown tomcat:tomcat /opt/tomcat/webapps/<application_name>.war
chmod 755 <application_name>
service tomcat start
```
## Links
### API download link

> [https://github.com/gadelhati/mfa-back](https://github.com/gadelhati/mfa-back)

### API running locally

> [http://localhost:8080/mfa](http://localhost:8080/mfa)
### Endpoint
These are the paths to services:
- [x] [CREATE](http://localhost:8080/mfa/user) - path to item creation;
- [x] [RETRIEVE](http://localhost:8080/mfa/user/id) - path to retrieve of an item by id;
- [x] [RETRIEVE](http://localhost:8080/mfa/user/search) - path to retrieve of an item by search or all items without source;
- [x] [UPDATE](http://localhost:8080/mfa/user/id) - path to update an item;
- [x] [DELETE](http://localhost:8080/mfa/user/id) - path to delete an item;
- [x] [DELETE_ALL](http://localhost:8080/mfa/user) - path to delete all items;

## Git tips

### Commit types
* feature: Um novo recurso para a aplicação, e não precisa ser algo grande, mas apenas algo que não existia antes e que a pessoa final irá acessar.
* fix: Correções de bugs
* docs: Alterações em arquivos relacionados à documentações
* style: Alterações de estilização, formatação etc
* refactor: Um codigo de refatoração, ou seja, que foi alterado, que tem uma mudança transparente para o usuário final, porém uma mudança real para a aplicação
* perf: Alterações relacionadas à performance
* test: Criação ou modificação de testes
* chore: Alterações em arquivos de configuração, build, distribuição, CI, ou qualquer outra coisa que não envolva diretamente o código da aplicação para o usuário final

type in terminal
```
# initialize git repository, create git folder
git init

# clone the project and build locally
git clone https://github.com/gadelhati/mfa-back

# add all files on the staging area
git add .

# shows tracked files on the staging
git status

# packs tracked files on the staging
git commit -m "[ID]<type_of_commit>:<message>"

# shows commit history
git log

# define main branch
git branch -M main

# add remote repository, don't forget "Git Credential Manager Core"
git remote add origin https://*.git

# sends changes to the repository
git push -u origin <branch_name>

# update branch
git pull

# create new branch
git checkout -b <branch_name>

# delete a local branch
git branch -d <branch_name>

# delete a remote branch
git push --delete origin <branch_name>

# show all branch
git branch

# upload a branch
git push -u <branch_name>

# update branch
git rebase main

# upload your changes
git push -f

# list tag
git tag

# list tag by key
git tag -l "v1.8.5*"

# create a tag
git tag -a v1.4 -m "my version 1.4"

# show a tag
git show v1.4

# creating tags later
git log --pretty=oneline
git tag -a v1.2 <UUID>
```

## HTTP Status code list
> [HHTP Status Code](https://httpstatuses.com/)

## Developers
> [Gadelha TI](https://github.com/gadelhati)
> [Lucas](https://github.com/lucassmartins)
> [Augusto](https://github.com/augustmat)
> [Diego](https://github.com/diegoferreirapinto)

## Licence
> [MIT License](https://choosealicense.com/licenses/mit/)
```
MIT License

Copyright (c) 2020 Jason Watmore

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
