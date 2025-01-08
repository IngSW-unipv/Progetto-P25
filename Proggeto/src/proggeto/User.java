package proggeto;

public class User {
    String nameUser;
    String passwordUser;
    String emailUser;

    public User(String passwordUser, String nameUser, String emailUser) {
        this.passwordUser = passwordUser;
        this.nameUser = nameUser;
        this.emailUser = emailUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }
}
