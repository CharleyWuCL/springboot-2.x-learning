package com.springboot.chapter16.main;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.springboot.chapter16.pojo.Product;
import com.springboot.chapter16.pojo.User;
import com.springboot.chapter16.service.ProductService;
import com.springboot.chapter16.service.UserService;

@RunWith(SpringRunner.class)
//使用随机端口启动测试服务
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class Chapter16ApplicationTests {

	// 注入用户服务类
    @Autowired
    private UserService userService = null;

    @Test
    public void contextLoads() {
        User user = userService.getUser(1L);
        // 判断用户信息是否为空
        Assert.assertNotNull(user);
    }
    
 // REST测试模板，Spring Boot自动提供
    @Autowired
    private TestRestTemplate restTemplate = null;

    // 测试获取用户功能
    @Test
    public void testGetUser() {
        // 请求当前启动的服务，请注意URI的缩写
        User user = this.restTemplate.getForObject("/user/{id}",
                User.class, 1L);
        Assert.assertNotNull(user);
    }
    
    @MockBean
    private ProductService productService = null;

    @Test
    public void testGetProduct() {
        // 构建虚拟对象
        Product mockProduct = new Product();
        mockProduct.setId(1L);
        mockProduct.setProductName("product_name_" + 1);
        mockProduct.setNote("note_" + 1);
        // 指定Mock Bean方法和参数
        BDDMockito.given(this.productService.getProduct(1L))
                // 指定返回的虚拟对象
                .willReturn(mockProduct);
        // 进行Mock测试
        Product product = productService.getProduct(1L);
        Assert.assertTrue(product.getId() == 1L);
    }

}
