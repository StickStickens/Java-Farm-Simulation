import static java.lang.Math.abs;

public abstract class Creature implements Runnable {
    protected int pos_x; //annoying name. probably we should change to just x (same for pos_y)
    protected int pos_y;

    Creature(){
        this.pos_x=getRandomNum();
        this.pos_y=getRandomNum();
        //this.run();
    }
    public abstract void doActions();
    public abstract String getSign();

    @Override
    public void run(){
        while(true){
            doActions();
            try {
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }


    public synchronized void randomMove(){
        int[][] moves = {{0,1}, {0,-1},{1,0},{-1,0}};
        int move = (int)(Math.random() *4);
        int dx= moves[move][0];
        int dy= moves[move][1];
        // Yes, that looks much better!
        if(inBounds(pos_x + dx, pos_y+ dy)){
            Farm.field.get(pos_y).get(pos_x).delCreature(this);
            this.pos_x += dx;
            this.pos_y += dy;
            Farm.field.get(pos_y).get(pos_x).addCreature(this);
        }

    }

    public  synchronized void  moveToTarget(int[] target){
        int dx=0;
        int dy=0;
        if(pos_x > target[0]){
            dx-=1;
        }
        else if(pos_x < target[0]){
            dx +=1;
        } else if (pos_y > target[1]) {
            dy -=1;
        } else if (pos_y < target[1]) {
            dy +=1;
        }
        Farm.field.get(pos_y).get(pos_x).delCreature(this);
        pos_x += dx;
        pos_y += dy;
        Farm.field.get(pos_y).get(pos_x).addCreature(this);
    }

    protected int getRandomNum(){
        int N=Farm.field.size();
        return (int)(Math.random() * N);
    }

    protected boolean doWithProbability(double probability){
        return (int)(Math.random() + probability) == 1;
    }
        //IS
    public int[] getPos(){

        return new int[]{pos_x, pos_y};
    }//IS
    public void setPos(int pos_x, int pos_y){
        this.pos_x = pos_x;
        this.pos_y = pos_y;
    }//IS


    public boolean inBounds(int x, int y){
        int N = Farm.field.size();
        return 0<= y && y<N && 0<=x && x<N;
    }

    int getDistance(Creature c1, Creature c2){
        return (abs(c1.pos_x - c2.pos_x) +abs(c1.pos_y - c2.pos_y));
    }


}
