import sun.awt.image.ImageWatched;

import java.util.*;

public class Folder extends Item implements Cloneable{

    private List<Folder> folders;
    private List<File> files;

    public Folder(String name, Folder parentFolder) {

        this.setName(name);
        this.setDate();
        this.setParentFolder(parentFolder);
        this.setFolders();
        this.setFiles();
    }
    public Folder(String name) {

        this.setName(name);
        this.setDate();
        this.setParentFolder(null);
        this.setFolders();
        this.setFiles();
    }

    public void copyFile(File f, Folder destination) throws NameAlreadyUsedException{
        File fileCopy;

        if(destination.containsFile(f)){
            throw new NameAlreadyUsedException("File name already exists within the folder.");
        }
        else{
            fileCopy = new File(f.getName(), f.getDateCreated(), destination, f.getSize(), f.getType());
        }

        destination.addFile(fileCopy);
    }

    public void moveFile(File f, Folder destination) throws NameAlreadyUsedException{

        if(destination.containsFile(f)){
            throw new NameAlreadyUsedException("File name already exists within the folder.");

        }
        else{
            this.files.remove(f);
            destination.addFile(f);
            f.setParentFolder(destination);
        }

    }

    public void addFile(File f) throws NameAlreadyUsedException{

        for(File currentFile : files){

            if(currentFile.getName().equalsIgnoreCase(f.getName()) && currentFile.getType().equalsIgnoreCase(f.getType())){
                throw new NameAlreadyUsedException("File name already exists within the folder.");
            }

        }
        this.getFiles().add(f);
        f.setParentFolder(this);

    }

    public void deleteFile(File f) throws NameNotFoundException{

        boolean foundFile = false;

        for(File currentFile : this.getFiles()){

            if(currentFile.getName().equalsIgnoreCase(f.getName()) && currentFile.getType().equalsIgnoreCase(f.getType())){
                foundFile = true;
                break;
            }
        }

        if(foundFile){
            this.getFiles().remove(f);
            f.setParentFolder(null);
        }
        else{
            throw new NameNotFoundException();
        }

    }

    public void addFolder(Folder f) throws NameAlreadyUsedException{

        for(Folder currentFolder : this.getFolders()) {
            String currentFolderName = currentFolder.getName();
            if(currentFolderName.equalsIgnoreCase(f.getName())) {
                throw new NameAlreadyUsedException("Folder name already exists in the folder.");

            }
        }

        this.getFolders().add(f);
        f.setParentFolder(this);

    }

    public void deleteFolder(Folder f) throws NameNotFoundException {

        boolean foundFolder = false;

        for(Folder currentFolder : this.getFolders()) {

            String currentFolderName = currentFolder.getName();
            if(currentFolderName.equalsIgnoreCase(f.getName())){
                foundFolder = true;
                break;
            }
        }
        if(foundFolder) {

            this.getFolders().remove(f);
            f.setParentFolder(null);
        }
        else {
            throw new NameNotFoundException("Folder does not exist in the current folder.");
        }
    }

    public void copyFolder(Folder f, Folder destination) throws SubFolderException, CloneNotSupportedException, NameAlreadyUsedException{

        if(f.containsSubFolder(destination)){
            throw new SubFolderException("The destination folder is a subfolder of the source folder.");
        }
        else{

            Folder copiedFolder = (Folder) f.clone();
            destination.addFolder(copiedFolder);
        }

    }

    public void moveFolder(Folder f, Folder destination) throws SubFolderException, CloneNotSupportedException, NameAlreadyUsedException, NameNotFoundException{

        if(f.containsSubFolder(destination)){
            throw new SubFolderException("The destination folder is a subfolder of the source folder.");
        }
        else{
            Folder parentFolder = f.getParentFolder();
            destination.addFolder(f);
            parentFolder.deleteFolder(f);

        }

    }

