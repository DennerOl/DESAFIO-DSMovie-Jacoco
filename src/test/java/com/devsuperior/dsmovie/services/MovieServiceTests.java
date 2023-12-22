package com.devsuperior.dsmovie.services;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.entities.MovieEntity;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import com.devsuperior.dsmovie.tests.MovieFactory;

@ExtendWith(SpringExtension.class)
public class MovieServiceTests {

	@InjectMocks
	private MovieService service;

	@Mock
	private MovieRepository repository;

	private String movieTitle;
	private MovieEntity movieEntity;
	private PageImpl<MovieEntity> page;

	@BeforeEach
	void setUp() throws Exception {

		movieEntity = MovieFactory.createMovieEntity();
		movieTitle = movieEntity.getTitle();
		page = new PageImpl<>(List.of(movieEntity));

		Mockito.when(repository.searchByTitle(any(), (Pageable) any())).thenReturn(page);

	}

	@Test
	public void findAllShouldReturnPagedMovieDTO() {

		Pageable pageable = PageRequest.of(0, 12);
		Page<MovieDTO> result = service.findAll(movieTitle, pageable);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getSize(), 1);
		Assertions.assertEquals(result.iterator().next().getTitle(), movieTitle);

	}
	/*
	 * @Test public void findByIdShouldReturnMovieDTOWhenIdExists() { }
	 * 
	 * @Test public void
	 * findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() { }
	 * 
	 * @Test public void insertShouldReturnMovieDTO() { }
	 * 
	 * @Test public void updateShouldReturnMovieDTOWhenIdExists() { }
	 * 
	 * @Test public void
	 * updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() { }
	 * 
	 * @Test public void deleteShouldDoNothingWhenIdExists() { }
	 * 
	 * @Test public void
	 * deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() { }
	 * 
	 * @Test public void deleteShouldThrowDatabaseExceptionWhenDependentId() { }
	 */
}
