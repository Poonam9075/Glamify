package com.Glamify.dto;

import java.math.BigDecimal;

public class ServiceResponseDto {

    private Long serviceId;
    private String serviceName;
    private BigDecimal price;

    private Long categoryId;
    private String categoryName;

    // ✅ getters & setters

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

	public void setPrice(int price2) {
		// TODO Auto-generated method stub
		
	}
}