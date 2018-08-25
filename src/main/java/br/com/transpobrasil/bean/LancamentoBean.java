package br.com.transpobrasil.bean;

import br.com.transpobrasil.helper.JSFHelper;
import br.com.transpobrasil.helper.LoggerHelper;
import br.com.transpobrasil.model.Lancamento;
import br.com.transpobrasil.service.LancamentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import java.util.List;
import java.util.Objects;

@ManagedBean
@RequestScoped
public class LancamentoBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(LancamentoBean.class);

    private List<Lancamento> lancamentos;
    private Lancamento currentLancamento;

    @Autowired
    private LancamentoService lancamentoService;

    @PostConstruct
    public void init() {
        lancamentos = lancamentoService.findAll();
    }

    public void delete() {
        LOGGER.info("INIT Request delete Lancamento {}", currentLancamento);
        try {
            if (Objects.nonNull(currentLancamento)) {
                lancamentoService.delele(currentLancamento);
            }
            lancamentos = lancamentoService.findAll();
            JSFHelper.getInstance().showMessageSuccess("Lancamento removido com sucesso");

            LOGGER.info("END Request delete Lancamento");
        } catch (Exception e) {
            LOGGER.error(LoggerHelper.ERROR, e);
        }
    }

    public Lancamento getCurrentLancamento() {
        return currentLancamento;
    }

    public void setCurrentLancamento(Lancamento currentLancamento) {
        LOGGER.debug("INIT Setting currentLancamento {}", currentLancamento);
        this.currentLancamento = currentLancamento;

        LOGGER.debug("END Setting currentItem");
    }

    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }

    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }

}
