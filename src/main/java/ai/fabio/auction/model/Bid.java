package ai.fabio.auction.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Bid")
public class Bid {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer bidid;

	private Integer quantity;

	private Integer price;

	private String type;
	
	private Integer reservedPrice;

	


	@ManyToOne
	@JoinColumn(name = "traderid", insertable = true, updatable = true)
	private Trader trader;

	@ManyToOne
	@JoinColumn(name = "productid", insertable = true, updatable = true)
	private Product product;


	public Integer getBidid() {
		return bidid;
	}

	public void setBidid(Integer bidid) {
		this.bidid = bidid;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Bid() {
		
	}

	public Bid(Integer bidid, Integer quantity, Integer price, String type, Trader trader, Product product) {
		super();
		this.bidid = bidid;
		this.quantity = quantity;
		this.price = price;
		this.type = type;
		this.trader = trader;
		this.product = product;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Trader getTrader() {
		return trader;
	}

	public void setTrader(Trader trader) {
		this.trader = trader;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public Integer getReservedPrice() {
		return reservedPrice;
	}

	public void setReservedPrice(Integer reservedPrice) {
		this.reservedPrice = reservedPrice;
	}

	
	@Override
	public String toString() {
		return "Bid [bidid=" + bidid + ", quantity=" + quantity + ", price=" + price + ", type=" + type + "]";
	}
}
