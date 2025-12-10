package com.pixzeleria.pixzeleria.model.user;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ClientProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int loyaltyPoints;

    @ManyToOne // un usuario puede tener perfil de employee o de client
    @JoinColumn(name = "user_id")
    private User user;
}
