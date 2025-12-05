package com.pixzeleria.pixzeleria.model.user;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class EmployeeProfile {
    @Id
    private Long id;
    private String department;

    @ManyToOne // un usuario puede tener perfil de employee o de client
    @JoinColumn(name = "user_id")
    private User user;
}
