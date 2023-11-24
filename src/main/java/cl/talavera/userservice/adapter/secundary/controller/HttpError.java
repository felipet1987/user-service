package cl.talavera.userservice.adapter.secundary.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HttpError {
    private Instant timestamo;
    private int codigo;
    private String detail;
}
