package stockAnalyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import stockAnalyzer.service.StockComparisonService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/stock-comparison")
public class StockComparisonController {

    @Autowired
    private StockComparisonService stockComparisonService;

    @GetMapping("/compare-daily-return")
    @CrossOrigin(origins = "http://localhost:3000/")
    public ResponseEntity<Map<String, BigDecimal>> compareDailyReturn(
            @RequestParam String ticker1,
            @RequestParam String ticker2,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        Map<String, BigDecimal> comparisonResult = stockComparisonService.compareDailyReturn(ticker1, ticker2, startDate, endDate);
        return ResponseEntity.ok(comparisonResult);
    }
}