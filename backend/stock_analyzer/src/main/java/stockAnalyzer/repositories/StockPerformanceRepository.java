package stockAnalyzer.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import stockAnalyzer.model.StockPerformance;

public interface StockPerformanceRepository extends JpaRepository<StockPerformance, Long> {

    StockPerformance findByTickerAndDate(String ticker, LocalDate date);

    List<StockPerformance> findAllByTickerAndDateBetween(String ticker, LocalDate startDate, LocalDate endDate);
    
//    List<StockPerformance> findByTickerAndDateBetween(String ticker, LocalDate startDate, LocalDate endDate);
}
