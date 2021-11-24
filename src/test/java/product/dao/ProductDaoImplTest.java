package product.dao;

import application.Utils;
import org.testng.annotations.*;
import product.domain.Product;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ProductDaoImplTest {
    Connection connection;
    ProductDaoImpl productDao;
    
    
    @BeforeMethod
    public void setUp() throws Exception {
        connection = Utils.openConnection();
        productDao = new ProductDaoImpl(connection);
    }
    
    @Test
    public void getTest() {
        List<Product> productList = productDao.getAll();
        List<Long> productIdList = new ArrayList<>();
        for (Product product: productList) {
            productIdList.add(product.getId());
        }
        Product product = productDao.get(productIdList.get((int)(Math.random() * productIdList.size())));
        assertEquals("Product isn't correct",product.getProductName(), productDao.get(product.getId()).getProductName());
    }
    
    @Test
    public void getAllTest() {
        int sizeList = productDao.getAll().size();
        assertTrue("Size isn't correct",sizeList>0);
    }
    
    @Test
    public void createTest() {
        List<Product> productList = productDao.getAll();
        List<Long> productIdList = new ArrayList<>();
        Long lastProduct = 0L;
        for (Product product: productList) {
            productIdList.add(product.getId());
            lastProduct = product.getId();
        }
        Product product = productDao.get(productIdList.get((int)(Math.random() * productIdList.size())));
        Product productCreated = new Product(
                product.getProductName()+" i3",
                product.getCategory(),
                product.getPrice()+50);
        productDao.create(productCreated);
        assertNotNull(productDao.get(lastProduct+1));
    }
    
    @Test
    public void updateTest() {
        List<Product> productList = productDao.getAll();
        List<Long> productIdList = new ArrayList<>();
        for (Product product: productList) {
            productIdList.add(product.getId());
        }
        Product product = productDao.get(productIdList.get((int)(Math.random() * productIdList.size())));
        product.setPrice(product.getPrice()+10);
        Product productUpdate = new Product(product.getProductName(), product.getCategory(), product.getPrice()+10);
        productDao.update(product.getId(), productUpdate);
        assertNotEquals(product.getPrice(), productUpdate.getPrice());
    }
    
    @Test
    public void deleteTest() {
        productDao.create(new Product("itemToDelete", "deleted", 10.0));
        List<Product> productList = productDao.getAll();
        Long lastProduct = 0L;
        for (Product product:productList) {
            lastProduct = product.getId();
        }
        Long idBeforeDelete = productDao.get(lastProduct).getId();
        productDao.delete(idBeforeDelete);
        assertNull(productDao.get(idBeforeDelete).getId());
    }

    @AfterMethod
    public void tearDown() throws Exception {
        Utils.closeConnection(connection);
    }
    
    
}