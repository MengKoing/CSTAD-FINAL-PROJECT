package co.istad.sms.dao;

import co.istad.sms.domain.Category;

import java.util.List;

public interface CategoryDao {

    // find all categories
    // return type = List<Category>

    List<Category> findAll();

    Category addNew(Category newCategory);

    Category findById(Integer id);
}
