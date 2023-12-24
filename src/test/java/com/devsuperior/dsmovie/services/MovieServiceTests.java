package com.devsuperior.dsmovie.services;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.entities.MovieEntity;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import com.devsuperior.dsmovie.services.exceptions.DatabaseException;
import com.devsuperior.dsmovie.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dsmovie.tests.MovieFactory;

@ExtendWith(SpringExtension.class)
public class MovieServiceTests {

	@InjectMocks
	private MovieService service;

	@Mock
	private MovieRepository repository;

	private long existingMovieId, nonExistingMovieId, dependentMovieId;
	private String movieTitle;
	private MovieEntity movieEntity;
	private PageImpl<MovieEntity> page;
	private MovieDTO movieDTO;

	@BeforeEach
	void setUp() throws Exception {

		existingMovieId = 1L;
		nonExistingMovieId = 1000L;
		dependentMovieId = 3L;

		movieEntity = MovieFactory.createMovieEntity();
		movieTitle = movieEntity.getTitle();
		page = new PageImpl<>(List.of(movieEntity));

		movieDTO = new MovieDTO(movieEntity);

		Mockito.when(repository.searchByTitle(any(), (Pageable) any())).thenReturn(page);

		Mockito.when(repository.findById(existingMovieId)).thenReturn(Optional.of(movieEntity));
		Mockito.when(repository.findById(nonExistingMovieId)).thenReturn(Optional.empty());

		Mockito.when(repository.save(any())).thenReturn(movieEntity);
		
		Mockito.when(repository.getReferenceById(existingMovieId)).thenReturn(movieEntity);
		Mockito.when(repository.getReferenceById(nonExistingMovieId)).thenThrow(ResourceNotFoundException.class);
		
		Mockito.when(repository.existsById(existingMovieId)).thenReturn(true);
		Mockito.when(repository.existsById(nonExistingMovieId)).thenReturn(false);
		Mockito.when(repository.existsById(dependentMovieId)).thenReturn(true);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentMovieId);

	}

	@Test
	public void findAllShouldReturnPagedMovieDTO() {

		Pageable pageable = PageRequest.of(0, 12);
		Page<MovieDTO> result = service.findAll(movieTitle, pageable);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getSize(), 1);
		Assertions.assertEquals(result.iterator().next().getTitle(), movieTitle);

	}

	@Test
	public void findByIdShouldReturnMovieDTOWhenIdExists() {

		MovieDTO result = service.findById(existingMovieId);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingMovieId);
		Assertions.assertEquals(result.getTitle(), movieEntity.getTitle());
	}

	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingMovieId);
		});

	}

	@Test
	public void insertShouldReturnMovieDTO() {

		MovieDTO result = service.insert(movieDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), movieEntity.getId());
	}
	
	@Test 
	public void updateShouldReturnMovieDTOWhenIdExists() {
		
		MovieDTO result = service.update(existingMovieId, movieDTO);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingMovieId);
		Assertions.assertEquals(result.getTitle(), movieDTO.getTitle());
	}
	
	
	@Test 
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.update(nonExistingMovieId, movieDTO);
		});
		
	}
	
	@Test 
	public void deleteShouldDoNothingWhenIdExists() { 
		
		Assertions.assertDoesNotThrow(()-> {
			service.delete(existingMovieId);
					
		});		
	}
	@Test 
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingMovieId);
		});
		
	}

	  @Test 
	  public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
		  
		  Assertions.assertThrows(DatabaseException.class, () -> {
				service.delete(dependentMovieId);
			});
			
	  }
}
