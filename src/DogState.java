public interface DogState {
    void performAction(Dog dog);
}

class DogWalking implements DogState{
    @Override
    public void performAction(Dog dog){
        dog.randomMove();
        synchronized (dog){
            //on: chyba tak, dobrze że naprawiłes te czesc
            // Taka moja praca
            Rabbit rabbit=dog.findAttacksWho();
            if (rabbit!=null) {
                dog.setState(new Attacking());
                dog.setAttacksWho(rabbit);
                rabbit.setWhoAttacks(dog);
                // System.out.println(Thread.currentThread().getName() +": i see rabbit!!");
            }
        }
    }
}

class Attacking implements DogState{
    @Override
    public synchronized void performAction(Dog dog){
        if(!dog.AttacksAvailableRabbit()){
            dog.setState(new DogWalking());
            dog.getAttacksWho().setWhoAttacks(null); //rabbit is nor longer attacked
            dog.setAttacksWho(null); //dog doesn't attack
            // System.out.println(Thread.currentThread().getName() + ": i lost rabbit?!");
            return;
        }
        dog.moveToRabbit();
        if(dog.kill()){

            dog.setState(new DogWalking());
            dog.getAttacksWho().setState(new RabbitJustKilled());
            dog.getAttacksWho().setWhoAttacks(null); //rabbit is nor longer attacked
            dog.setAttacksWho(null); //dog doesn't attack
            // System.out.println(Thread.currentThread().getName() + ":  i killed!!");
            //farmer doesn't see rabbit
        }
    }
}