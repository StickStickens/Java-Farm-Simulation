import java.io.IOException;
import java.util.Vector;

public class Farm {
    private int N;
    public static Vector<Vector<Tile>> field;

    public static Vector<Thread> threads;
    public static Vector<Rabbit> rabbits;
    public static Vector<Dog> dogs;
    public static Vector<Farmer> farmers;


    Farm(int N, int rab_num, int dog_num, int far_num){
        this.N=N;
        this.rabbits = new Vector<>();
        this.farmers = new Vector<>();
        this.dogs = new Vector<>();
        this.threads = new Vector<>();


        field = new Vector<>(N);
        for (int i = 0; i < N; i++){
            Vector<Tile> row = new Vector<>(N);
            for (int j = 0; j < N; j++) {
                row.add(new Tile());
            }
            field.add(row);
        }

        for(int i=0; i<rab_num; i++){
            Rabbit rabbit = new Rabbit();
            this.rabbits.add(rabbit);
            Thread thread = new Thread(rabbit);
            this.threads.add(thread);
        }

        for(int i=0; i<dog_num; i++){
            Dog dog = new Dog();
            this.dogs.add(dog);
            Thread thread = new Thread(dog);
            this.threads.add(thread);
        }

        for(int i=0; i<far_num; i++){
            Farmer farmer = new Farmer();
            this.farmers.add(farmer);
            Thread thread = new Thread(farmer);
            this.threads.add(thread);
        }

//        for(Thread thread : threads){
//            thread.start();
//        }
        simulation();
    }
    //literally copied from stackOverflow, but it works
    // cleans terminal
    private static void cls(){
        try {

        if (System.getProperty("os.name").contains("Windows"))
            new ProcessBuilder("cmd", "/c", 
                    "cls").inheritIO().start().waitFor();
        else
            Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {}
    }

    public void simulation() {
        int counter=-1;
        for(Thread thread : threads){
            // System.out.println("hejotred");
            thread.start();
        }
        while (true) { //main loop
            counter++;
            // System.out.println("hejo loop");
            for(int i=0; i<N; i++) {
                Vector<Tile> row = field.get(i);
                for (int j = 0; j < N; j++) {
                    Tile tile = row.get(j);
                    tile.update();//może wygodniej każdego tile'a na osobnym threadzie robic?
                }
            }
            printTestState(counter);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }



//    public void getCurrentFiled(){
//
//    }

    public synchronized void printState(){

        for(int i=0; i<N; i++){
            String line="";
            Vector<Tile> row = field.get(i);
            for(int j=0; j<N; j++){
                Tile tile = row.get(j);
                String obj=" ";
                if(tile.getDestroyed()){
                    obj = " *";
                }else {
                    if (tile.getSeeded()) {
                        if (tile.getPhase() < 0) {
                            obj = " -";
                        } else if (tile.getPhase() < 6) {
                            obj = " ,";
                        } else {
                            obj = " |";
                        }
                    } else {
                        obj = " .";
                    }
                }
                if(! tile.getCreatures().isEmpty()){
                    Creature creature = tile.getCreatures().getFirst();
                    obj = creature.getSign();

                }


                line+= obj;
            }
            System.out.println(line);
        }
        //| ready crop
        //0- not ready crop
        //-- destroyed crop

        //doesn't handle multiple creatures at a tile/ don't allow multiple creatures at tile?
    }

    public synchronized void printTestState(int counter){
        // cls();
        System.out.println("Iteration: " + counter);
        for(int i=0; i<N; i++){
            String line="";
            Vector<Tile> row = field.get(i);
            for(int j=0; j<N; j++){
                Tile tile = row.get(j);
                String obj;
                if(tile.getDestroyed()){
                    obj = " * ";
                }else {
                    if (tile.getSeeded()) {
                        if (tile.getPhase() < 0) {
                            obj = " - ";
                        } else if (tile.getPhase() < 6) {
                            obj = " , ";
                        } else {
                            obj = " | ";
                        }
                    } else {
                        obj = " . ";
                    }
                }
                if(! tile.getCreatures().isEmpty()){
                    obj = "";
                    Vector<Creature> tile_info = tile.getCreatures();
                    synchronized (tile_info) {
                        
                    for(Creature creature : tile_info){
                        if(creature.getClass().getSimpleName().equals("Farmer")){
                            obj += "F";
                        }
                        if(creature.getClass().getSimpleName().equals("Rabbit")){
                            obj += "R";
                        }
                        if(creature.getClass().getSimpleName().equals("Dog")){
                            obj += "D";
                        }
                    }
                    }
                }
                int alpha = obj.length();
                for (int t = 0; t < 3-alpha; t++) {
                    obj+=" ";
                }
                line+=" "+ obj;
            }
            System.out.println(line);
        }
    }
}
