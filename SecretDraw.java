
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
/*

*/

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

   /**
  Main entry point of the program.
 
  Inputs:
  - args: command-line arguments passed to the program.
 
  Process:
  - Displays the main menu in a loop.
  - Reads the user option from the console.
  - Validates that the option is numeric and within the allowed range.
  - Executes the selected action such as creating a draw,
    registering participants, listing participants, or exiting.
 
  Outputs:
  - Prints messages and menus to the console.
  - Invokes the corresponding program methods.
  - Ends the program when the user selects option 0.
  @param args command-line arguments (not used in this program)
 */

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
  Prints the main menu of the program.
  Inputs:
  - None.
  Process:
  - Displays the title of the program.
  - Shows the list of available menu options.
  Outputs:
  - Prints the menu options to the console.
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
  Creates a new draw and stores its information.
  Inputs:
  - Draw name entered by the user.
  - Draw description entered by the user.
  - Suggested budget entered by the user.
  - Event date entered by the user.
  - Overwrite confirmation if a draw is already registered.
  Process:
  - Checks whether a draw already exists.
  - If a draw exists, asks the user whether to overwrite it.
  - Resets previous draw data if overwrite is confirmed.
  - Reads and validates the new draw information.
  - Updates the draw state to CREATED.
  - Clears previous participant assignments.
  Outputs:
  - Stores the draw data in the corresponding variables.
  - Prints confirmation or cancellation messages to the console.
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

    /**
  Reads a non-empty text value from the user.
  Inputs:
  - userText: prompt message displayed to the user.
  Process:
  - Displays the prompt message.
  - Reads text input from the console.
  - Validates that the entered text is not empty.
  - Repeats the process until valid input is entered.
  Outputs:
  - Returns a valid non-empty text string.
  @param userText message displayed to request input
  @return a non-empty text entered by the user
 */

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
    /**
 * Reads a valid integer value from the user.
  Inputs:
  - prompt: message displayed to the user.
  Process:
  - Displays the prompt.
  - Reads input from the console.
  - Attempts to convert the input into an integer.
  - Repeats the process if the input is invalid.
 
  Outputs:
  - Returns a valid integer value.
  @param prompt message displayed to request an integer
  @return a valid integer entered by the user
 */

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

    /**
  Reads a valid positive decimal number from the user.
  Inputs:
  - prompt: message displayed to the user.
  Process:
  - Displays the prompt.
  - Reads input from the console.
  - Attempts to convert the input into a double value.
  - Validates that the number is greater than zero.
  - Repeats the process if the input is invalid.
  Outputs:
  - Returns a valid positive decimal number.
  @param prompt message displayed to request a decimal number
  @return a valid double value greater than zero
 */

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
    
    /**
  Reads a valid date from the user in YYYY-MM-DD format.
  Inputs:
  - prompt: message displayed to the user.
  Process:
  - Displays the prompt.
  - Reads input from the console.
  - Attempts to parse the input as a LocalDate.
  - Repeats the process if the format is invalid.
  Outputs:
  - Returns a valid date value.
  @param prompt message displayed to request a date
  @return a valid LocalDate entered by the user
 */

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
  Checks whether a draw is already registered.
  Inputs:
  - Current values of drawName, eventDate, and drawState.
  Process:
  - Verifies that the draw name is not null.
  - Verifies that the event date is not null.
  - Verifies that the draw state is either CREATED or DRAWN.
  Outputs:
  - Returns true if the draw is registered.
  - Returns false otherwise.
  @return true if a draw is registered; false otherwise
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
  Resets all draw and participant data to the initial state.
  Inputs:
  - Current stored draw data and participants.

  Process:
  - Clears the draw name, description, budget, and event date.
  - Resets the draw state to NONE.
  - Removes all registered participants.
  - Clears the assigned friend index array.
  Outputs:
  - Leaves the program data in its initial empty state.
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

    /**
  Registers participants for the current draw.
  Inputs:
  - Current draw state.
  - Number of participants entered by the user.
  - Participant names entered by the user.
  Process:
  - Verifies that a draw has already been created.
  - Verifies that the draw has not already been executed.
  - Requests the number of participants to register.
  - Reads each participant name.
  - Validates that each name is not duplicated.
  - Adds valid participant names to the participants list.
  Outputs:
  - Stores participant names in the participants list.
  - Prints success or error messages to the console.
 */

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

    /**
  Checks whether a participant name is already registered.
  Inputs:
  - participantName: name entered by the user.
  - Current participants list.
  Process:
  - Normalizes the input name by trimming spaces and converting to lowercase.
  - Iterates through the participants list.
  - Normalizes each stored participant name.
  - Compares the normalized names.
  Outputs:
  - Returns true if the participant already exists.
  - Returns false if the participant does not exist.
  @param participantName name to validate
  @return true if the participant is duplicated; false otherwise
 */

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
    /**
  Displays the list of registered participants.
  Inputs:
  - Current participants list.
  - Current draw name.
  Process:
  - Checks whether the participants list is empty.
  - If not empty, iterates through the list.
  - Prints each participant with its corresponding number.
  Outputs:
  - Prints the participant list to the console.
  - Prints a message if no participants are registered.
 */

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

    /**
  Executes the raffle draw process.
  Inputs:
  - random: Random object used to generate randomized assignments
  Process:
  - Verifies that a draw is registered.
  - Ensures there are at least two participants.
  - Checks that the draw has not already been executed.
  - Generates valid assignments where no participant is assigned to themselves.
  - Updates the draw state to DRAWN.
  Outputs:
  - Stores the generated assignments in the assignedFriendIndex array.
  - Updates the draw state.
  - Prints error messages if the draw cannot be executed.
  @param random Random generator used for assignment shuffling
 */

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

