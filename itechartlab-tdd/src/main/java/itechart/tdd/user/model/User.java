package itechart.tdd.user.model;

import java.util.Objects;

public class User {

    private Long id;
    private String firstName;
    private String lastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object object) {

        if (object == null || !(object instanceof User)) {
            return false;
        }
        User someUser = (User) object;
        return Objects.equals(this.id, someUser.getId())
                && Objects.equals(this.firstName, someUser.getFirstName())
                && Objects.equals(this.lastName, someUser.getLastName());
    }

}
