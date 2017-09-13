package sk.codexa.darwin.securityservice.model;

public enum Role {

    ADMIN("Admin"), TENANT_ADMIN("Tenant Admin"), TENANT("Tenant"), GUEST("Guest");

    private String description;

    Role(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
