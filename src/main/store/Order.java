package store;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Order {

	private static final int _Shipping = 15;
	private Customer customer;
	private Salesman salesman;
	private Date orderedOn;
	private String deliveryStreet;
	private String deliveryCity;
	private String deliveryCountry;
	private Set<OrderItem> items;

	public Order(Customer customer, Salesman salesman, String deliveryStreet, String deliveryCity, String deliveryCountry, Date orderedOn) {
		this.customer = customer;
		this.salesman = salesman;
		this.deliveryStreet = deliveryStreet;
		this.deliveryCity = deliveryCity;
		this.deliveryCountry = deliveryCountry;
		this.orderedOn = orderedOn;
		this.items = new HashSet<OrderItem>();
	}

	public Customer getCustomer() {
		return customer;
	}

	public Salesman getSalesman() {
		return salesman;
	}

	public Date getOrderedOn() {
		return orderedOn;
	}

	public String getDeliveryStreet() {
		return deliveryStreet;
	}

	public String getDeliveryCity() {
		return deliveryCity;
	}

	public String getDeliveryCountry() {
		return deliveryCountry;
	}

	public Set<OrderItem> getItems() {
		return items;
	}

	public float calculateTotalItems() {
		float totalItems = 0;
		
		for (OrderItem item : items) {
			float totalItem=0;
			float itemAmount = calculateItemAmount(item);
			
			if (isAccessorie(item)) 
				totalItem = itemAmount - calculateBooksDiscount(itemAmount);
			if (isBike(item)) 
				totalItem = itemAmount - discount20Porcent(itemAmount);	
			if (isCloathing(item)) 
				totalItem = itemAmount - calculateCloathingDiscount(item);

			totalItems += totalItem;
		}

		if (isDeliveryUSA()){
			// total=totalItems + tax + 0 shipping
			return totalItems + totalItems * 5 / 100;
		}

		// total=totalItemst + tax + 15 shipping
		return totalItems + totalItems * 5 / 100 + _Shipping;
	}

	private boolean isDeliveryUSA() {
		return this.deliveryCountry == "USA";
	}

	private float calculateCloathingDiscount(OrderItem item) {
		if (item.getQuantity() > 2) 
			return item.getProduct().getUnitPrice();
		return 0;
	}

	private boolean isCloathing(OrderItem item) {
		return item.getProduct().getCategory() == ProductCategory.Cloathing;
	}

	private float discount20Porcent(float itemAmount) {
		return itemAmount * 20 / 100;
	}

	private boolean isBike(OrderItem item) {
		return item.getProduct().getCategory() == ProductCategory.Bikes;
	}

	private float calculateBooksDiscount(float itemAmount) {
		if (itemAmount >= 100) 
			return itemAmount * 10 / 100;
		return 0;
	}

	private boolean isAccessorie(OrderItem item) {
		return item.getProduct().getCategory() == ProductCategory.Accessories;
	}

	private float calculateItemAmount(OrderItem item) {
		return item.getProduct().getUnitPrice() * item.getQuantity();
	}
}
