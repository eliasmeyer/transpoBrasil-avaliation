package br.com.transpobrasil.service;

import br.com.transpobrasil.model.Item;
import br.com.transpobrasil.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public List<Item> findAll() {
        List<Item> itemEntities = new ArrayList<>();
        itemRepository.findAll().forEach(itemEntities::add);
        return itemEntities;
    }

    public void update(Item item) {
        itemRepository.save(item);
    }

    public void delete(Item item) {
        itemRepository.delete(item);
    }
    
    public void save(Item item){
        itemRepository.save(item);
    }
}
