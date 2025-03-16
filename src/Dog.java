
public class Dog extends Creature{
    DogState state;
    Rabbit attacksWho;

    Dog(){
        super();
        state = new DogWalking();
    }


    @Override
    public void doActions(){
        state.performAction(this);
    }

    @Override
    public String getSign(){
        return "\uD83D\uDC36";
    }



    public Rabbit findAttacksWho(){
        int min_dist = 5*Farm.field.size();
        Rabbit chosen_rabbit=null;
        for(Rabbit rabbit : Farm.rabbits) {
            if (rabbit.isRabbitAvailable() && rabbit.getWhoAttacks()==null) {
                if(getDistance(this, rabbit)<min_dist){
                    chosen_rabbit = rabbit;
                    min_dist = getDistance(this, rabbit);
                }
            }
        }
        // System.out.println(Thread.currentThread().getName() + "synchro failed here(but i made a temp fix(or more probably constant))");
        if(min_dist >= 5){return null;}
        return chosen_rabbit;
    }

    public synchronized boolean AttacksAvailableRabbit(){
        for(Rabbit rabbit : Farm.rabbits){
            if( rabbit.isRabbitAvailable() && (getDistance(this, rabbit) < 5 || rabbit.getWhoAttacks()==this)){
                return true;
            }
        }
        return false;
    }


    public  synchronized void  moveToRabbit(){
        moveToTarget(attacksWho.getPos());
    }
    public synchronized boolean kill(){
        return this.pos_y == attacksWho.pos_y && this.pos_x == attacksWho.pos_x;
    }



    public void setState(DogState state){
        this.state = state;
    }

    public void setAttacksWho(Rabbit attacksWho) {
        this.attacksWho = attacksWho;
    }

    public Rabbit getAttacksWho() {
        return attacksWho;
    }
}
