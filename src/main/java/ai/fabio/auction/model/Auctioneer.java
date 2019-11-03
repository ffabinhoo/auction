package ai.fabio.auction.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Auctioneer {

	List<Bid> listSells = new ArrayList<Bid>();

	List<Bid> listBuys = new ArrayList<Bid>();

	List<Bid> residualBuy = new ArrayList<Bid>();
	
	List<Bid> residualSell = new ArrayList<Bid>();
	
	public List<Bid> getResidualBuy() {
		return residualBuy;
	}

	public void setResidualBuy(List<Bid> residualBuy) {
		this.residualBuy = residualBuy;
	}

	public List<Bid> getResidualSell() {
		return residualSell;
	}

	public void setResidualSell(List<Bid> residualSell) {
		this.residualSell = residualSell;
	}

	public List<Bid> getListSells() {
		return listSells;
	}

	public void setListSells(List<Bid> listSells) {
		this.listSells = listSells;
	}

	public List<Bid> getListBuys() {
		return listBuys;
	}

	public void setListBuys(List<Bid> listBuys) {
		this.listBuys = listBuys;
	}

	public LinkedHashMap<Bid, Bid> match() {
		int j = 0;
		int i = 0;
		int diff = 0;
		int qtdBuy = 0;
		int qtdSell = 0;
		int priceSell = 0;
		int priceBuy = 0;
		LinkedHashMap<Bid, Bid> matchs = new LinkedHashMap<>();
		Bid bidMatch = new Bid();

		while (listSells.size() > 0 && listBuys.size() > 0) {
			qtdSell = listSells.get(i).getQuantity();
			qtdBuy = listBuys.get(j).getQuantity();
			priceSell = listSells.get(i).getPrice();
			priceBuy = listBuys.get(i).getPrice();
			if (priceBuy < priceSell) {
				residualBuy.add(listBuys.get(i));
				residualSell.add(listSells.get(i));
				listBuys.remove(i);
				listSells.remove(i);
			} else {
				if (qtdBuy >= qtdSell) {
					diff = qtdBuy - qtdSell;
					listBuys.get(i).setQuantity(qtdSell);
					bidMatch = new Bid(listBuys.get(i).getBidid(), listBuys.get(i).getQuantity(),
							listBuys.get(i).getPrice(), listBuys.get(i).getType(), listBuys.get(i).getTrader(),
							listBuys.get(i).getProduct());
					matchs.put(listSells.get(i), bidMatch);
					listSells.remove(i);
					listBuys.get(i).setQuantity(diff);
				} else {
					diff = qtdSell - qtdBuy;
					listSells.get(i).setQuantity(qtdSell);
					bidMatch = new Bid(listSells.get(i).getBidid(), qtdBuy, listSells.get(i).getPrice(),
							listSells.get(i).getType(), listSells.get(i).getTrader(), listSells.get(i).getProduct());
					matchs.put(bidMatch, listBuys.get(i));
					listBuys.remove(i);
					listSells.get(i).setQuantity(diff);
				}
			}
		}
		if (listSells.size()>0) {
			residualSell.addAll(listSells);
		}
		if (listBuys.size()>0) {
			residualBuy.addAll(listBuys);
		}
		return matchs;
	}
}
