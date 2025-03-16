import java.util.Vector;

public class Tile {
    private int grow_phase; // negative is destroyed and above 6 is ready
    private Vector<Creature> creatures;
    private boolean seeded;
    private boolean destroyed;
    //private boolean justDestroyed; //if from last update rabbit destroyed a tile

    Tile(){
        this.grow_phase = 0;
        this.creatures = new Vector<>();
        seeded = false;
    }



    public void update(){ //main function to update tile(source)
        if (seeded) {
            Grow();
        }
//        if(justDestroyed){}//not necessry i think
        //eg to add here: if beeing repaired

    }
    public void Grow(){
        if (grow_phase >= 0 && grow_phase <=6) {
            grow_phase += (int) (Math.random() + 0.5);
        } else  if(grow_phase < 0){ // destroyed

        }else  if(grow_phase > 6) { //ready
            return;
        }
    }


    public boolean ifContains(Class<?> type){
        synchronized(this.creatures){        
            for (Creature creature : this.creatures) {
            if (type.isInstance(creature)) {
                return true;
            }
        }
        }
        return false;
    }

    public int getPhase(){
        return this.grow_phase;
    }

    public void setPhase(int x){
        grow_phase = x;
    }

    public boolean getSeeded(){
        return seeded;
    }
    public void setSeeded(boolean x){
        seeded = x;
    }

    public void setDestroyed(boolean x){
        destroyed = x;
    }

    public boolean getDestroyed(){
        return destroyed;
    }

    public Vector<Creature> getCreatures(){
        return this.creatures;
    }

    public void addCreature(Creature creature){
        this.creatures.add(creature);
    }

    public void delCreature(Creature creature){
        this.creatures.remove(creature);
    }



}
