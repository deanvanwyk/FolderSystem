public class NameNotFoundException extends Exception {

    public NameNotFoundException(){
        super("File does not exist in the current folder.");
    }
    public NameNotFoundException(String message){
        super(message);
    }
}
