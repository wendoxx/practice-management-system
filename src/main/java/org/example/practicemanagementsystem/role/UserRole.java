package org.example.practicemanagementsystem.role;

public enum UserRole {
    ADMIN("admin"),
    DOCTOR("doctor"),
    USER("user");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
