package com.pixzeleria.pixzeleria.controller;

import com.pixzeleria.pixzeleria.model.user.EmployeeProfile;
import com.pixzeleria.pixzeleria.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/{userId}/profile")
    public ResponseEntity<EmployeeProfile> addEmployeeProfile(@PathVariable Long userId,
                                                              @RequestParam String department) {
        return ResponseEntity.ok(employeeService.create(userId, department));
    }

    @GetMapping("/{userId}/profile")
    public ResponseEntity<EmployeeProfile> getEmployeeProfile(@PathVariable Long userId) {
        return ResponseEntity.of(employeeService.getByUser(userId));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeProfile>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeProfile(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
