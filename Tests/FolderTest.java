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

}