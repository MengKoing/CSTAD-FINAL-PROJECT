package co.istad.sms.dao;

import co.istad.sms.domain.Product;

import java.util.List;

public interface ProductDao {

    Product addNew (Product product);

    Product findById (Integer id);

    List<Product> findAll();

    void deleteById (Integer id);

    void updateById (Integer id, Product product);

}
