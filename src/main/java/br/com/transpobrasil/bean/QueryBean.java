package br.com.transpobrasil.bean;

import br.com.transpobrasil.model.Lancamento;
import br.com.transpobrasil.service.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@RequestScoped
public class QueryBean {

    @Autowired
    private LancamentoService lancamentoService;
    private List<Lancamento> lancamentos;
    private Double totalLancamentos;

    @PostConstruct
    public void init() {
        lancamentos = new ArrayList<>();
    }

    public void calculateTotalByMediaItemsGreaterThanOrEqual100() {
        totalLancamentos = lancamentoService.calculateTotalByMediaItemsGreaterThanOrEqual100();
    }

    public void findByTop10MaxValueItemAndDescStartA() {
        lancamentos = lancamentoService.findByTop10MaxValueItemAndDescStartA();
    }

    public void findByCountItemsGreaterThan10() {
        lancamentos = lancamentoService.findByCountItemsGreaterThan10();
    }

    public Double getTotalLancamentos() {
        return totalLancamentos;
    }

    public void setTotalLancamentos(Double totalLancamentos) {
        this.totalLancamentos = totalLancamentos;
    }

    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }

    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }
}
