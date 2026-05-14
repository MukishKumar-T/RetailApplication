package com.retail.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductQuantityDTO {
    private String name;
    private int quantity;
    private boolean add;

    public boolean getAdd(){
        return this.add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }
}
