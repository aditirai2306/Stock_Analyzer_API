package stockAnalyzer.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stockAnalyzer.model.StockPerformance;
import stockAnalyzer.repositories.StockPerformanceRepository;
import stockAnalyzer.service.StockPerformanceService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;


@Service
public class StockPerformanceServiceImpl implements StockPerformanceService {

    private final StockPerformanceRepository stockPerformanceRepository;

    @Autowired
    public StockPerformanceServiceImpl(StockPerformanceRepository stockPerformanceRepository) {
        this.stockPerformanceRepository = stockPerformanceRepository;
    }

    @Override
    public double calculateDailyReturn(String ticker, LocalDate date) {
        StockPerformance performance = stockPerformanceRepository.findByTickerAndDate(ticker, date);
        if (performance == null) {
            throw new IllegalArgumentException("No stock performance data found for the given ticker and date");
        }
        BigDecimal openPrice = performance.getOpenPrice();
        BigDecimal closePrice = performance.getClosePrice();

        // Calculating daily return using open and close prices
        // Formula: ((Close Price - Open Price) / Open Price) * 100
        BigDecimal priceDifference = closePrice.subtract(openPrice);
        BigDecimal dailyReturn = priceDifference.divide(openPrice, 4, RoundingMode.HALF_UP);
        return dailyReturn.multiply(BigDecimal.valueOf(100)).doubleValue();
    }

    @Override
    public double calculateAverageDailyPerformance(String ticker, LocalDate startDate, LocalDate endDate) {
        List<StockPerformance> performances = stockPerformanceRepository.findAllByTickerAndDateBetween(ticker, startDate, endDate);
        if (performances.isEmpty()) {
            throw new IllegalArgumentException("No stock performance data found for the given ticker and date range");
        }

        BigDecimal totalPerformance = BigDecimal.ZERO;
        for (StockPerformance performance : performances) {
            BigDecimal openPrice = performance.getOpenPrice();
            BigDecimal closePrice = performance.getClosePrice();
            BigDecimal priceDifference = closePrice.subtract(openPrice);
            BigDecimal dailyPerformance = priceDifference.divide(openPrice, 4, RoundingMode.HALF_UP);
            totalPerformance = totalPerformance.add(dailyPerformance);
        }

        BigDecimal averagePerformance = totalPerformance.divide(BigDecimal.valueOf(performances.size()), 4, RoundingMode.HALF_UP);
        return averagePerformance.multiply(BigDecimal.valueOf(100)).doubleValue();
    }
}
