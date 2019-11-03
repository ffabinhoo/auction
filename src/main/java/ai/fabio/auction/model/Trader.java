package ai.fabio.auction.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Trader")
public class Trader {

	@Id
	private Long traderid;

	private String name;

	@OneToMany(mappedBy = "trader")
	private List<Bid> bidList;

	

	public Long getTraderid() {
		return traderid;
	}

	public void setTraderid(Long traderid) {
		this.traderid = traderid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Bid> getBidList() {
		return bidList;
	}

	public void setBidList(List<Bid> bidList) {
		this.bidList = bidList;
	}

}
