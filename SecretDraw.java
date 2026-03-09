import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class SecretDraw {

    // States
    public static final String STATE_NONE = "NONE";
    public static final String STATE_CREATED = "CREATED";
    public static final String STATE_DRAWN = "DRAWN";

    // Draw data
    public static String drawName = null;
    public static String drawDescription = null;
    public static double suggestedBudget = 0.0;
    public static LocalDate eventDate = null;
    public static String drawState = STATE_NONE;


    // Participants
    public static final ArrayList<String> participants = new ArrayList<>();
    public static int[] assignedFriendIndex = null;

    //Scanner
    public static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Random random = new Random();
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
                    System.out.println("--------------------------");
                    System.out.println("   REGISTER PARTICIPANTS ");
                    System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
                    registerParticipants();
                    break;
                case 3:
                    System.out.println("--------------------------");
                    System.out.println("       PARTICIPANTS ");
                    System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
                    listParticipants();
                    break;
                case 4:
                    System.out.println("\n-----------------------------------------------------------------------------");
                    System.out.println(" Draw completed successfully!. See the results in the Draw Summary option (5) ");
                    System.out.println("-------------------------------------------------------------------------------\n");
                    runDrawRaffle(random);
                    break;
                case 5:
                    System.out.println("\n--------------------------");
                    System.out.println("        DRAW SUMMARY ");
                    System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯\n");
                    drawSummary();
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
        System.out.println("\n***********************************");
        System.out.println("* Draw registered successfully!   *");
        System.out.println("***********************************");
        
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

    public static double readDouble(String prompt) {
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

    public static LocalDate readDate(String prompt) {
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

    public static void registerParticipants() {
        if (!isDrawRegistered()) {
            System.out.println("Error: you must create the draw first.");
            return;
        }
    
        if (drawState.equals(STATE_DRAWN)) {
            System.out.println("Error: you cannot add participants after running the draw.");
            return;
        }
    
        int numberOfParticipants;
        while (true) {
            numberOfParticipants = readInt("How many participants do you want to register? ");
            if (numberOfParticipants > 0) {
                break;
            }
            System.out.println("Error: number must be greater than 0.");
        }   
        for (int i = 1; i <= numberOfParticipants; i++) {
            String name = readText("Enter participant " + i + " name: ");
    
            if (isDuplicateParticipant(name)) {
                System.out.println("Error: this participant already exists.");
                i--;
                continue;
            }
    
            participants.add(name);
        }
        System.out.println("\n*******************************************");
        System.out.println("* Participants registered successfully!   *");
        System.out.println("*******************************************");
    }

    public static boolean isDuplicateParticipant(String participantName) {
        String normalizedInputName = participantName.trim().toLowerCase();
    
        for (int i = 0; i < participants.size(); i++) {
            String normalizedParticipantName = participants.get(i).trim().toLowerCase();

            if (normalizedParticipantName.equals(normalizedInputName)) {
                return true;
            }
        }
        return false;
    }

    public static void listParticipants() {
        if (participants.isEmpty()) {
            System.out.println("No participants registered yet.");
            return;
        }
        System.out.println("List of participants for the draw "+drawName+":\n");
        for (int i = 0; i < participants.size(); i++) {
            System.out.println((i + 1) + ") " + participants.get(i));
        }
    }

    public static void runDrawRaffle(Random random) {
        if (!isDrawRegistered()) {
            System.out.println("Error: you must create the draw before running it.");
            return;
        }
    
        if (participants.size() < 2) {
            System.out.println("Error: at least 2 participants are required.");
            return;
        }
    
        if (drawState.equals(STATE_DRAWN)) {
            System.out.println("The draw has already been done.");
            return;
        }
    
        assignedFriendIndex = generateAssignments(participants.size(), random);
        drawState = STATE_DRAWN;
    
    }


    public static int[] generateAssignments(int numberOfParticipants, Random random) {
        int[] assignment = new int[numberOfParticipants];
    
        for (int participantIndex = 0; participantIndex < numberOfParticipants; participantIndex++) {
            assignment[participantIndex] = participantIndex;
        }
    
        do {
            shuffleAssignments(assignment, random);
        } while (hasSelfAssignments(assignment));
    
        return assignment;
    }

    public static boolean hasSelfAssignments(int[] assignedFriendIndexes) {
        for (int participantIndex = 0; participantIndex < assignedFriendIndexes.length; participantIndex++) {
            if (assignedFriendIndexes[participantIndex] == participantIndex) {
                return true;
            }
        }
        return false;
    }

    public static void shuffleAssignments(int[] assignedFriendIndexes, Random random) {
        for (int currentIndex = assignedFriendIndexes.length - 1; currentIndex > 0; currentIndex--) {
            int randomIndex = random.nextInt(currentIndex + 1);
            int temporaryValue = assignedFriendIndexes[currentIndex];
            assignedFriendIndexes[currentIndex] = assignedFriendIndexes[randomIndex];
            assignedFriendIndexes[randomIndex] = temporaryValue;
        }
    }

    public static void drawSummary() {
        if (!isDrawRegistered()) {
            System.out.println("ERROR: No raffle registered yet.");
            return;
        }
  
        System.out.println("Name: " + drawName);
        System.out.println("Description: " + drawDescription);
        System.out.println("Suggested budget: " + suggestedBudget);
        System.out.println("Event date: " + eventDate);
        System.out.println("State: " + drawState);

        if (!STATE_DRAWN.equals(drawState)) {
            System.out.println("WARNING!: The raffle has not been drawn yet.");
            return;
        }

        System.out.println("\nAssignments (participant -> secret friend):");
        for (int i = 0; i < participants.size(); i++) {
            int friendIndex = assignedFriendIndex[i];
            System.out.println(participants.get(i) + " -> " + participants.get(friendIndex));
        }
    }

}