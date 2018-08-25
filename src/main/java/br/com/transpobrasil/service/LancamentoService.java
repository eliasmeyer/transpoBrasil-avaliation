package br.com.transpobrasil.service;

import br.com.transpobrasil.model.Lancamento;
import br.com.transpobrasil.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;
    private static final int NUMBER_MAX_RECORDS = 10;

    @Transactional(readOnly = true)
    public List<Lancamento> findAll() {
        List<Lancamento> lancamentos = new ArrayList<>();
        lancamentoRepository.findAll().forEach(lancamentos::add);
        return lancamentos;
    }

    public void add(Lancamento lancamento) {
        lancamentoRepository.save(lancamento);
    }

    public void delele(Lancamento lancamento) {
        lancamentoRepository.delete(lancamento);
    }

    public void update(Lancamento lancamento) {
        lancamentoRepository.save(lancamento);
    }

    public Optional<Lancamento> findById(Long id) {
        return lancamentoRepository.findById(id);
    }

    //Requisito 10
    public Double calculateTotalByMediaItemsGreaterThanOrEqual100() {
        return lancamentoRepository.calculateTotalByMediaItemsGreaterThanOrEqual100();
    }

    //Requisito 11
    public List<Lancamento> findByTop10MaxValueItemAndDescStartA() {
        List<Lancamento> lancamentos = lancamentoRepository.findByTop10MaxValueItemAndDescStartA();

        if (Objects.nonNull(lancamentos) &&
                (lancamentos.size() > NUMBER_MAX_RECORDS)) {
            return lancamentos.stream().limit(NUMBER_MAX_RECORDS).collect(Collectors.toList());
        }
        return lancamentos;
    }

    //Requisito 12
    public List<Lancamento> findByCountItemsGreaterThan10() {
        return lancamentoRepository.findByCountItemsGreaterThan10();
    }
}
