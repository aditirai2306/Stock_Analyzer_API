package stockAnalyzer.service;

import java.time.LocalDate;

public interface StockPerformanceService {
	
	
    double calculateDailyReturn(String ticker, LocalDate date);

    double calculateAverageDailyPerformance(String ticker, LocalDate startDate, LocalDate endDate);
}
