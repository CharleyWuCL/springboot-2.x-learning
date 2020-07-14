package com.springboot.chapter5.pojo;

//
//@Entity(name = "transactionRecord")
//@Table(name = "t_transaction_record")
public class TransactionRecord {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//	@Column(name = "product_name")
	private String productName;

//	@Column(name = "quantity")
	private int quantity;

//	@Column(name = "amount")
	private double amount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
