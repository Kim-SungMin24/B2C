package com.group5.b2c.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.group5.b2c.model.Book;
import com.group5.b2c.repository.BookRepository;
//import com.group5.b2c.repository.RentalRepository;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class BookService {
	private final BookRepository bookRepository;
	//private final RentalRepository rentalRepository;
	
	//0726 paging
	/*public Page<Book> findAll(Pageable pageable) {
		
		return bookRepository.findAll(pageable);
		
	}*/
	
	
	
	
	
	/*public void insert(Book book,Member member) {
		Rental rental = new Rental();
		rental.setRentid(member);
		rental.setBookid(book);
		bookRepository.save(book);
		rentalRepository.save(rental);
	}
	*/
	public void insert(Book book, String uploadFolder) {
		UUID uuid = UUID.randomUUID();
		MultipartFile f = book.getUpload();
		String uploadFileName = "";
		
		if(!f.isEmpty()) {
			uploadFileName = uuid.toString() + "_" + f.getOriginalFilename();
			File saveFile = new File(uploadFolder, uploadFileName);
			try {
				f.transferTo(saveFile);
				book.setImg(uploadFileName);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
		bookRepository.save(book);
	}
	
	
	public List<Book> list(String keyword){
		List<Book> list = null;
		if(keyword.equals("")) {
			list = bookRepository.findAll();
		}else {
			list=bookRepository.findByTitleContainingOrAuthorContainingOrCategoryContaining(keyword, keyword, keyword);
		}
		return list;
	}
	
	public void Insert(Book book) {
		UUID uuid = UUID.randomUUID();
		MultipartFile f = book.getUpload();
		String uploadFileName = "";
		
		if(!f.isEmpty()) {
			uploadFileName = uuid.toString() + "_" + f.getOriginalFilename();
			File saveFile = new File(uploadFileName);
			try {
				f.transferTo(saveFile);
				book.setImg(uploadFileName);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
		bookRepository.save(book);
	}
	
	@Transactional
	public Book bookdetail(long num) {
		Book b = bookRepository.findById(num).get();
		
		return b;
	
	
		
	}
	public Page<Book> findAll(String field, String word, Pageable pageable){
		Page<Book> lists=bookRepository.findAll(pageable);
		if(field.equals("title")) {
			lists = bookRepository.findByTitleContaining(word,pageable);
		}else if(field.equals("content")){
			lists = bookRepository.findByContentContaining(word,pageable);

		}
		return lists;
	}
	public long count(String field, String word) {
		long count = bookRepository.count();
		if(field.equals("title")) {
			count = bookRepository.cntTitleSearch(word);
		}else if(field.equals("content")) {
			count = bookRepository.cntContentSearch(word);

		}
		return count;
	}
	
	

}
	
	
	

