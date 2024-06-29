package co.istad.sms;

import co.istad.sms.dao.CategoryDao;
import co.istad.sms.dao.ProductDao;
import co.istad.sms.dao.impl.CategoryDaoImpl;
import co.istad.sms.dao.impl.ProductImpl;
import co.istad.sms.database.DbSingleton;
import co.istad.sms.domain.Category;
import co.istad.sms.domain.Product;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Scanner;

public class CoffeeShopApplication {
    
    private final ProductDao productDao;
    private final CategoryDao categoryDao;
    private final Scanner scanner;

    public CoffeeShopApplication(){
        scanner = new Scanner(System.in);
        categoryDao = new CategoryDaoImpl();
        productDao = new ProductImpl();
    }

    public static void main(String[] args) {

        CoffeeShopApplication app = new CoffeeShopApplication();

        do {
            System.out.println("Welcome to KBB Cafe Shop");
            System.out.println("=".repeat(50));
            System.out.println("Application Manu");
            System.out.println("A.Admin Management");
            System.out.println("B.Staff Management");
            System.out.println("C.Sale Management");
            System.out.println("E.Exit Management");
            System.out.print("=> Choose manu: ");
            String manu = app.scanner.nextLine();

            switch (manu.toUpperCase()){
                case "A" -> app.adminManagement();
                case "B" -> app.staffManagement();
                case "C" -> app.saleManagement();
                case "E" -> System.exit(0);
                default -> System.out.println("Invalid Manu");
            }
        }while(true);
    }
    
    private void adminManagement(){
        do {
            System.out.println("==========================");
            System.out.println("ADMIN Management");
            System.out.println("A. List of Categories");
            System.out.println("B. Add new Category");
            System.out.println("C. List of Products");
            System.out.println("D. Add new Product");
            System.out.println("F. Find Product by Find ID");
            System.out.println("G. Delete Product by ID");
            System.out.println("H. Update Product by ID");
            System.out.println("E. Exit");
            System.out.print("->Choose manu: ");
            String manu = scanner.nextLine();
            switch (manu.toUpperCase()){
                case "A" ->{
                    System.out.println("ID\t\tName");
                    categoryDao.findAll()
                            .forEach(category ->{
                                System.out.printf("%d\t\t%s\n", category.getId(),category.getName());
                            });
                }
                case "B" ->{
                    Category newCategory = new Category();
                    System.out.print(">Enter new category name: ");
                    newCategory.setName(scanner.nextLine());
                    categoryDao.addNew(newCategory);
                }
                case "C" ->{
                    System.out.println("ID\t\tName\t\tPrice\t\tUnit\t\tCName");
                    productDao.findAll()
                            .forEach(product ->{
                                System.out.printf("%d\t\t%s\t\t%.2f\t\t%s\t\t%s\n",
                                        product.getId(),
                                        product.getName(),
                                        product.getPrice(),
                                        product.getUnit(),
                                        product.getCategory().getName());
                            });
                }
                case "D" ->{
                    Product newProduct = new Product();
                    Category category = new Category();

                    System.out.print(">Enter new Product name: ");
                    newProduct.setName(scanner.nextLine());
                    System.out.print(">Enter new Product price : ");
                    newProduct.setPrice(BigDecimal.valueOf(Double.parseDouble(scanner.nextLine())));
                    System.out.print(">Enter new Product Unit: ");
                    newProduct.setUnit(scanner.nextLine());
                    System.out.print(">Enter new Product category ID: ");
                    category.setId(Integer.parseInt(scanner.nextLine()));
                    newProduct.setCategory(category);

                    newProduct = productDao.addNew(newProduct);
                    printProduct(newProduct);

                }
                case "F" ->{
                    System.out.print("=> Enter Product ID: ");
                    Integer id = Integer.parseInt(scanner.nextLine());
                    Product product = productDao.findById(id);
                    printProduct(product);
                }
                case "G" ->{
                    System.out.print("=> Enter product ID: ");
                    Integer id = Integer.parseInt(scanner.nextLine());
                    productDao.deleteById(id);
                }
                case "H" ->{
                    Product updateProduct = new Product();
                    Category category = new Category();

                    System.out.println(">Enter Product ID: ");
                    Integer id = Integer.parseInt(scanner.nextLine());

                    Product oldProduct  = productDao.findById(id);
                    if (oldProduct != null){
                        System.out.print(">Enter Product name: ");
                        updateProduct.setName(scanner.nextLine());
                        System.out.print(">Enter Product price : ");
                        updateProduct.setPrice(BigDecimal.valueOf(Double.parseDouble(scanner.nextLine())));
                        System.out.print(">Enter Product Unit: ");
                        updateProduct.setUnit(scanner.nextLine());
                        System.out.print(">Enter Product category ID: ");
                        category.setId(Integer.parseInt(scanner.nextLine()));
                        updateProduct.setCategory(category);
                        productDao.updateById(id, updateProduct);
                    }
                }
                case "E" ->{
                    return;
                }
            }
            promptEnterKey();
        }while (true);
    }

