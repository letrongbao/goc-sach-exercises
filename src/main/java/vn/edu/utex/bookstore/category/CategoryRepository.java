package vn.edu.utex.bookstore.category;
import java.util.List;
public interface CategoryRepository {
    Category category(long id);
    List<Category> categories(String query);
    Category saveCategory(Category category);
    void deleteCategory(long id);
}
