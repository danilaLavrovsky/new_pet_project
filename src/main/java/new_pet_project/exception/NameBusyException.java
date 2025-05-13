package new_pet_project.exception;

public class NameBusyException extends Exception {
    public NameBusyException(String errorMessage) {
        super(errorMessage);
    }
}
