import java.util.Date;

public abstract class Item {

    private String name;
    private Date dateCreated;
    private Folder parentFolder;

    void setName(String newName){
        this.name = newName;
    }

    void setDate(){
        this.dateCreated = new Date();
    }

    void setDate(Date newDateCreated){
        this.dateCreated = newDateCreated;
    }

    void setParentFolder(Folder newParentFolder){
        this.parentFolder = newParentFolder;
    }

    String getName(){
        return name;
    }

    Date getDateCreated(){
        return dateCreated;
    }

    Folder getParentFolder(){
        return parentFolder;
    }
}
