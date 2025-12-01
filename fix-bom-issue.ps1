# Save as fix-bom-issue.ps1 and run from project root

# 1. Create clean ClientDTO.java
@'
package com.pixzeleria.pixzeleria.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDTO {
    private String firstName;
    private String lastName;
    private String password;
}
'@ | Out-File -FilePath "src/main/java/com/pixzeleria/pixzeleria/dto/ClientDTO.java" -Encoding ASCII

# 2. Create clean IngredientDTO.java
@'
package com.pixzeleria.pixzeleria.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientDTO {
    private String name;
    private Integer stock;
}
'@ | Out-File -FilePath "src/main/java/com/pixzeleria/pixzeleria/dto/IngredientDTO.java" -Encoding ASCII

# 3. Create clean PizzaDTO.java
@'
package com.pixzeleria.pixzeleria.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class PizzaDTO {
    private String name;
    private List<Long> ingredientIds;
}
'@ | Out-File -FilePath "src/main/java/com/pixzeleria/pixzeleria/dto/PizzaDTO.java" -Encoding ASCII

Write-Host "Created clean DTO files without BOM!" -ForegroundColor Green