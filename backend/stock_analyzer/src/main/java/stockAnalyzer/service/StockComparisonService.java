package stockAnalyzer.service;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public interface StockComparisonService {

    Map<String, BigDecimal> compareDailyReturn(String ticker1, String ticker2, LocalDate startDate, LocalDate endDate);

}
