package product.model;

public class ProductContent {
	
	private Integer number;
	private String content;
	private String fileName;
	
	public ProductContent(Integer number, String content) {
		this(number, content, "");
	}

	public ProductContent(Integer number, String content, String fileName) {
		this.number = number;
		this.content = content;
		this.fileName = fileName;
	}

	public Integer getNumber() {
		return number;
	}

	public String getContent() {
		return content;
	}
	
	public String getFileName() {
		return fileName;
	}


}
