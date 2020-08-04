package product.service;

import product.model.Product;
import product.model.ProductContent;

public class ProductData {
	
	private Product product;
	private ProductContent content;
	
	public ProductData(Product product, ProductContent content) {
		this.product = product;
		this.content = content;
	}

	public Product getProduct() {
		return product;
	}

	public String getContent() {
		return content.getContent();
	}
	
	public String getFileName() {
		return content.getFileName();
	}

}
