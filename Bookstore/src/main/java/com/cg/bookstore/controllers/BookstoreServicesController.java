package com.cg.bookstore.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.cg.bookstore.beans.Address;
import com.cg.bookstore.beans.Admin;
import com.cg.bookstore.beans.Book;
import com.cg.bookstore.beans.Category;
import com.cg.bookstore.beans.Customer;
import com.cg.bookstore.beans.OrderItem;
import com.cg.bookstore.exceptions.AdminDetailsNotFoundException;
import com.cg.bookstore.exceptions.BookDetailsNotFoundException;
import com.cg.bookstore.exceptions.CustomerDetailsNotFoundException;
import com.cg.bookstore.exceptions.InvalidUserDetailsException;
import com.cg.bookstore.services.BookstoreServices;
@CrossOrigin(origins="http://localhost:4200")
@Controller
public class BookstoreServicesController {
	@Autowired
	BookstoreServices bookstoreServices;
	@RequestMapping(value= {"/sayHello"}, method=RequestMethod.GET)
	public ResponseEntity<String> sayHello(){
		return new ResponseEntity<String>("Hello World To all From RESTImpl",HttpStatus.OK);
	}
	@RequestMapping(value= {"/acceptCustomerDetails"}, method=RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> acceptCustomerDetails(@ModelAttribute Customer customer){
		customer = bookstoreServices.acceptCustomerDetails(customer);
		return new ResponseEntity<>("Customer Details successfully added. \n Customer Id : "+customer.getCustomerEmailId(),HttpStatus.OK);
	}
	@RequestMapping(value= {"/acceptBookDetails"}, method=RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> acceptBookDetails(@ModelAttribute Book book){
		book = bookstoreServices.acceptBookDetails(book);
		return new ResponseEntity<>("Book Details successfully added. \n Book Id : "+book.getBookIsbn(),HttpStatus.OK);
	}
	@RequestMapping(value= {"/acceptAdminDetails"}, method=RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> acceptAdminDetails(@ModelAttribute Admin admin){
		admin = bookstoreServices.acceptAdminDetails(admin);
		return new ResponseEntity<>("Admin Details successfully added. \n Admin Id : "+admin.getAdminEmailId(),HttpStatus.OK);
	}
	@RequestMapping(value= {"/addCategory"}, method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,headers="Accept=application/json")
	public ResponseEntity<String> addCategory(@RequestBody Category category){
		category = bookstoreServices.addCategory(category);
	
		return new ResponseEntity<>("Category successfully added. "+category,HttpStatus.OK);
	}
	@RequestMapping(value= {"/getBookDetails"}, method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Book> getBookDetailsRequestParam(@RequestParam long bookIsbn) throws BookDetailsNotFoundException{
		Book book = bookstoreServices.getBookDetails(bookIsbn);
		return new ResponseEntity<Book>(book,HttpStatus.OK);
	}
	@RequestMapping(value= {"/getCustomerDetails"}, method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> getCustomerDetailsRequestParam(@RequestParam String customerEmailId) throws CustomerDetailsNotFoundException{
		Customer customer = bookstoreServices.getCustomerDetails(customerEmailId);
		return new ResponseEntity<Customer>(customer,HttpStatus.OK);
	}
	@RequestMapping(value= {"/getOrderDetails"}, method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OrderItem> getOrderDetailsRequestParam(@RequestParam long orderId) throws CustomerDetailsNotFoundException{
		OrderItem order = bookstoreServices.getOrderDetails(orderId);
		return new ResponseEntity<OrderItem>(order,HttpStatus.OK);
	}
	@RequestMapping(value= {"/getCategoryDetails"}, method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Category> getCategoryDetailsRequestParam(@RequestParam int categoryId) throws CustomerDetailsNotFoundException{
		Category category = bookstoreServices.getCategoryDetails(categoryId);
		return new ResponseEntity<Category>(category,HttpStatus.OK);
	}
	@RequestMapping(value= {"/getAllCategoryDetails"}, method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Category>> getCategoryDetailsRequestParam() throws CustomerDetailsNotFoundException{
		return new ResponseEntity<List<Category>>( bookstoreServices.getAllCategoryDetails(),HttpStatus.OK);
	}
	@RequestMapping("/adminLogin")
	public ModelAndView adminLogin(@RequestParam String adminEmailId, String password, HttpSession session) throws AdminDetailsNotFoundException, InvalidUserDetailsException {
		if(bookstoreServices.adminLogin(adminEmailId, password)!=null) {
			session.setAttribute("emailId", adminEmailId);
		}
		else throw new AdminDetailsNotFoundException();
		return new ModelAndView("home", "admin", bookstoreServices.adminLogin(adminEmailId, password));
	}
	@RequestMapping("/customerLogin")
	public ModelAndView customerLogin(@RequestParam String customerEmailId, String password, HttpSession session) throws CustomerDetailsNotFoundException, InvalidUserDetailsException {
		if(bookstoreServices.customerLogin(customerEmailId, password)!=null) {
			session.setAttribute("emailId", customerEmailId);
		}
		else throw new CustomerDetailsNotFoundException();
		return new ModelAndView("home", "customer", bookstoreServices.customerLogin(customerEmailId, password));
	}
	@RequestMapping("/editProfile")
	public ModelAndView resetPasswordAction(@RequestParam String customerEmailId, Address customerAddress) throws CustomerDetailsNotFoundException{
		return new ModelAndView("EditProfile", "customer", bookstoreServices.editProfile(customerEmailId, customerAddress ));
	}
	@RequestMapping("/removeBook")
	public ModelAndView removeBookDetails(@RequestParam long bookIsbn, HttpSession session) throws BookDetailsNotFoundException {
		return new ModelAndView("RemoveBook", "successMessage", bookstoreServices.removeBookDetails(bookIsbn));
	}
}