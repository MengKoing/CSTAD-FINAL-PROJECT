package co.istad.sms.dao.impl;

import co.istad.sms.dao.CategoryDao;
import co.istad.sms.dao.ProductDao;
import co.istad.sms.database.DbSingleton;
import co.istad.sms.domain.Category;
import co.istad.sms.domain.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductImpl implements ProductDao {

    private final Connection connection;
    private final CategoryDao categoryDao;

    public ProductImpl(){
        connection = DbSingleton.getDbSingleton().getConnection();
        categoryDao = new CategoryDaoImpl();
    }

    @Override
    public Product addNew(Product product) {

        String sql = """
                INSERT INTO products (name, price, unit, category_id)
                VALUES (?, ?, ?, ?)
                """;
        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, product.getName());
            statement.setBigDecimal(2,product.getPrice());
            statement.setString(3,product.getUnit());
            statement.setInt(4,product.getCategory().getId());

            int affectedRow = statement.executeUpdate();

            if (affectedRow > 0){
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()){
                    Integer keyId = resultSet.getInt("id");
                    return  findById(keyId);
                }
            }else{
                System.out.println("Product cannot be added");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Product findById(Integer id) {

        String sql = """
                SELECT p.id, p.name, p.price, p.unit, c.id AS cId, c.name AS cName
                FROM products AS p
                INNER JOIN categories AS c
                ON c.id = p.category_id
                WHERE p.id = ?
                """;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Product product = new Product();
                Category category = new Category();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setUnit(resultSet.getString("unit"));
                category.setId(resultSet.getInt("cId"));
                category.setName(resultSet.getString("cName"));
                product.setCategory(category);
                return product;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new Product();
        }
        return null;
    }

    @Override
    public List<Product> findAll() {

        String sql = """
                SELECT * FROM products
                """;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setUnit(resultSet.getString("unit"));

                Integer categoryId = resultSet.getInt("category_id");
                Category category = categoryDao.findById(categoryId);

                product.setCategory(category);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void deleteById(Integer id) {

        Product product = findById(id);

        if(product != null){
            String sql = """
                    DELETE FROM products
                    WHERE id = ?
                    """;

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, id);
                int affectedRow = statement.executeUpdate();
                if (affectedRow > 0){
                    System.out.println("Product has been deleted");
                }else{
                    System.out.println("Product can't be deleted");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    @Override
    public void updateById(Integer id, Product product) {
        String sql = """
                    UPDATE products
                    SET name = ?,
                    price = ?,
                    unit = ?,
                    category_id = ?
                    WHERE id = ?
                    """;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, product.getName());
            statement.setBigDecimal(2, product.getPrice());
            statement.setString(3, product.getUnit());
            statement.setInt(4, product.getCategory().getId());
            statement.setInt(5, id);

            int affectedRow = statement.executeUpdate();
            if (affectedRow > 0){
                System.out.println("Product has been updated");
            }else{
                System.out.println("Product can't be updated");

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Add new Product

}
