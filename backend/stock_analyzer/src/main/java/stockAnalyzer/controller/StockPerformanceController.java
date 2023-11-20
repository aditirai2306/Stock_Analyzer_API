package stockAnalyzer.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import stockAnalyzer.service.StockPerformanceService;

@RestController
@RequestMapping("/api/stock-performance")
public class StockPerformanceController {

    private final StockPerformanceService stockPerformanceService;

    @Autowired
    public StockPerformanceController(StockPerformanceService stockPerformanceService) {
        this.stockPerformanceService = stockPerformanceService;
    }

    @GetMapping("/daily-return")
    @CrossOrigin(origins = "http://localhost:3000/")
    public ResponseEntity<Double> getDailyReturn(@RequestParam String ticker,
                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        double dailyReturn = stockPerformanceService.calculateDailyReturn(ticker, date);
        return ResponseEntity.ok(dailyReturn);
    }

    @GetMapping("/average-performance")
    @CrossOrigin(origins = "http://localhost:3000/")
    public ResponseEntity<Double> getAveragePerformance(@RequestParam String ticker,
                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        double averagePerformance = stockPerformanceService.calculateAverageDailyPerformance(ticker, startDate, endDate);
        return ResponseEntity.ok(averagePerformance);
    }
}