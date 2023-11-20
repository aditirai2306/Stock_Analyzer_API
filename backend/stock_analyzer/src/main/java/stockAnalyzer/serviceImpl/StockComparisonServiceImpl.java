package stockAnalyzer.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stockAnalyzer.model.StockPerformance;
import stockAnalyzer.repositories.StockPerformanceRepository;
import stockAnalyzer.service.StockComparisonService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StockComparisonServiceImpl implements StockComparisonService {

    @Autowired
    private StockPerformanceRepository stockPerformanceRepository;

    @Override
    public Map<String, BigDecimal> compareDailyReturn(String ticker1, String ticker2, LocalDate startDate, LocalDate endDate) {
        List<StockPerformance> data1 = stockPerformanceRepository.findAllByTickerAndDateBetween(ticker1, startDate, endDate);
        List<StockPerformance> data2 = stockPerformanceRepository.findAllByTickerAndDateBetween(ticker2, startDate, endDate);

        BigDecimal dailyReturn1 = calculateDailyReturn(data1);
        BigDecimal dailyReturn2 = calculateDailyReturn(data2);

        Map<String, BigDecimal> response = new HashMap<>();
        response.put(ticker1, dailyReturn1);
        response.put(ticker2, dailyReturn2);

        return response;
    }

    private BigDecimal calculateDailyReturn(List<StockPerformance> data) {
        BigDecimal totalDailyReturn = BigDecimal.ZERO;
        int dataSize = data.size();

        if (dataSize == 0) {
            return BigDecimal.ZERO;
        }

        for (StockPerformance performance : data) {
            BigDecimal openPrice = performance.getOpenPrice();
            BigDecimal closePrice = performance.getClosePrice();
            BigDecimal dailyReturn = calculateReturnPercentage(openPrice, closePrice);
            totalDailyReturn = totalDailyReturn.add(dailyReturn);
        }

        return totalDailyReturn.divide(BigDecimal.valueOf(dataSize), 2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateReturnPercentage(BigDecimal openPrice, BigDecimal closePrice) {
        
        return closePrice.subtract(openPrice).divide(openPrice, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
    }
}