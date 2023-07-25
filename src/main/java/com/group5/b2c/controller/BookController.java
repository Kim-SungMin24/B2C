package com.group5.b2c.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.group5.b2c.config.auth.PrincipalMember;
import com.group5.b2c.dto.BookFormDTO;
import com.group5.b2c.model.Book;

import com.group5.b2c.service.BookService;

import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/book/*")
@RequiredArgsConstructor
public class BookController {
	private final BookService bookService;
	
	
	//0726페이징
	@GetMapping ("list")
	public String list (Model model,
			@PageableDefault (size = 9, sort="num", direction = Direction.DESC) Pageable pageable,
			@RequestParam(required = false, defaultValue = "") String field,
			@RequestParam(required = false, defaultValue = "")String word) {
	Page<Book> lists = bookService.findAll (field, word, pageable);
	
	long count = bookService.count(field,word);
	model.addAttribute("bookList", lists);
	model.addAttribute("count", count);
	model.addAttribute("f", field);
	model.addAttribute("w", word);
	return "/book/list";
	
	
	}
		
	
	
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("insert")
	public String insert() {
		return "/book/insert";
	}
	
	@PostMapping("insert")
	public String insert(@Valid BookFormDTO bookFormDTO,BindingResult bindingResult,  @AuthenticationPrincipal PrincipalMember principalMember, HttpSession session) {
		if(bindingResult.hasErrors()) {
			return "/book/insert";
		}
		String uploadFolder = session.getServletContext().getRealPath("/") + "\\resource\\img";
		Book book = Book.createBook(bookFormDTO,principalMember.getMember());
		bookService.insert(book, uploadFolder);
		return "redirect:/book/list";
	}
	
	
	@GetMapping("list")
	public String list(Model model, @RequestParam(defaultValue = "") String keyword) {
		model.addAttribute("lists", bookService.list(keyword));
		return "/book/booklist";
	
	
}
	@GetMapping ("bookdetail/{num}")
	public String Detail(@PathVariable long num, Model model) {
		model.addAttribute("book",bookService.bookdetail(num));
		Book b = bookService.bookdetail(num);
		System.out.println(bookService.bookdetail(num).getTitle());
		System.out.println("username = " + b.getMemberid().getUsername());
		return "/book/bookdetail";
	}
}