/**
  Generates the assignments for the secret draw.
  Inputs:
  - numberOfParticipants: total number of participants in the draw.
  - random: Random generator used for shuffling assignments.
  Process:
  - Creates an array representing participant indexes.
  - Randomly shuffles the indexes.
  - Repeats the shuffle until no participant is assigned to themselves.
  Outputs:
  - Returns an array where each index represents a participant
    and the value represents their assigned secret friend.
  @param numberOfParticipants total number of participants
  @param random random generator used for shuffling
  @return an array of valid participant assignments
 */

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

    /**
 * Checks whether any participant is assigned to themselves.
  Inputs:
  - assignedFriendIndexes: array containing the current assignments.
  Process:
  - Iterates through the assignment array.
  - Compares each index with its assigned value.
  Outputs:
  - Returns true if at least one participant is assigned to themselves.
  - Returns false if all assignments are valid.
  @param assignedFriendIndexes array containing assignments
  @return true if self-assignment exists, false otherwise
 */

    public static boolean hasSelfAssignments(int[] assignedFriendIndexes) {
        for (int participantIndex = 0; participantIndex < assignedFriendIndexes.length; participantIndex++) {
            if (assignedFriendIndexes[participantIndex] == participantIndex) {
                return true;
            }
        }
        return false;
    }

/**
  Randomly shuffles the assignments array.
  Inputs:
  - assignedFriendIndexes: array containing participant assignments.
  - random: Random generator used for shuffling.
  Process:
  - Applies the Fisher-Yates shuffle algorithm to randomize the array order.
  Outputs:
  - Modifies the assignment array in place with a new random order.
  @param assignedFriendIndexes array containing assignments to shuffle
  @param random random generator used for shuffling
 */

    public static void shuffleAssignments(int[] assignedFriendIndexes, Random random) {
        for (int currentIndex = assignedFriendIndexes.length - 1; currentIndex > 0; currentIndex--) {
            int randomIndex = random.nextInt(currentIndex + 1);
            int temporaryValue = assignedFriendIndexes[currentIndex];
            assignedFriendIndexes[currentIndex] = assignedFriendIndexes[randomIndex];
            assignedFriendIndexes[randomIndex] = temporaryValue;
        }
    }

/**
  Displays a summary of the draw and the final assignments.
 
  Inputs:
  - Stored draw information (name, description, budget, date, state).
  - Participant list and assignment array.
 
  Process:
  - Verifies that a draw has been registered.
  - Displays the draw information.
  - If the draw has been executed, prints the participant assignments.
 
  Outputs:
  - Prints the draw information and results to the console.
 */
    
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
