package co.istad.sms.dao.impl;

import co.istad.sms.dao.CategoryDao;
import co.istad.sms.database.DbSingleton;
import co.istad.sms.domain.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    private final Connection connection;
    public  CategoryDaoImpl(){
        connection = DbSingleton.getDbSingleton().getConnection();
    }

    @Override
    public List<Category> findAll() {

        String sql = """
                    SELECT * FROM categories
                    ORDER BY id DESC
                """;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            List<Category> categories = new ArrayList<>();
            while (resultSet.next()){
                Category category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setName(resultSet.getString("name"));
                categories.add(category);
            }
            return  categories;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public Category addNew(Category newCategory) {
        String sql = """
                INSERT INTO categories (name)
                VALUES (?)
                """;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, newCategory.getName());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0){
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()){
                    System.out.println(resultSet.getInt("id"));
                }
                System.out.println("Category added successfully");
            }else {
                System.out.println("Category is failed to add");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Category findById(Integer id) {

        String sql = """ 
                SELECT * FROM categories
                WHERE id = ?
                """;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet =statement.executeQuery();
            if (resultSet.next()){
                Category category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setName(resultSet.getString("name"));
                return category;
            }
            return null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
