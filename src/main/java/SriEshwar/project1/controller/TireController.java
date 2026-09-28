package SriEshwar.project1.controller;

import SriEshwar.project1.dto.TireRequestDTO;
import SriEshwar.project1.entity.Tire;
import SriEshwar.project1.service.TireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fleet/tires")
@CrossOrigin(origins = "*")
public class TireController {

    @Autowired
    private TireService tireService;

    @PostMapping
    public ResponseEntity<Tire> createTire(@RequestBody TireRequestDTO dto) {
        return new ResponseEntity<>(tireService.createTire(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Tire>> getAllTires() {
        return ResponseEntity.ok(tireService.getAllTires());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tire> getTireById(@PathVariable Long id) {
        return ResponseEntity.ok(tireService.getTireById(id));
    }
}
