package br.com.transpobrasil.repository;

import br.com.transpobrasil.model.Lancamento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LancamentoRepository extends CrudRepository<Lancamento, Long> {
    
    //Query requisito 10
    @Query("SELECT SUM(la.valueTotal) FROM Lancamento la WHERE EXISTS( SELECT 1 FROM la.items i GROUP BY i HAVING AVG(i.value) >= 100) ")
    Double calculateTotalByMediaItemsGreaterThanOrEqual100();
    
    //Query requisito 11
    List<Lancamento> findByTop10MaxValueItemAndDescStartA();

    //Query requisito 12
    List<Lancamento> findByCountItemsGreaterThan10();

}
