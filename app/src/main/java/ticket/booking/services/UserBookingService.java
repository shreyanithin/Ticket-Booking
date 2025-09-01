package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.User;
import ticket.booking.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserBookingService {

    private User user;

    private ObjectMapper objectMapper = new ObjectMapper();
    private List<User> userList;

    private static final String USERS_PATH="app/src/main/java/ticket/booking/localDB/users.json";

    public UserBookingService(User user1)throws IOException {
        //store user already logged in so can be passed to other functions
        this.user=user1;
        File users=new File(USERS_PATH);
        userList = ObjectMapper.readValue(users,new TypeReference<List<User>>);
    }

    public Boolean loginUser(){
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
        return foundUser.isPresent();
    }

    public Boolean signUp(User user1){
        try{
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        }catch (IOException ex){
            return Boolean.FALSE;
        }
    }

    private void saveUserListToFile() throws IOException {
        File usersFile = new File(USERS_PATH);
        objectMapper.writeValue(usersFile, userList);
    }
    //object -> json = serialize
    //json -> object =deserialize

    public void fetchBooking(String ticketID){
        user.printTickets();
    }

    public boolean cancelBooking(String ticketId){
        boolean removed=user.getTicketsBooked().removeIf(ticket -> ticket.getTicketId().equals(ticketId));
        if(removed){
            System.out.println("Ticket with ID " + ticketId + " has been canceled.");
            return Boolean.TRUE;
        }
        else{
            System.out.println("No ticket found with ID " + ticketId);
            return Boolean.FALSE;
    }
}
