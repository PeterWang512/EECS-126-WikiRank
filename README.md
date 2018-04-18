# WikiRank

## Instructions
1. Download [MySQL](https://dev.mysql.com/downloads/mysql/) and [JDBC]
(https://dev.mysql.com/downloads/connector/j/3.1.html). Alternatively, you 
can download SQL using HomeBrew on OSX by running "brew install mysql". 

2. Set the username and password as "root" and "hello". If you installed 
using HomeBrew, the default password will be "". Just press enter whenever 
asked for a password. Currently, in DB.java, the password we use to access
the database is "". If you change the password to something else, make sure 
to change the password variable in DB.java.

3. Run the SQL server and create a database named "wikirank" (./mysqladmin create wikirank -u root -p)
4. Download [simplewiki dumps](https://dumps.wikimedia.org/simplewiki/20180401/) (...pagelinks.sql.gz and ...page.sql.gz)
5. Import the tables to the database (mac: zcat < path_to_file/filename.sql.gz | ./mysql wikirank -u root -p)
6. Execute the SQL instructions 
"delete from page where page_namespace > 0;"
"delete from pagelinks where pl_from_namespace > 0;" 
"delete from pagelinks where pl_namespace > 0;"
7. Run WikiRank.java
8. You can choose to change the hyperparameters for how many PageRank results
you want to print, and the size of each cluster at the bottom of WikiRank.java

[JDBC setup tutorial](https://www.tutorialspoint.com/jdbc/jdbc-environment-setup.htm)
