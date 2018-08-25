package br.com.transpobrasil.bean;

import br.com.transpobrasil.exception.InvalidDateRangeException;
import br.com.transpobrasil.helper.JSFHelper;
import br.com.transpobrasil.helper.LoggerHelper;
import br.com.transpobrasil.model.Item;
import br.com.transpobrasil.model.Lancamento;
import br.com.transpobrasil.service.ItemService;
import br.com.transpobrasil.service.LancamentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.RequestScoped;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@ManagedBean
@RequestScoped
public class RegisterLancamentoBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterLancamentoBean.class);
    private static final Long NEGATIVE = 1L;

    @Autowired
    private ItemService itemService;
    @Autowired
    private LancamentoService lancamentoService;

    private Long lancamentoId;
    private Lancamento lancamento;
    private List<Item> itemsPool;
    private Map<Item, Boolean> selectionItems;
    private Set<Item> currentlySelectedItems;
    
    @PostConstruct
    public void init() {
        itemsPool = itemService.findAll();
        loadLancamento(lancamentoId);
        initCurrentlySelectedItems();
        initSelectionItems();
    }

    @PreDestroy
    public void destroy() {
        itemsPool = null;
        selectionItems = null;
        currentlySelectedItems = null;
    }

    public void add() {
        LOGGER.info("INIT Request add lancamento");
        try {
            validate();
            lancamento.setItems(currentlySelectedItems);
            lancamento.setValueTotal(getTotal());
            LOGGER.debug("Registering lancamento {}", lancamento);
            lancamentoService.add(lancamento);
            init();
            JSFHelper.getInstance().showMessageSuccess("Lancamento cadastrado com sucesso");
            LOGGER.info("END Request add lancamento");
        } catch (InvalidDateRangeException e) {
            LOGGER.error(LoggerHelper.ERROR, e);
            JSFHelper.getInstance().addMessageErrorToComponent("dateFinal", "Data Inicial posterior a Data Final");
        } catch (Exception e) {
            LOGGER.error(LoggerHelper.ERROR, e);
            JSFHelper.getInstance().showMessageFatal("Erro interno. Por favor, entre em contato com a área de suporte.");
        }
    }

    private void validate() throws InvalidDateRangeException {
        if (lancamento.getDateInitial().after(lancamento.getDateFinal())) {
            throw new InvalidDateRangeException("Initial date is after date final.");
        }
    }

    public void onSelect() {
        currentlySelectedItems = selectionItems.entrySet().stream()
                .filter(Entry::getValue)
                .map(Entry::getKey)
                .collect(Collectors.toSet());
    }

    public void onDeselect(Item item) {
        if (Objects.nonNull(item)) {
            currentlySelectedItems.remove(item);
            selectionItems.put(item, Boolean.FALSE);
        }
    }

    private void loadLancamento(Long id) {
        if (Objects.isNull(id)) {
            lancamento = new Lancamento();
            return;
        }

        Optional optionalLancamento = lancamentoService.findById(id);
        if (!optionalLancamento.isPresent()) {
            lancamento = new Lancamento();
            return;
        }

        lancamento = (Lancamento) optionalLancamento.get();
    }

    private void initCurrentlySelectedItems() {
        if (Objects.isNull(lancamento.getItems()) ||
                (lancamento.getItems().isEmpty())) {
            currentlySelectedItems = new HashSet<>();
            return;
        }

        currentlySelectedItems = lancamento.getItems();
    }

    private void initSelectionItems() {
        selectionItems = itemsPool.stream().collect(Collectors.toMap(Function.identity(), item -> item.getId().equals(NEGATIVE)));
        currentlySelectedItems.forEach(item -> selectionItems.put(item, Boolean.TRUE));
    }


    public Double getTotal() {
        return currentlySelectedItems
                .stream()
                .mapToDouble(Item::getValue)
                .sum();
    }

    public Set<Item> getCurrentlySelectedItems() {
        return currentlySelectedItems;
    }

    public void setCurrentlySelectedItems(
            Set<Item> currentlySelectedItems) {
        this.currentlySelectedItems = currentlySelectedItems;
    }

    public List<Item> getItemsPool() {
        return itemsPool;
    }

    public void setItemsPool(List<Item> itemsPool) {
        this.itemsPool = itemsPool;
    }

    public Lancamento getLancamento() {
        return lancamento;
    }

    public void setLancamento(Lancamento lancamento) {
        this.lancamento = lancamento;
    }

    public Map<Item, Boolean> getSelectionItems() {
        return selectionItems;
    }

    public void setSelectionItems(
            Map<Item, Boolean> selectionItems) {
        this.selectionItems = selectionItems;
    }

    public Long getLancamentoId() {
        return lancamentoId;
    }

    public void setLancamentoId(Long lancamentoId) {
        this.lancamentoId = lancamentoId;
    }
}
