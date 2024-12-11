package pt.ipleiria.estg.dei.book.modelo;

public class User {
    private String token;

    public User(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}
