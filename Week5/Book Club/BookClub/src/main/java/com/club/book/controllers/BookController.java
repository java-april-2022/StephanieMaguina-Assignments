package com.club.book.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.club.book.models.Book;
import com.club.book.services.BookService;
@Controller
public class BookController {
	@Autowired
	private BookService bookService;

	@GetMapping("/book/new")
	public String newBook(HttpSession session, @ModelAttribute("newBook") Book book) {
		if(session.getAttribute("loggedInUser")== null) {
		return "dashboard.jsp";
		}
		else {
		return "new.jsp";
	}
}
	@PostMapping("/book/create")
	public String createBook(@Valid @ModelAttribute("newBook") Book book, BindingResult result) {
		if(result.hasErrors()) {
			return"new.jsp";
		}
		else {
			bookService.createBook(book);
			return "redirect:/dashboard";
		}
	}
	@GetMapping("/book/details/{id}")
	public String viewBook(@PathVariable Long id, Model model, HttpSession session) {
		if(session.getAttribute("loggedInUser")!= null) {
		Book book= bookService.getOneBook(id);
		model.addAttribute("book", book);
		return "details.jsp";
	}
		else {
			return "redirect:/";
		}
	}
	@GetMapping("/book/edit/{id}")
	public String editBook(@PathVariable Long id, HttpSession session, @ModelAttribute("editBook") Book book, Model model) {
		if(session.getAttribute("loggedInUser")!= null) {
		return "dashboard.jsp";
		}
		else {
			Book editBook = bookService.getOneBook(id);
			model.addAttribute("editBook", editBook);
		return "edit.jsp";
		}
	}
	@PutMapping("/book/update/{id}")
	public String updateBook(@PathVariable Long id, @Valid @ModelAttribute("editBook") Book book, HttpSession session) {
		if(session.getAttribute("loggedInUser")!= null) {
			return "dashboard.jsp";
		}
		else {
		bookService.updateBook(book);
		return "redirect:/dashboard";
	}
}
}