import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test2 {

    public static void main(String[] args) throws CloneNotSupportedException{

        Folder doc = new Folder("Documents");
        Folder mov = new Folder("Movies");
        File xMen = new File("X-Men", 102425888, "mkv");
        File wordDoc = new File("Chance", 125, "word");
        File secondDoc = new File("Chance", 125, "word");

        System.out.println("Documents");
        System.out.println();
        System.out.println(doc);
        System.out.println();
        System.out.println("Movies");
        System.out.println();
        System.out.println(mov);
        System.out.println();
        System.out.println("X-Men");
        System.out.println(xMen);
        System.out.println();
        System.out.println("Add X-Men to Movies");
        System.out.println();
        try{
            mov.addFile(xMen);
        }
        catch(NameAlreadyUsedException e){
            System.out.println(e.getMessage());
        }
        System.out.println(mov);
        System.out.println();
        System.out.println("Add Movies to Documents");
        System.out.println();
        try{
            doc.addFolder(mov);
            doc.addFile(wordDoc);
        }
        catch(NameAlreadyUsedException e){
            System.out.println(e.getMessage());
        }
        System.out.println(doc);

       Folder dean = new Folder("Dean");

       try{
           doc.moveFolder(mov, dean);
       }
       catch(SubFolderException e){
           System.out.println(e.getMessage());
       }
       catch(NameAlreadyUsedException e){
           System.out.println(e.getMessage());
       }
       catch(NameNotFoundException e){
           System.out.println(e.getMessage());
       }
       try{
           doc.moveFile(wordDoc, dean);
       }
       catch(NameAlreadyUsedException e){
           System.out.println(e.getMessage());
       }

       System.out.println();
       System.out.println(dean);
       System.out.println();

       System.out.println(doc.equals(dean));

        System.out.println("Doc Folders: " + doc.getFolders().size() + "," + "Files: " + doc.getFiles().size());
        System.out.println("Dean Folders: " + dean.getFolders().size() + "," + "Files: " + dean.getFiles().size());

       for(Folder currentFolder : dean.getFolders()){
           System.out.println(currentFolder);
       }
       try{
           dean.deleteFolder(mov);
       }
       catch(NameNotFoundException e){
           System.out.println(e.getMessage());
       }
       System.out.println(dean);
       System.out.println(doc);

       System.out.println(doc.equals(dean));



    }
}
