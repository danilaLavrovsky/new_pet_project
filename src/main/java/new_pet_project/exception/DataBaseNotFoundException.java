package new_pet_project.exception;

public class DataBaseNotFoundException extends Exception {
    public DataBaseNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
