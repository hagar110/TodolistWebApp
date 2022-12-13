package utility;

import server.model.TodoItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ui.Font;

import java.util.HashMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Utils {
    Font font = new Font();
    Scanner data = new Scanner(System.in);

    public void PrintColoredMessage(String color, String message) {
        System.out.println(color + message + font.ANSI_RESET);
    }

    public void print(String message) {
        System.out.println(message);
    }

    public String getInput(String message) {// used to make sure that user input(string) is not empty or not only just ' ' character
        // System.out.println("Hello, what is your name?");
        String userInput = data.nextLine();
        userInput = userInput.trim();
        // System.out.println("in validation :: "+userInput);
        while (userInput.matches(" +") || userInput.isEmpty()) {
            System.out.println(font.ANSI_RED + message + font.ANSI_RESET);
            userInput = data.nextLine();
        }
        return userInput;
    }


    public int getInput(String message, int startLimit, int endLimit) {
        String userInput = data.nextLine();
        userInput = userInput.trim();
        if (userInput.equalsIgnoreCase("/back")) return -1;
        while (!userInput.matches("\\d+")
                || Integer.parseInt(userInput) < startLimit
                || Integer.parseInt(userInput) > endLimit) {
            System.out.println(font.ANSI_RED + message + font.ANSI_RESET);
            userInput = data.nextLine();
            if (userInput.equalsIgnoreCase("/back")) return -1;
        }
        return Integer.parseInt(userInput);
    }

    public String capitalizeFirstLetter(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    public ArrayList<String> convertItemsToJson(ArrayList<TodoItem> items){
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<String> toJson = new ArrayList<>();

        for (TodoItem item: items) {
            try {
                toJson.add(mapper.writeValueAsString(item));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return toJson;
    }

    public ArrayList<TodoItem> jsonToTodos(ArrayList<String> list){
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<TodoItem> items = new ArrayList<>();
        try {
            for(String l : list){
                TodoItem obj = mapper.readValue(l, TodoItem.class);
                items.add(obj);
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        return items;
    }


    public HashMap<String,String> todoItemToMap(TodoItem todoItem){
        DateUtils dateUtils = new DateUtils();

        HashMap<String,String> todoItemMap = new HashMap<>();
        todoItemMap.put("title",todoItem.getTitle());
        todoItemMap.put("description",todoItem.getDescription());
        todoItemMap.put("priority",todoItem.getPriority().toString());
        todoItemMap.put("category",todoItem.getCategory().toString());
        todoItemMap.put("startDate",dateUtils.convertDateToString(todoItem.getStartDate()));
        todoItemMap.put("endDate",dateUtils.convertDateToString(todoItem.getEndDate()));
        todoItemMap.put("isFavorite",String.valueOf(todoItem.isFavorite()));
        return todoItemMap;
    }
    public int getItemByTitle(String title,ArrayList<TodoItem> items){
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getTitle().equalsIgnoreCase(title)) {
                return i;
            }
        }
        return -1;
    }
}
