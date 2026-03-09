import java.util.*;
public class SecretDraw{

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        boolean exit = false;

        while(!exit){
            printMenu();
            String input = sc.nextLine();
            int option;
            
            try {
                option = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please, enter a valid number");
                continue;

            }
            if(option<0 || option>5){
                System.out.println("Option must be between 0 and 5");
                continue;
            }
            
            switch(option){
                case 1:
                    System.out.println("1");
                    break;
                case 2:
                    System.out.println("2");
                    break;
                case 3:
                    System.out.println("3");
                    break;
                case 4:
                    System.out.println("4");
                    break;
                case 5:
                    System.out.println("5");
                    break;
                case 0:
                    exit = true;
                    System.out.println("Bye!");
                    break;
            }

        }
        

    }
    public static void printMenu(){
        System.out.println("\n*******************");
        System.out.println("    Secret Draw    ");
        System.out.println("*******************\n");
        System.out.println("       Menu\n");
        System.out.println("1) Regiter Raffle");
        System.out.println("2) Regiter Participants");
        System.out.println("3) List Participants");
        System.out.println("4) Run Draw");
        System.out.println("5) Draw Summary");
        System.out.println("0) Exit")
        ;
    }
}