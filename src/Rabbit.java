
public class Rabbit extends Creature {
    //RabbitState state;

    private RabbitState state;
    private int eatingStage;
    private Dog whoAttacks; //so only one dog can attack



    Rabbit(){
        super() ;
        this.setState(new RabbitHiding());
        eatingStage = 0; //4 => successfully ate
    }



    @Override
    public synchronized void doActions(){
        //System.out.println("rabbit");
        state.performAction(this);

    }
    @Override
    public String getSign(){
        return "\uD83D\uDC07";
    }



    public void updateTile(){ //chcę z actions wyrzucić tu część żeby nie było chaosu

    }

    public void setEatingStage(int x){
        this.eatingStage = x;
    }

    public int getEatingStage(){
        return this.eatingStage;
    }

    public Dog getWhoAttacks() {
        return whoAttacks;
    }

    public void setWhoAttacks(Dog whoAttacks){
         this.whoAttacks= whoAttacks;
    }

    public void setState(RabbitState state){
        this.state = state;
    }

    public RabbitState getState(){
        return this.state;
    }

    public boolean isInState(RabbitState state){
        return this.state==state;
    }
    public boolean isRabbitAvailable(){
        return (this.state instanceof RabbitEating) || (this.state instanceof  RabbitWalking); 
    }// jak działaja takie instancy?

}
