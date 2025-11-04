package interfaces;

import java.sql.SQLException;
import java.util.List;

import model.dto.Category;

public interface ICategoryDAO {

    int createCategory(Category category) throws SQLException;

    Category getCategoryById(int categoryId) throws SQLException;

    List<Category> getAllCategory() throws SQLException;

    boolean updateCategory(Category category) throws SQLException;

    boolean deleteCategory(int categoryId) throws SQLException;

}