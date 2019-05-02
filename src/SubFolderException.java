public class SubFolderException extends Exception {

    public SubFolderException(){

        super("The destination folder is a subfolder of the source folder.");
    }

    public SubFolderException(String message){

        super(message);
    }

}
