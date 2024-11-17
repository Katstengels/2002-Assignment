package src;

/**
 * The {@code Pharmacist} class represents a pharmacist in the system, inheriting properties
 * and behaviors from the {@code Staff} class.
 * <p>
 * This class provides specific implementation for a pharmacist, including details
 * such as user ID, name, role, gender, age, and password.
 * </p>
 */
public class Pharmacist extends Staff {

    /**
     * Constructs a new {@code Pharmacist} object with the specified details.
     *
     * @param userID   the unique identifier of the pharmacist
     * @param name     the name of the pharmacist
     * @param role     the role of the pharmacist in the system
     * @param gender   the gender of the pharmacist
     * @param age      the age of the pharmacist
     * @param password the password of the pharmacist for authentication
     */
    public Pharmacist(String userID, String name, String role, String gender, int age, String password) {
        super(userID, name, role, gender, age, password);
    }
}
