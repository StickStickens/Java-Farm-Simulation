import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        int N, rabbit_num, dog_num, farmer_num;
        while(true){
            System.out.println("Give parameters to start simulation\n");
            System.out.println("size N");
            N = myScanner.nextInt();
            if(N>25){System.out.println("Come on! It won't fit in the terminal"); continue;}
            System.out.println("farmers num");
            farmer_num = myScanner.nextInt();
            if(farmer_num>N*(N-1)/2){System.out.println("Come on! It's too many farmers"); continue;}
            System.out.println("dogs num");
            dog_num = myScanner.nextInt();
            if(dog_num>N*(N-1)/2){System.out.println("Come on! It's too many dogs"); continue;}
            System.out.println("rabbits num");
            rabbit_num = myScanner.nextInt();
            if(rabbit_num>N*(N-1)/2){System.out.println("Come on! It's too many rabbits"); continue;}
            break;
        }


        Farm farm = new Farm(N, rabbit_num, dog_num, farmer_num);



    }
}