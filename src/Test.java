public class Test {

    public static void main(String[] args) throws NameNotFoundException {

        Folder music = new Folder("Music", null);
        Folder rock = new Folder("Rock");
        Folder soul = new Folder("Soul");
        Folder theSmiths = new Folder("The Smiths");


        System.out.println(music);
        System.out.println();

        System.out.println("Add Rock.");
        try {
            music.addFolder(rock);
        } catch (NameAlreadyUsedException e) {
            e.getMessage();
        }

        System.out.println(music);

        System.out.println();

        System.out.println("Add Soul.");

        try {
            music.addFolder(soul);
        } catch (NameAlreadyUsedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();
        System.out.println(music);
        System.out.println();
        System.out.println(rock);
        System.out.println();
        System.out.println(soul);

        System.out.println("Add the Smiths to Rock");
        System.out.println();
        try {
            rock.addFolder(theSmiths);
        } catch (NameAlreadyUsedException e) {
            e.printStackTrace();
        }
        System.out.println(rock);

        try {
            rock.addFolder(theSmiths);
        } catch (NameAlreadyUsedException e) {
            System.out.println(e.getMessage());
        }

        System.out.println();
        System.out.println("Add songs as files.");

        File tcm = new File("This Charming Man", 1564599879, "mp3");

        System.out.println();
        System.out.println(tcm);
        System.out.println();

        try{
            theSmiths.addFile(tcm);
        }
        catch(NameAlreadyUsedException e){
            System.out.println(e.getMessage());
        }

        System.out.println(theSmiths);
        System.out.println();
        System.out.println(tcm);
        System.out.println();
        System.out.println();
    }


}
