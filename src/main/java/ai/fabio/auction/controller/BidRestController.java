package ai.fabio.auction.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.fabio.auction.model.Auctioneer;
import ai.fabio.auction.model.Bid;
import ai.fabio.auction.service.BidService;

@RestController
@RequestMapping("/bid-rest")
public class BidRestController {
	private final BidService bidService;
	
	public BidRestController(BidService bidService) {
		super();
		this.bidService = bidService;
	}
	
	
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
    public String list(Model model){
		Stream<Bid> allStream = StreamSupport.stream(bidService.findAll().spliterator(), false);
		Stream<Bid> allStream2 = StreamSupport.stream(bidService.findAll().spliterator(), false);
		List<Bid> sells = allStream.filter(p -> p.getType().equals("A"))
				.sorted(Comparator.comparingInt(Bid::getPrice))
				.collect(Collectors.toList());
		List<Bid> buys =  allStream2.filter(p -> p.getType().equals("B"))
				.sorted(Comparator.comparingInt(Bid::getPrice)
				.reversed())
				.collect(Collectors.toList());
		
		model.addAttribute("bids", bidService.findAll());
		model.addAttribute("buys",buys);
		model.addAttribute("sells", sells);
        return "list";
    }
	
	@RequestMapping(value="/match", produces = "application/json", method=RequestMethod.GET)
    public Map<String,Object> getMatch(Model model){
		Map<String,Object> map=new HashMap<>();
		map.put("success" , false);
		return map;
	}
	
	@RequestMapping(value="/match",  produces = "application/json", method=RequestMethod.POST)
    public Map<String,Object> postMatch(@RequestParam(name = "details") String details ){
		List <Float> clearingPrice = new ArrayList<Float>();
		Stream<Bid> allStream = StreamSupport.stream(bidService.findAll().spliterator(), false);
		Stream<Bid> allStream2 = StreamSupport.stream(bidService.findAll().spliterator(), false);
		Map<String,Object> map=new HashMap<>();

		List<Bid> sells = allStream.filter(p -> p.getType().equals("A"))
				.sorted(Comparator.comparingInt(Bid::getPrice))
				.collect(Collectors.toList());
		List<Bid> buys =  allStream2.filter(p -> p.getType().equals("B"))
				.sorted(Comparator.comparingInt(Bid::getPrice)
				.reversed())
				.collect(Collectors.toList());
		
		
		Auctioneer auc = new Auctioneer();
		auc.setListSells(sells);
		auc.setListBuys(buys);
		LinkedHashMap<Bid, Bid> ret = auc.match();
		
		buys.clear();
		sells.clear();
		
		ret.forEach((k, v) -> {
			clearingPrice.add( (((float)k.getPrice()+(float)v.getPrice())/2)); 
            sells.add(k);
            buys.add(v);
        });
		map.put("bids", bidService.findAll());
		map.put("buys", buys);
		map.put("sells", sells);
		map.put("clearingPrice", clearingPrice);
		map.put("residualA", auc.getResidualSell());
		map.put("residualB", auc.getResidualBuy());
		
		map.put("success", true);
		
		
        return map;
    }
}
