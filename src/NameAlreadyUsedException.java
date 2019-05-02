public class NameAlreadyUsedException extends Exception {

    public NameAlreadyUsedException(){
        super("File name already exists within the folder.");
    }

    public NameAlreadyUsedException(String message){
        super(message);
    }
}
