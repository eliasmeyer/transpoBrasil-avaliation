package br.com.transpobrasil.bean;

import br.com.transpobrasil.helper.JSFHelper;
import br.com.transpobrasil.helper.LoggerHelper;
import br.com.transpobrasil.model.Item;
import br.com.transpobrasil.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import java.util.List;
import java.util.Objects;

@Named
@ViewScoped
public class ItemBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemBean.class);

    private List<Item> itemsPool;

    private String description;
    private Double value;

    private Item currentItem;
    private String editDescription;
    private Double editValue;

    @Autowired
    private ItemService itemService;
    
    @PostConstruct
    public void init() {
        LOGGER.debug("INIT Request findAll Item");
        itemsPool = itemService.findAll();
        LOGGER.debug("END Request findAll Item");
    }

    public void save() {
        LOGGER.info("INIT Request save Item");
        try {
            Item item = new Item();
            item.setDescription(getDescription());
            item.setValue(getValue());

            itemService.save(item);
            reset();
            itemsPool = itemService.findAll();
        } catch (Exception e) {
            LOGGER.error(LoggerHelper.ERROR, e);
        }
        LOGGER.info("END Request save Item");
    }

    public void update() {
        LOGGER.info("INIT Request update Item");
        try {
            currentItem.setDescription(editDescription);
            currentItem.setValue(editValue);
            itemService.update(currentItem);
            itemsPool = itemService.findAll();
            JSFHelper.getInstance().showMessageSuccess("Item atualizado com sucesso.");
        } catch (Exception e) {
            LOGGER.error(LoggerHelper.ERROR, e);
        }
        LOGGER.info("END Request update Item");
    }

    public void delete() {
        LOGGER.info("INIT Request delete Item current {}", currentItem);
        try {
            if (Objects.nonNull(currentItem)) {
                itemService.delete(currentItem);
            }
            itemsPool = itemService.findAll();

            JSFHelper.getInstance().showMessageSuccess("Item removido com sucesso");
            
            LOGGER.info("END Request delete Item");
        } catch (Exception e) {
            LOGGER.error(LoggerHelper.ERROR, e);
        }
    }

    public void reset() {
        setDescription(null);
        setValue(null);
    }

    public List<Item> getItemsPool() {
        return itemsPool;
    }

    public void setItemsPool(List<Item> itemsPool) {
        this.itemsPool = itemsPool;
    }

    public void setCurrentItem(Item currentItem) {
        LOGGER.debug("INIT Setting currentItem {}", currentItem);
        this.currentItem = currentItem;
        this.editDescription = currentItem.getDescription();
        this.editValue = currentItem.getValue();
        LOGGER.debug("END Setting currentItem");
    }

    public Item getCurrentItem() {
        return this.currentItem;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEditDescription() {
        return editDescription;
    }

    public void setEditDescription(String editDescription) {
        this.editDescription = editDescription;
    }

    public Double getEditValue() {
        return editValue;
    }

    public void setEditValue(Double editValue) {
        this.editValue = editValue;
    }

    public ItemService getItemService() {
        return itemService;
    }

    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }
}
