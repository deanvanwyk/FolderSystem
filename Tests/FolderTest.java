import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class FolderTest {

    private Folder dean = new Folder("Dean");
    private Folder desktop = new Folder("Desktop");
    private Folder documents = new Folder("Documents");
    private Folder pictures = new Folder("Pictures");
    private Folder games = new Folder("Games");
    private File cv = new File("My CV", 12, "word");
    private File vacation = new File("Vacation", 68, "jpeg");
    private File dota = new File("Dota 2", 219, "url");
    private File trailer = new File("Avengers Endgame Trailer", 15400, "mp4");

    @Before
    public void setUpFolderSystem() throws Exception{
        dean.addFolder(desktop);
        dean.addFolder(documents);
        dean.addFolder(pictures);
        dean.addFile(trailer);

        desktop.addFolder(games);
        documents.addFile(cv);
        pictures.addFile(vacation);

        games.addFile(dota);
    }

    @Test
    public void testFileName(){

        assertEquals("My CV", cv.getName());
    }

    @Test
    public void testFileSize(){
        assertEquals(12, cv.getSize());
    }

    @Test
    public void testFileType(){
        assertEquals("word", cv.getType());
    }

    @Test
    public void testFileDate(){
        assertEquals(cv.getDateCreated(), cv.getDateCreated());
    }

    @Test
    public void testFileNoParentFolder(){

        assertEquals(null, dean.getParentFolder());

    }

    @Test
    public void testFolderName(){
        assertEquals("Dean", dean.getName());
    }

    @Test
    public void testAmountFolderFiles(){
        assertEquals(1, dean.getFiles().size());
    }

    @Test
    public void testAmountFolderFolders(){
        assertEquals(3, dean.getFolders().size());
    }

    @Test
    public void addFile() throws NameAlreadyUsedException{

        File app = new File("Virus app", 10101011, "exe");

        dean.addFile(app);
        assertTrue(dean.containsFile(app));
    }

    @Test
    public void deleteFile() throws NameNotFoundException {

        dean.deleteFile(trailer);
        assertFalse(dean.containsFile(cv));
    }

    @Test
    public void copyFileOldFolder() throws NameAlreadyUsedException{
        dean.copyFile(trailer, desktop);
        assertTrue(dean.containsFile(trailer));
    }

    @Test
    public void copyFileNewFolder() throws NameAlreadyUsedException{
        dean.copyFile(trailer, desktop);
        assertTrue(desktop.containsFile(trailer));
    }

    @Test
    public void moveFileOldFolder() throws NameNotFoundException, NameAlreadyUsedException{
        dean.moveFile(trailer, desktop);
        assertFalse(dean.containsFile(trailer));
    }

    @Test
    public void moveFileNewFolder() throws NameNotFoundException, NameAlreadyUsedException{
        dean.moveFile(trailer, desktop);
        assertTrue(desktop.containsFile(trailer));
    }

    @Test
    public void addFolder() throws NameAlreadyUsedException{
        Folder downloads = new Folder("Downloads");

        dean.addFolder(downloads);
        assertTrue(dean.containsFolder(downloads));
    }

    @Test
    public void deleteFolder() throws NameNotFoundException{
        dean.deleteFolder(desktop);
        assertFalse(dean.containsFolder(desktop));
    }

    @Test
    public void copyFolderOldFolder() throws NameAlreadyUsedException, CloneNotSupportedException, SubFolderException{

        desktop.copyFolder(games, dean);
        assertTrue(dean.containsFolder(games));
    }

    @Test
    public void copyFolderNewFolder() throws NameAlreadyUsedException, CloneNotSupportedException, SubFolderException{

        desktop.copyFolder(games, dean);
        assertTrue(desktop.containsFolder(games));
    }

    @Test
    public void moveFolderOldFolder() throws NameAlreadyUsedException, CloneNotSupportedException, SubFolderException, NameNotFoundException{

        desktop.moveFolder(games, dean);
        assertFalse(desktop.containsFolder(games));
    }

    @Test
    public void moveFolderNewFolder()throws NameAlreadyUsedException, CloneNotSupportedException, SubFolderException, NameNotFoundException{
        desktop.moveFolder(games, dean);
        assertTrue(dean.containsFolder(games));
    }

    @Test
    public void containsSubFolder(){

        assertTrue(dean.containsSubFolder(games));

    }

    @Test
    public void doesNotContainSubFolder(){
        assertFalse(documents.containsSubFolder(games));
    }

    @Test
    public void cloneFile(){

        File cloned = (File) dota;

        assertEquals(cloned, dota);
    }

   //Exception Testing

    @Test(expected = SubFolderException.class)
    public void copyIntoSubfolderException() throws SubFolderException, NameAlreadyUsedException, CloneNotSupportedException{
        dean.copyFolder(desktop, games);
    }

    @Test(expected = SubFolderException.class)
    public void moveIntoSubFolderException() throws SubFolderException, CloneNotSupportedException, NameAlreadyUsedException, NameNotFoundException {
        dean.moveFolder(desktop,games);
    }

    @Test(expected = NameNotFoundException.class)
    public void deleteNonExistentFile() throws NameNotFoundException{
        documents.deleteFile(vacation);
    }

    @Test(expected = NameAlreadyUsedException.class)
    public void addFileNameAlreadyUsedSameType() throws NameAlreadyUsedException{

        File dota2 = new File("Dota 2", 225, "url");
        games.addFile(dota2);
    }

    @Test
    public void addFileNameAlreadyUsedDifferentType() throws NameAlreadyUsedException {

        File dota2 = new File("Dota 2", 225, "ps4");
        games.addFile(dota2);
        games.containsFile(dota2);
    }

    @Test
    public void deleteFileNameAlreadyUsedDifferentTypeNewFile() throws NameAlreadyUsedException, NameNotFoundException {

        File dota2 = new File("Dota 2", 225, "ps4");
        games.addFile(dota2);
        games.deleteFile(dota);
        assertTrue(games.containsFile(dota2));
    }

    @Test (expected = NameAlreadyUsedException.class)
    public void copyFileToLocationWithSameFileNameSameType() throws NameAlreadyUsedException {
        File secondTrailer = new File("Avengers Endgame Trailer", 15400, "mp4");

        pictures.addFile(secondTrailer);
        pictures.copyFile(secondTrailer, dean);
    }

    @Test
    public void copyFileToLocationWithSameFileNameDifferentType() throws NameAlreadyUsedException {
        File secondTrailer = new File("Avengers Endgame Trailer", 15400, "mkv");

        pictures.addFile(secondTrailer);
        pictures.copyFile(secondTrailer, dean);
    }

    @Test
    public void deleteFileNameAlreadyUsedDifferentTypeOldFile() throws NameAlreadyUsedException, NameNotFoundException {

        File dota2 = new File("Dota 2", 225, "ps4");
        games.addFile(dota2);
        games.deleteFile(dota);
        assertFalse(games.containsFile(dota));
    }

    @Test(expected = NameNotFoundException.class)
    public void deleteNonExistentFolder() throws NameNotFoundException {

        pictures.deleteFolder(documents);
    }

    @Test(expected = NameAlreadyUsedException.class)
    public  void addFolderWithExistentName() throws NameAlreadyUsedException {
        Folder extraFolder = new Folder("Documents");
        dean.addFolder(extraFolder);
    }

    @Test(expected = NameAlreadyUsedException.class)
    public void copyFolderToFolderWithExistentName() throws NameAlreadyUsedException, SubFolderException, CloneNotSupportedException {
        Folder documentGames = new Folder("Games");
        documents.addFolder(documentGames);
        desktop.copyFolder(games, documents);
    }

    @Test(expected = NameAlreadyUsedException.class)
    public void moveFolderToFolderWithExistentName() throws NameAlreadyUsedException, SubFolderException, CloneNotSupportedException, NameNotFoundException {
        Folder documentGames = new Folder("Games");
        documents.addFolder(documentGames);
        desktop.moveFolder(games, documents);
    }

    //toStringMethods
    @Test
    public void fileToString(){

        assertEquals("Name: Vacation\nType: jpeg\nSize: 68 KB" + "\nDate created: " + vacation.getDateCreated() + "\nParent folder: Pictures" ,vacation.toString());
    }
    @Test
    public void folderToString(){
        assertEquals("Name: Dean\nDate created: " + dean.getDateCreated() + "\nParent Folder: None\nFolders: | Desktop | Documents | Pictures | \nFiles: | Avengers Endgame Trailer.mp4 |", dean.toString());
    }

}