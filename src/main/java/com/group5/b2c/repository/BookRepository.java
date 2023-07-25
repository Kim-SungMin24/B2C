package com.group5.b2c.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.group5.b2c.model.Book;
import com.group5.b2c.model.Member;

public interface BookRepository extends JpaRepository<Book, Long> {
	List<Book> findByMemberid(Member member);
	List<Book> findByTitleContainingOrAuthorContainingOrCategoryContaining(String title, String author, String category);
	
	
	public Page<Book> findByTitleContaining(String word, Pageable pageable);
	public Page<Book> findByContentContaining(String word, Pageable pageable);
	
	@Query(value = "select count(*) from b2c where title like CONCAT('%',:word,'%')" , nativeQuery = true)
	public long cntTitleSearch(@Param("word") String word);
	@Query(value = "select count(*) from b2c where content like CONCAT('%',:word,'%')" , nativeQuery = true)
	public long cntContentSearch(@Param("word") String word);
	
	//07 26 paging
	//Page<Book> findByMemberOrderByIdDesc(Member member, Pageable pageable);
	  Page<Book> findBybookid(final Long bookid, final PageRequest pageRequest);




}
