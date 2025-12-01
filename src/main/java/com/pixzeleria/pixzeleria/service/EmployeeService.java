package com.pixzeleria.pixzeleria.service;

import com.pixzeleria.pixzeleria.model.user.EmployeeProfile;
import com.pixzeleria.pixzeleria.model.user.User;
import com.pixzeleria.pixzeleria.repository.EmployeeProfileRepository;
import com.pixzeleria.pixzeleria.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeProfileRepository employeeRepo;
    private final UserRepository userRepo;

    public EmployeeService(EmployeeProfileRepository employeeRepo, UserRepository userRepo) {
        this.employeeRepo = employeeRepo;
        this.userRepo = userRepo;
    }

    // Create and attach to a user
    public EmployeeProfile create(Long userId, String department) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        EmployeeProfile profile = new EmployeeProfile();
        profile.setUser(user);
        profile.setDepartment(department);
        return employeeRepo.save(profile);
    }

    // Get profile by user
    public Optional<EmployeeProfile> getByUser(Long userId) {
        return employeeRepo.findByUserId(userId);
    }

    // List all employees
    public List<EmployeeProfile> getAll() {
        return employeeRepo.findAll();
    }

    // Delete profile
    public void delete(Long id) {
        employeeRepo.deleteById(id);
    }
}