    private void staffManagement(){
        do {
            System.out.println("==========================");
            System.out.println("STAFF Management");
            System.out.println("A. View of Products");
            System.out.println("B. Find Product by Find ID");
            System.out.println("C. Update Product by ID");
            System.out.println("E. Exit");
            System.out.print("->Choose manu: ");
            String manu = scanner.nextLine();
            switch (manu.toUpperCase()){
                case "A" ->{
                    System.out.println("ID\t\tName\t\tPrice\t\tUnit\t\tCName");
                    productDao.findAll()
                            .forEach(product ->{
                                System.out.printf("%d\t\t%s\t\t%.2f\t\t%s\t\t%s\n",
                                        product.getId(),
                                        product.getName(),
                                        product.getPrice(),
                                        product.getUnit(),
                                        product.getCategory().getName());
                            });
                }
                case "B" ->{
                    System.out.print("=> Enter Product ID: ");
                    Integer id = Integer.parseInt(scanner.nextLine());
                    Product product = productDao.findById(id);
                    printProduct(product);
                }
                case "C" ->{
                    Product updateProduct = new Product();
                    Category category = new Category();

                    System.out.println(">Enter Product ID: ");
                    Integer id = Integer.parseInt(scanner.nextLine());

                    Product oldProduct  = productDao.findById(id);
                    if (oldProduct != null){
                        System.out.print(">Enter Product name: ");
                        updateProduct.setName(scanner.nextLine());
                        System.out.print(">Enter Product price : ");
                        updateProduct.setPrice(BigDecimal.valueOf(Double.parseDouble(scanner.nextLine())));
                        System.out.print(">Enter Product Unit: ");
                        updateProduct.setUnit(scanner.nextLine());
                        System.out.print(">Enter Product category ID: ");
                        category.setId(Integer.parseInt(scanner.nextLine()));
                        updateProduct.setCategory(category);
                        productDao.updateById(id, updateProduct);
                    }
                }
                case "E" ->{
                    return;
                }
                default -> System.out.println("Invalid Letter");
            }
            promptEnterKey();
        }while (true);
    }

    private void saleManagement(){
        do {
            System.out.println("==========================");
            System.out.println("SALE Management");
            System.out.println("A. View of Products");
            System.out.println("B. Sale Product");
            System.out.println("E. Exit");
            System.out.print("->Choose manu: ");
            String manu = scanner.nextLine();
            switch (manu.toUpperCase()){
                case "A" ->{
                    System.out.println("ID\t\tName\t\tPrice\t\tUnit\t\tCName");
                    productDao.findAll()
                            .forEach(product ->{
                                System.out.printf("%d\t\t%s\t\t%.2f\t\t%s\t\t%s\n",
                                        product.getId(),
                                        product.getName(),
                                        product.getPrice(),
                                        product.getUnit(),
                                        product.getCategory().getName());
                            });
                }
                case "B" -> {
                    System.out.print("=> Enter Product ID: ");
                    Integer id = Integer.parseInt(scanner.nextLine());
                    Product product = productDao.findById(id);
                    if (product != null) {
                        System.out.println("==============|KBB CAFE SHOP|=================");
                        System.out.println("Receipt ");
                        System.out.println("Name = " + product.getName());
                        System.out.println("Price = " + product.getPrice());
                        System.out.println("Unit = " + product.getUnit());
                        System.out.println("===============================================");
                    }else{
                        System.out.println("================");
                        System.out.println("No Product");
                        System.out.println("================");
                    }
                }
                case "E" ->{
                    return;
                }
                default -> System.out.println("Invalid Letter");
            }
            promptEnterKey();
        }while (true);
    }
    
        public void promptEnterKey(){
            System.out.println("Press \"ENTER\" to continue...");
            try {
                System.in.read();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        public void printProduct(Product product){
        if (product != null) {
            System.out.println("Product");
            System.out.println("ID = " + product.getId());
            System.out.println("Name = " + product.getName());
            System.out.println("Price = " + product.getPrice());
            System.out.println("Unit = " + product.getUnit());
            System.out.println("Cate ID = " + product.getCategory().getId());
            System.out.println("Cate Name = " + product.getCategory().getName());
            }else{
            System.out.println("================");
            System.out.println("No Product");
            System.out.println("================");
            }
        }
}
