package edu.curtin.oose2024s1.assignment2;
import edu.curtin.oose2024s1.assignment2.factory.DeliveryMessage;
import edu.curtin.oose2024s1.assignment2.factory.DropOffMessage;
import edu.curtin.oose2024s1.assignment2.factory.Message;
import edu.curtin.oose2024s1.assignment2.factory.PickUpMessage;
import edu.curtin.oose2024s1.assignment2.factory.PurchaseInStoreMessage;
import edu.curtin.oose2024s1.assignment2.factory.PurchaseOnlineMessage;

public class SimulationInput {

    //initializing total number of processed messages
    private static int totalMessages = 0;

    //Processes a message, updates total message count or failure count based on validity
    public static void processMessage(String msg, BikeShop bikeShop) {
    totalMessages++;
    if (isValidMessage(msg)) {
        Message message = createMessage(msg);
        if (message != null) {
        message.process(bikeShop);
        //totalMessages++;
        } else {
        //increment the failure message count in the bike shop
        bikeShop.failuremassagecount++;
        System.out.println("FAILURE: Invalid message format: " + msg);
        }
    } else {
        System.out.println("FAILURE: Invalid message: " + msg);
        //increment the failure message count in the bike shop
        bikeShop.failuremassagecount++;
    }
    }

    //Generates a Message object depending on the message type
    private static Message createMessage(String msg) {
    if (msg.startsWith("DELIVERY")) {
        return new DeliveryMessage();
    } else if (msg.startsWith("DROP-OFF")) {
        //Extract the email address from the message.
        String email = msg.split(" ")[1];
        return new DropOffMessage(email);
    } else if (msg.startsWith("PURCHASE-ONLINE")) {
        //Extract the email address from the message.
        String email = msg.split(" ")[1];
        return new PurchaseOnlineMessage(email);
    } else if (msg.startsWith("PURCHASE-IN-STORE")) {
        return new PurchaseInStoreMessage();
    } else if (msg.startsWith("PICK-UP")) {
        //Extract the email address from the message.
        String email = msg.split(" ")[1];
        return new PickUpMessage(email);
    }
    return null;
    }

    // Validates the message format
    private static boolean isValidMessage(String msg) {
        String[] validPrefixes = {"DELIVERY", "DROP-OFF", "PURCHASE-ONLINE", "PURCHASE-IN-STORE", "PICK-UP"};
        //Iterate through each valid prefix
        for (String prefix : validPrefixes) {
            if (msg.startsWith(prefix)) {
                if (prefix.equals("DROP-OFF") || prefix.equals("PURCHASE-ONLINE") || prefix.equals("PICK-UP")) {
                    //Ensure that the message includes the message and an email.
                    if (msg.split(" ", 2).length == 2) { 
                        return true;
                    }
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    //Getter for totalMessages
    public static int getTotalMessages() {
        return totalMessages;
    }

   
}
