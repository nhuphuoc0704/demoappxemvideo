package home;

import java.util.List;

import home.category.Category;

public interface IHomeVideo {
    void onSuccessFul();
    void onMessenger(String mes);
    void onGetDataVideoHome(List<Category> categories);

}
