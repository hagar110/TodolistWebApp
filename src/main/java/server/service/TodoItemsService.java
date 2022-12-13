package server.service;

import enums.Category;
import enums.Priority;
import enums.SearchKey;
import server.model.TodoItem;
import server.repository.TodoItemsRepository;
import ui.Font;
import utility.DateUtils;
import utility.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

public class TodoItemsService {
    private final TodoItemsRepository repository;
    private Font font;

    private static TodoItemsService todoService;
    public static TodoItemsService getInstance(){
        if (todoService==null){
            todoService=new TodoItemsService();
        }
        return todoService;
    }
    private TodoItemsService() {
        this.repository = TodoItemsRepository.getInstance();
        this.font = new Font();

    }
    public boolean addTodoItem(String name, TodoItem item) {
        return repository.createUserTodo(name, item);
    }

    public boolean updateTodoItem(String name,TodoItem item, String oldTitle){
        return repository.updateTodoItem(name,item,oldTitle);
    }

    public boolean deleteTodoItem(String title, String name) {
        return repository.deleteTodoItem(title, name);
    }

    public ArrayList<TodoItem> getTodosFromDB(ResultSet result) {
        ArrayList<TodoItem> todos = new ArrayList<>();

        DateUtils dateUtils = new DateUtils();
        Utils utils = new Utils();
        try {
            TodoItem todo;
            while (result.next()) {
                if (result.getString(1) == null) {
                    break;
                }
                String currentFormat = "yyyy-MM-dd";
                todo = new TodoItem();
                todo.setTitle(result.getString("title"));
                todo.setDescription(result.getString("description"));
                todo.setPriority(Priority.valueOf(utils.capitalizeFirstLetter(result.getString("priority"))));
                todo.setCategory(Category.valueOf(utils.capitalizeFirstLetter(result.getString("category"))));
                todo.setFavorite(result.getInt("isFavorite") == 1);
                todo.setStartDate(dateUtils.changeFormat(currentFormat, result.getDate("startDate")));
                todo.setEndDate(dateUtils.changeFormat(currentFormat, result.getDate("endDate")));
                todos.add(todo);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return todos;
    }

    public void showTop5ItemsByDate(String username) {
        ResultSet result = repository.getUserLatestTodos(username);
        ArrayList<TodoItem> items = getTodosFromDB(result);
        items.forEach(System.out::println);
    }

    public int getItemByTitle(String title, ArrayList<TodoItem> userTodoItems) {
        for (int i = 0; i < userTodoItems.size(); i++) {
            if (userTodoItems.get(i).getTitle().equalsIgnoreCase(title)) {
                return i;
            }
        }
        return -1;
    }
    public ArrayList<TodoItem>searchByKey(SearchKey searchKey, String searchValue,String username){
        ArrayList<TodoItem> userTodos = null;
        switch (searchKey) {
            case Title:
                userTodos = getTodosFromDB(repository.searchByTitle(username, searchValue));
                break;
            case Priority:
                userTodos = getTodosFromDB(repository.searchByPriority(username, searchValue));
                break;
            case StartDate:
                try {
                    Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(searchValue);
                    userTodos = getTodosFromDB(repository.searchByStartDate(username, searchValue));
                } catch (ParseException e) {
                    System.out.println(font.ANSI_RED + "invalid date format" + font.ANSI_RESET);

                }
                break;
            case EndDate:
                try {
                    Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(searchValue);
                    userTodos = userTodos = getTodosFromDB(repository.searchByEndDate(username, searchValue));
                } catch (ParseException e) {
                    System.out.println(font.ANSI_RED + "invalid date format" + font.ANSI_RESET);

                }
                break;
        }
        return userTodos;
    }
    public boolean addItemToFavorite(String name,String title){
        boolean updated = repository.addItemToFavorite(name,title);
        return updated;
    }

    public boolean addItemToCategory(String name,String title, Category category){
        boolean updated = repository.addItemToCategory(name,title,category);
        return updated;
    }



}
