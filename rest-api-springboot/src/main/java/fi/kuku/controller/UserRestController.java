package fi.kuku.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import fi.kuku.dao.UserRepo;
import fi.kuku.entity.User;

@RestController
@RequestMapping("api/users")
public class UserRestController {
	
	@Autowired
	private UserRepo urepo;
	
	@GetMapping("/hello")
	public String hello() {
		return "Hello world";
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(
		@PathVariable("id") Integer id, 
		@RequestBody User user){
		user.setId(id);
		urepo.save(user);
		return ResponseEntity.ok(user);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable("id") Integer id){
		try {
			User ur= urepo.findById(id).get();
			urepo.delete(ur);
			return ResponseEntity.ok(ur);
			
		} catch (Exception e) {
			return ResponseEntity.status(404).body(null);
		}
	}
	
	@PostMapping
	public ResponseEntity<?> addUser(@RequestBody User user){
		urepo.save(user);
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Integer id) {
		try {
			User u1 = urepo.findById(id).get();
			return ResponseEntity.ok(u1);
		} catch (Exception e) {
			return ResponseEntity.status(400).body(id + " not found");
		}
		
	}
	
	@GetMapping
	public Iterable<User> getAllUsers(
			@RequestParam(name="_page", defaultValue = "1") Integer pageNumber, 
			@RequestParam(name="_limit", defaultValue = "15")Integer pageSize) {
		Pageable pa = PageRequest.of(pageNumber, pageSize);
		return urepo.findAll(pa).getContent();
	}

}
