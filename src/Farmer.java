import java.util.Vector;
public class Farmer extends Creature {
    final private  int vision;
    private int[] target = {-1, -1};
    Farmer(){
        super() ;
        this.vision = 4;
        
    }

    @Override
    public void doActions(){
        //System.out.println("farmer");
        this.target = getDestroyedTile();
        inform_dogs();
        if(Farm.field.get(pos_y).get(pos_x).getDestroyed()){
            if(doWithProbability(0.5)){
                Farm.field.get(pos_y).get(pos_x).setDestroyed(false);
                //ja bym to usunal bo tak po pewnym czasi nie bedzie nie zasadzonych pol
                // A się zgadzam
                Farm.field.get(pos_y).get(pos_x).setSeeded(false);
                Farm.field.get(pos_y).get(pos_x).setPhase(0);
                target[0] = -1;
            }
            return;
        }
        if(this.target[0]!=-1 && doWithProbability(0.9)){moveToTarget(target);}
        else{randomMove();}
        Farm.field.get(pos_y).get(pos_x).setSeeded(true);// xdd farmer może się rozkojarzyc i zpomniec gzie idze
        //dokładnie; plus to małe zabezpieczenie aby farmerzy nie byli ciągle ze sobą scaleni
        // Bo tak się niestety czasem działo
    }

    @Override
    public String getSign(){
        return "\uD83D\uDC69\u200D\uD83C\uDF3E";
    }

    public synchronized Vector<Rabbit> find_rabbits(){
        Vector<Rabbit> ans = new Vector<Rabbit>();
        for(Rabbit rabbit: Farm.rabbits){
            if(this.getDistance(this, rabbit)<=this.vision && rabbit.isRabbitAvailable()){ ans.add(rabbit);}
        }
        return ans;
    }
    public synchronized void inform_dogs(){
        Vector<Rabbit> Rabbitinfo = find_rabbits();
        for(Rabbit rabbit : Rabbitinfo){
            int min_dist=5*Farm.field.size(), curr_dist; //jak sie oktresla min dist?
            //Manhattan distance (ta duża wartość jest po to aby zawsze ją dało się zmniejszyć)
            Dog chosen_dog=null;
            for(Dog dog : Farm.dogs){
                if(dog.attacksWho!=null){continue;}
                curr_dist = getDistance(rabbit, dog);
                if(min_dist>curr_dist){
                    chosen_dog = dog;
                    min_dist = curr_dist;
                }
            }
            if(chosen_dog==null){continue;}
            chosen_dog.setState(new Attacking());
            chosen_dog.setAttacksWho(rabbit);
            rabbit.setWhoAttacks(chosen_dog);
        }
    }
    private int[] getDestroyedTile(){
        int min_dist=5*Farm.field.size(), curr_dist;
        int[] min_pos = {-1,0};
        for(int i=-vision; i<vision+1; i++){
            for(int j=-vision+Math.abs(i); j<vision-Math.abs(i)+1; j++){
                curr_dist = Math.abs(i)+Math.abs(j);
                if(!inBounds(pos_x+j, pos_y+i)){continue;}
                if(Farm.field.get(pos_y+i).get(pos_x+j).getDestroyed() && curr_dist<min_dist){
                    min_dist = curr_dist;
                    min_pos[0] = pos_x+j; min_pos[1] = pos_y+i;
                }
            }
        }
        return min_pos;
    }//tej funkcji szczegółowo nie analizowałem
    //To w praktyce kalka z 1 projektu (sprawdzanie diamentu wokół farmera)
    // Szukanie zniszczonego pola i wybranie do naprawy tego najbliższego
}
