package server.connection;

public class DBConfig {
    private String url;
    private String dbName;
    private String dbUser;
    private String password;

    public DBConfig() {
        this.url = "jdbc:mysql://localhost:3306/";
        this.dbName = "todolist";
        this.dbUser = "root";
        this.password = "P@ssw0rd";
    }

    public String getUrl() {
        return url;
    }

    public String getDbName() {
        return dbName;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getPassword() {
        return password;
    }
}
