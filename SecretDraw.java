import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class SecretDraw {

    // States
    private static final String STATE_NONE = "NONE";
    private static final String STATE_CREATED = "CREATED";
    private static final String STATE_DRAWN = "DRAWN";

    // Draw data
    public static String drawName = null;
    public static String drawDescription = null;
    public static double suggestedBudget = 0.0;
    public static LocalDate eventDate = null;
    public static String drawState = STATE_NONE;


    // Participants
    private static final ArrayList<String> participants = new ArrayList<>();
    private static int[] assignedFriendIndex = null;

    //Scanner
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;

        while(!exit){
            printMenu();
            String input = sc.nextLine();
            int option;

            try {
                option = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            if(option < 0 || option > 5){
                System.out.println("Option must be between 0 and 5");
                continue;
            }

            switch(option){
                case 1:
                    System.out.println("--------------------------");
                    System.out.println("        CREATE DRAW       ");
                    System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
                    createDraw();
                    break;
                case 2:
                    System.out.println("Case 2");
                    break;
                case 3:
                    System.out.println("Case 3");
                    break;
                case 4:
                    System.out.println("Case 4");
                    break;
                case 5:
                    System.out.println("Case 5");
                    break;
                case 0:
                    exit=true;
                    System.out.println("Bye! :)");
                    break;
            }
        }
        
    }

    /**
     * Prints the main menu options.
     */
    public static void printMenu(){
        System.out.println("\n**********************");
        System.out.println("*     SECRET DRAW    *");
        System.out.println("**********************\n");
        System.out.println("        MENU\n");
        System.out.println("1. Create Draw");
        System.out.println("2. Register Participants");
        System.out.println("3. List Participants");
        System.out.println("4. Run Draw");
        System.out.println("5. Draw summary");
        System.out.println("0. Exit \n");
    }

    /**
     * Handles draw registration workflow. Registers ONE draw with required fields and sets state to CREATED.
     * If a draw already exists, it informs the user and allows overwriting.
     */    

    public static void createDraw(){
        if(isDrawRegistered()){
            System.out.println("A draw is already registered(" +drawName+").");
            
            while (true) { 
                System.out.println("Do you want to overwrite it? (y/n): ");
                String answer = sc.nextLine().trim().toLowerCase();

                if(answer.equals("y")){
                    resetDrawData();
                    break;
                }else if(answer.equals("n")){
                    System.out.println("Operation cancelled.");
                    return;
                }else{
                    System.out.println("Error: Please answer y or n");
                }
            }
        }
        drawName = readText("Enter Draw name: ");
        drawDescription = readText("Enter Draw description: ");
        suggestedBudget = readDouble("Enter suggested budget per gift: ");
        eventDate = readDate("Enter Draw date (YYYY-MM-DD): ");
        drawState = STATE_CREATED;
        assignedFriendIndex = null;
        System.out.println("Draw registered successfully.");
    }

    public static String readText(String userText){
        while (true) { 
            System.out.println(userText);
            String text = sc.nextLine();

            if(!text.isEmpty()){
                return text;
            }
            System.out.println("Error: value cannot be empty.");
        }
    }

    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: please enter a valid integer.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();
    
            try {
                double value = Double.parseDouble(input.trim());
    
                if (value > 0) {
                    return value;
                }    
                System.out.println("Error: value must be greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Error: please enter a valid number.");
            }
        }
    }    

    private static LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();
            try {
                return LocalDate.parse(input.trim());
            } catch (DateTimeParseException e) {
                System.out.println("Error: invalid date. Use YYYY-MM-DD.");
            }
        }
    }

    /**
     * Checks if a draw is already registered (name and date are set).
     *
     * @return true if registered; otherwise false
     */
    public static boolean isDrawRegistered(){
        if(drawName == null){
            return false;
        }
        if(eventDate == null){
            return false;
        }
        if (drawState.equals(STATE_CREATED) || drawState.equals(STATE_DRAWN)){
            return true;
        }
        return false;
    }

    /**
     * Resets draw and participant data to initial state.
     */
    public static void resetDrawData() {
        drawName = null;
        drawDescription = null;
        suggestedBudget = 0.0;
        eventDate = null;
        drawState = STATE_NONE;
        participants.clear();
        assignedFriendIndex = null;
    }    



}
