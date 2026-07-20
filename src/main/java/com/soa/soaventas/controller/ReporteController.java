package com.soa.soaventas.controller;

import com.soa.soaventas.dto.response.ApiResponse;
import com.soa.soaventas.dto.response.ReporteVentasResponse;
import com.soa.soaventas.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reportes")
@RequiredArgsConstructor
@Tag(name = "Reportes", description = "API para reportes y estadísticas")
public class ReporteController {

    private final PedidoService pedidoService;

    @GetMapping("/ventas/dia")
    @Operation(summary = "Reporte de ventas del día")
    public ResponseEntity<ApiResponse<ReporteVentasResponse>> reporteVentasDia(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        ReporteVentasResponse response = pedidoService.obtenerResumenVentasPorFecha(fecha);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/ventas/semana")
    @Operation(summary = "Reporte de ventas de la semana")
    public ResponseEntity<ApiResponse<List<ReporteVentasResponse>>> reporteVentasSemana(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio) {
        List<ReporteVentasResponse> response = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            response.add(pedidoService.obtenerResumenVentasPorFecha(inicio.plusDays(i)));
        }
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/ventas/mes")
    @Operation(summary = "Reporte de ventas del mes")
    public ResponseEntity<ApiResponse<List<ReporteVentasResponse>>> reporteVentasMes(
            @RequestParam int año, @RequestParam int mes) {
        YearMonth yearMonth = YearMonth.of(año, mes);
        List<ReporteVentasResponse> response = new ArrayList<>();
        for (int dia = 1; dia <= yearMonth.lengthOfMonth(); dia++) {
            LocalDate fecha = LocalDate.of(año, mes, dia);
            response.add(pedidoService.obtenerResumenVentasPorFecha(fecha));
        }
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
