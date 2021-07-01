package br.com.leandrocolevati.restapisimu.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.leandrocolevati.restapisimu.model.Netflix;
import br.com.leandrocolevati.restapisimu.persistence.NetflixDao;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "*")
public class NetflixServices {
	
	@Autowired
	NetflixDao nDao;
	
//	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/netflix")
	public List<Netflix> getNetflixes() throws IOException {
		return nDao.getNetflixes();
	}

//	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/netflix/{id}")
	public ResponseEntity<Netflix> getNetflix(@PathVariable(value = "id") String id) {
		try {
			return ResponseEntity.ok().body(nDao.getNetflix(Integer.parseInt(id)));
		} catch (NumberFormatException | IOException e) {
			return ResponseEntity.badRequest().build();
		}		
	}
	
//	@CrossOrigin(origins = "http://localhost:8080")
	@PostMapping("/netflix")
	public ResponseEntity<String> saveNetflix(@Valid @RequestBody Netflix n) {
		boolean inserted = nDao.insertNetflix(n);
		if (inserted) {
			return ResponseEntity.ok().body("Registro inserido");
		} else {
			return ResponseEntity.badRequest().build();
		}		
		
	}

}