    @Override
    public Object clone() throws CloneNotSupportedException{

        Folder cloned = new Folder(this.getName());
        cloned.setParentFolder(this.getParentFolder());

        LinkedList<Folder> oldStack = new LinkedList<>();
        LinkedList<Folder> newStack = new LinkedList<>();

        oldStack.add(this);
        newStack.add(cloned);

        while(!oldStack.isEmpty()){

            Folder oldFolder = oldStack.pollLast();
            Folder newFolder = newStack.pollLast();

            for(File currentFile : oldFolder.getFiles()){

                try{
                    newFolder.addFile(new File(currentFile.getName(), currentFile.getSize(), currentFile.getType()));
                }
                catch(NameAlreadyUsedException e){
                    System.out.println(e.getMessage());
                }
            }

            for(Folder currentFolder : oldFolder.getFolders()){

                Folder addedFolder = new Folder(currentFolder.getName());

                try{
                    newFolder.addFolder(addedFolder);
                }
                catch(NameAlreadyUsedException e){
                    System.out.println(e.getMessage());
                }

                oldStack.add(currentFolder);
                newStack.add(addedFolder);
            }

        }

        return cloned;
    }

    private void setFolders(){
        folders = new LinkedList<Folder>();
    }

    private void setFiles(){
        files = new LinkedList<File>();
    }
    public List<Folder> getFolders(){
        return folders;
    }

    public List<File> getFiles(){
        return files;
    }

    public boolean containsSubFolder(Folder destination){ //helper method to determine if inserted folder is a possible subfolder

        LinkedList<Folder> stack = new LinkedList<>();
        boolean isSubFolder = false;

        stack.add(this);

        while(!stack.isEmpty() && !isSubFolder){

            Folder folder = stack.pollLast();

            if(folder.getName().equalsIgnoreCase(destination.getName())){
                isSubFolder = true;
            }
            for(Folder subFolder : folder.getFolders()){
                stack.add(subFolder);

            }
        }

        return isSubFolder;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinkedList<Folder> oldStack = new LinkedList<>();
        LinkedList<Folder> newStack = new LinkedList<>();

        Folder checkedFolder = (Folder) o;

        oldStack.add(this);
        newStack.add(checkedFolder);

        boolean isEqual = true;

        while(!oldStack.isEmpty() && isEqual){

            Folder oldFolder = oldStack.pollLast();
            Folder newFolder = newStack.pollLast();

            if((oldFolder.getFolders().size() != newFolder.getFolders().size()) || (oldFolder.getFiles().size() != newFolder.getFiles().size())){
                isEqual = false;
                break;
            }

            Set<File> fileSet = new HashSet<>();
            for(File currentFile : oldFolder.getFiles()){

                fileSet.add(currentFile);
            }

            for(File currentFile : newFolder.getFiles()){

                if(!fileSet.contains(currentFile)){

                    isEqual = false;
                    break;
                }

            }

            Set<String> folderSet = new HashSet<>();

            for(Folder currentFolder : oldFolder.getFolders()){

                folderSet.add(currentFolder.getName());
                oldStack.add(currentFolder);
            }

            for(Folder currentFolder : newFolder.getFolders()){

                if(!folderSet.contains(currentFolder.getName())){
                    isEqual = false;
                    break;
                }
                newStack.add(currentFolder);
            }

        }

        return isEqual;
    }

    @Override
    public int hashCode() {
        return Objects.hash(folders, files);
    }

    public boolean containsFile(File f){

        Set<File> fileMap = new HashSet<File>();

        for(File currentFile : this.getFiles()){

                fileMap.add(currentFile);

        }

        return fileMap.contains(f);
    }

    public boolean containsFolder(Folder f){ //checks only if individual folder contains queried folder
        Set<String> fileMap = new HashSet<>();

        for(Folder currentFolder : this.getFolders()){

            fileMap.add(currentFolder.getName());

        }

        return fileMap.contains(f.getName());
    }

    public String toString(){
        String parentFolder;
        if(this.getParentFolder() == null){
            parentFolder = "None";
        }
        else{
            parentFolder = this.getParentFolder().getName();
        }
        return "Name: " + this.getName() + "\nDate created: " + this.getDateCreated() + "\nParent Folder: " + parentFolder + "\nFolders: " + this.folderToString() + "\nFiles: " + this.fileToString();
    }

    private String fileToString(){
        if(this.files.isEmpty()){
            return "None";
        }

        String output = "| ";

        for(File currentFile : files){
            output = output + currentFile.getName() + "." + currentFile.getType() + " |";
        }

        return output;
    }

    private String folderToString(){
        if(this.folders.isEmpty()){
            return "None";
        }

        String output = "| ";

        for(Folder currentFolder : folders){
            output = output + currentFolder.getName() + " | ";
        }

        return output;
    }
}
