package com.abubakar.billingSoftware.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.abubakar.billingSoftware.io.ItemRequest;
import com.abubakar.billingSoftware.io.ItemResponse;

public interface ItemService {
    
    ItemResponse add(ItemRequest itemRequest,MultipartFile file);

    List<ItemResponse> fetchItems();

    void deleteItem(String itemId);
}
