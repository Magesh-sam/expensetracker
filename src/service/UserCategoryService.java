package service;

import java.sql.SQLException;

import context.AppContext;
import model.dao.UserCategoryDAO;
import util.Validation;

public class UserCategoryService {
    private static final UserCategoryDAO userCategoryDAO = AppContext.getUserCategoryDAO();
    private static UserCategoryService userCategoryService;

    private UserCategoryService() {

    }

    public static UserCategoryService getInstance() {
        if (userCategoryService == null) {
            userCategoryService = new UserCategoryService();
        }
        return userCategoryService;
    }

    public int addCategoryToUser(int userId, int categoryId) throws SQLException {
        Validation.validateId("user", userId);
        Validation.validateId("category", categoryId);
        userCategoryDAO.addCategoryToUser(userId, categoryId);
        return -1;
    }
}
