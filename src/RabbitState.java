public interface RabbitState {
    void performAction(Rabbit rabbit);
}

class RabbitWalking implements RabbitState{
    @Override
    public void performAction(Rabbit rabbit){
        
        if(rabbit.doWithProbability(0.2)){
            Farm.field.get(rabbit.pos_y).get(rabbit.pos_x).setDestroyed(true);
        }
        rabbit.randomMove();
        if(Farm.field.get(rabbit.pos_y).get(rabbit.pos_x).ifContains(Dog.class)){
            rabbit.setState(new RabbitJustKilled());
        }
        if(Farm.field.get(rabbit.pos_y).get(rabbit.pos_x).getPhase()>=6){
            rabbit.setState(new RabbitEating());
        }
    }
}
class RabbitEating implements RabbitState{
    @Override
    public void performAction(Rabbit rabbit){
        int eatingStage = rabbit.getEatingStage() + ((int)(Math.random() + 0.7));
        if(eatingStage >= 4){
            // System.out.println(Thread.currentThread().getName() + ": I ate carrot :)");
            synchronized (rabbit) {
                rabbit.setState(new RabbitWalking());
                Farm.field.get(rabbit.pos_y).get(rabbit.pos_x).setDestroyed(true);
                rabbit.setEatingStage(0);
            }
            return;
        }
        rabbit.setEatingStage(eatingStage); // zrobienie tego czegoś było czymś uzasadnione(zamiast updateowac od razu stan jedzenia)
        //Aby nie było że po zjedzeniu wciążż ma stan jedzenia 4, inaczej po 1 posiłku jadłby w 1 turę
    }
}
class RabbitJustKilled implements RabbitState{
    @Override
    public void performAction(Rabbit rabbit){
        // System.out.println(Thread.currentThread().getName() + ": I was killed :(");
        synchronized (rabbit) {
            rabbit.setWhoAttacks(null);
            rabbit.setState(new RabbitHiding());
            Farm.field.get(rabbit.pos_y).get(rabbit.pos_x).delCreature(rabbit);
            // Farm.field.get(rabbit.pos_y).get(rabbit.pos_x).setDestroyed(true);//po co?
        }
    }
}
class RabbitHiding implements RabbitState{ //Or reincarnating, depending on situation //xdd
    @Override
    public void performAction(Rabbit rabbit){
        if(rabbit.doWithProbability(0.3)){
            int pos_x = rabbit.getRandomNum();
            int pos_y = rabbit.getRandomNum();
            if(Farm.field.get(pos_y).get(pos_x).ifContains(Dog.class)){return;}
            synchronized (rabbit) {
                rabbit.setPos(pos_x, pos_y);
                Farm.field.get(pos_y).get(pos_x).addCreature(rabbit);
                if (Farm.field.get(pos_y).get(pos_x).getPhase() >= 6){
                    rabbit.setState(new RabbitEating());
                    return;
                }
                rabbit.setState(new RabbitWalking());
            }
        }
    }
}
