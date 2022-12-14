package ui;

import enums.Category;

import java.util.ArrayList;
import java.util.Arrays;

public class Text {
    public ArrayList<String> menuOptions = new ArrayList<>(
            Arrays.asList("1- Add item ", "2- Update item ", "3- Delete item", "4- Show All items",
                    "5- Show top 5 nearest by date", "6- Search by title, date (start & End), or priority",
                    "7- Add item to a category", "8- Add item to a favorite", "9- show favorites","10- Clear Screen", "11- Update Your Name","12- Log out","13- Exit"));

    public ArrayList<Category> categories = new ArrayList<>(Arrays.asList(Category.Work, Category.Chores,
            Category.People, Category.Learning, Category.Other, Category.None));

    public String choosePriority = "Choose priority for the item (1.Low, 2.Medium, 3.High):";
    public String chooseCategory = "Choose category for the item (1.work, 2.chores, 3.People, 4.Learning, 5.Other, 6.No category)";
    public String enterStartDate = "Enter start date of the item (e.g. yyyy-MM-dd)";
    public String enterEndDate = "Enter end date of the item (e.g. yyyy-MM-dd)";
    public String chooseSearchFilter = "choose filter for search (1.title, 2.start date, 3.end date, 4.priority)";



}
