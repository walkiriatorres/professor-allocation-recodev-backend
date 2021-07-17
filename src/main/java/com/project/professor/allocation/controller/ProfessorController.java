package com.project.professor.allocation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.professor.allocation.entity.Professor;
import com.project.professor.allocation.service.ProfessorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = {"Professor"})
@RestController
@RequestMapping(path = "/professors", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfessorController {
	
	private final ProfessorService professorService;
	
	public ProfessorController(ProfessorService professorService) {
		this.professorService = professorService;
	}
	
	@ApiResponse(code = 200, message = "OK")
	@ApiOperation(value = "Find a professor")
	@GetMapping
	//@Get
	public ResponseEntity<List<Professor>> findAll(@RequestParam(name = "partName", required = false) String partName){
			List<Professor> professors = professorService.findAll(partName);
			return new ResponseEntity<>(professors, HttpStatus.OK);			
	}
	
	@ApiResponses({
		@ApiResponse(code = 404, message = "NOT_FOUND"),
		@ApiResponse(code = 200, message = "OK")
	})
	@ApiOperation(value = "Find a professor by Id")
	@GetMapping(path = "/{professor_id}") //GET /professors/1
	public ResponseEntity<Professor> findById(@PathVariable(name = "professor_id") Long id){
		Professor professor = professorService.findById(id);
		if (professor == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(professor, HttpStatus.OK);
		}
	}
	
	@ApiResponses({
		@ApiResponse(code = 201, message = "CREATED"),
		@ApiResponse(code = 200, message = "BAD_REQUEST"),		
	})
	@ApiOperation(value = "Create a professor")
	@PostMapping (path = "/")
	public ResponseEntity<Professor> create(@RequestBody Professor professor) {
		try {
			Professor newProfessor = professorService.create(professor);
			return new ResponseEntity<>(newProfessor, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiResponses ({
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 404, message = "NOT FOUND"),
		@ApiResponse(code = 200, message = "BAD_REQUEST"),
	})
	@ApiOperation(value = "Update a professor")
	@PutMapping (path = "/{professor_id}")
	public ResponseEntity<Professor> update(@PathVariable(name = "professor_id")Long id, @RequestBody Professor professor) {
		professor.setId(id);
		try {
			Professor newProfessor = professorService.update(professor);
			if (newProfessor == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
			return new ResponseEntity<>(newProfessor, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
			
	}
	
	@ApiResponse(code = 204 , message = "NO_CONTENT")
	@ApiOperation(value = "Delete a professor")
	@DeleteMapping(path = "/{professor_id}")
	public ResponseEntity<Void> delete(@PathVariable(name = "professor_id")Long id) {
		professorService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@ApiResponse(code = 204 , message = "NO_CONTENT")
	@ApiOperation(value = "Delete all professors")
	@DeleteMapping (path = "/")
	public ResponseEntity<Void> deleteAll() {
		professorService.deleteAll();
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	
	
	
	
	
	
	
	
	

}
	
	
	

