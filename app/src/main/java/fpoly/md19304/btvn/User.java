package fpoly.md19304.btvn;

public class User {
    private String id;
    private String name;
    private String city;

    public User() {
        // Default constructor required for Firestore deserialization
    }

    public User(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
