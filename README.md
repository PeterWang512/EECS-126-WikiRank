# WikiRank

## Instructions
1. Download [MySQL](https://dev.mysql.com/downloads/mysql/) and [JDBC](https://dev.mysql.com/downloads/connector/j/3.1.html) 
2. Set the username and password as "root" and "hello"
3. Run the SQL server and create a database named "wikirank" (./mysqladmin create wikirank -u root -p)
4. Download [simplewiki dumps](https://dumps.wikimedia.org/simplewiki/20180401/) (...pagelinks.sql.gz and ...page.sql.gz)
5. Import the tables to the database (mac: zcat < path_to_file/filename.sql.gz | ./mysql wikirank -u root -p)
6. Run WikiRank.java

[JDBC setup tutorial](https://www.tutorialspoint.com/jdbc/jdbc-environment-setup.htm)
