import java.util.Date;
import java.util.Objects;

public class File extends Item implements Cloneable{

    private long size;
    private String type;

    public File(String fileName, Folder parentFolder, long fileSize, String fileType){

        this.setName(fileName);
        this.setDate();
        this.setParentFolder(parentFolder);
        this.size = fileSize;
        this.type = fileType;

    }

    public File(String fileName,long fileSize, String fileType){

        this.setName(fileName);
        this.setDate();
        this.setParentFolder(null);
        this.size = fileSize;
        this.type = fileType;

    }

    public File(String fileName, Date dateCreated, Folder parentFolder, long fileSize, String fileType){

        this.setName(fileName);
        this.setDate(dateCreated);
        this.setParentFolder(parentFolder);
        this.size = fileSize;
        this.type = fileType;

    }

    public long getSize() {
        return size;
    }

    public String getType(){
        return type;
    }

    public boolean equals(Object f){

        if (this == f) return true;
        if (f == null || getClass() != f.getClass()) return false;

        String currentName = this.getName();
        String currentType = this.getType();
        long currentSize = this.getSize();

        return currentName.equalsIgnoreCase(((File)f).getName()) && currentType.equalsIgnoreCase(((File)f).getType()) && currentSize == ((File)f).getSize();

    }

    @Override
    public int hashCode() {
        return Objects.hash(size, type);
    }

    public String toString(){

        String parentFolder;
        if(this.getParentFolder() == null){
            parentFolder = "None";
        }
        else{
            parentFolder = this.getParentFolder().getName();
        }

        return "Name: " + this.getName() + "\nType: " + this.getType() + "\nSize: " + this.getSize() + " KB" + "\nDate created: " + this.getDateCreated() + "\nParent folder: " + parentFolder;
    }

    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
