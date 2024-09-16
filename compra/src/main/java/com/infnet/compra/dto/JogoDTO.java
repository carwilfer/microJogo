package com.infnet.compra.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JogoDTO {
    private Long id;
    private double preco;

}
