package proggeto;

public class Administrator extends User {
    public Administrator(String nameUser, String passwordUser, String emailUser) {
        super(nameUser, passwordUser, emailUser );
    }

    @Override
    public void setNameUser(String nameUser) {
        super.setNameUser("ADMIN");
    }
    @Override
    public String getNameUser() {
        return nameUser;
    }

    @Override
    public void setEmailUser(String emailUser) {
        super.setEmailUser("admin@example.com");
    }

    @Override
    public void setPasswordUser(String passwordUser) {
        super.setPasswordUser("123456");
    }
}
