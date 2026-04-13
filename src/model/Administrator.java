package model;

public class Administrator {
	private String name;//the unique identifier for the administrator
    private String password;//the password for the administrator's account

    public Administrator() {
        this.name = null;
        this.password = null;
    }
    public Administrator(String name, String password) {//constructor that initializes the administrator's information
        this.name = name;
        this.password = password;
    }
 
    public String getName() {
        return name;
    } 

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}