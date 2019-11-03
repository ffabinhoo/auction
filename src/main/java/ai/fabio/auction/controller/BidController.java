package ai.fabio.auction.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ai.fabio.auction.model.Auctioneer;
import ai.fabio.auction.model.Bid;
import ai.fabio.auction.model.Product;
import ai.fabio.auction.model.Trader;
import ai.fabio.auction.service.BidService;
import ai.fabio.auction.service.ProductService;
import ai.fabio.auction.service.TraderService;

@Controller
@RequestMapping("/bid")
public class BidController {
	private final TraderService traderService;
	private final ProductService productService;
	private final BidService bidService;
	
	public BidController(TraderService traderService, ProductService productService, BidService bidService) {
		super();
		this.traderService = traderService;
		this.productService = productService;
		this.bidService = bidService;
	}
	
	
	@RequestMapping(value="", method=RequestMethod.GET)
    public String index(Model model){
		model.addAttribute("bid", new Bid());
		model.addAttribute("traders", traderService.findAll());
		model.addAttribute("products", productService.findAll());
        return "bid";
    }
	
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
    public String create(Model model){
		return "bid";
    }
	

	@PostMapping("/create")
    public String greetingSubmit(@ModelAttribute Bid bid, Model model) {
		Auctioneer ac = new Auctioneer();
		Optional<Product> product = productService.findById(bid.getProduct().getProductid());
		Optional<Trader> trader = traderService.findById(bid.getTrader().getTraderid());
		
		bid.setTrader(trader.get());
		bid.setProduct(product.get());
		bidService.save(bid);
		model.addAttribute("bids", bidService.findAll());
		
        return "bid";
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
	
	@RequestMapping(value="/match", method=RequestMethod.GET)
    public String match(Model model){
		List <Float> clearingPrice = new ArrayList<Float>();
		Stream<Bid> allStream = StreamSupport.stream(bidService.findAll().spliterator(), false);
		Stream<Bid> allStream2 = StreamSupport.stream(bidService.findAll().spliterator(), false);
		List<Bid> sells = allStream.filter(p -> p.getType().equals("A"))
				.sorted(Comparator.comparingInt(Bid::getPrice))
				.collect(Collectors.toList());
		List<Bid> buys =  allStream2.filter(p -> p.getType().equals("B"))
				.sorted(Comparator.comparingInt(Bid::getPrice)
				.reversed())
				.collect(Collectors.toList());
		
		/*List<Bid> matchsSells = new ArrayList<Bid>();
		List<Bid> matchsBuys = new ArrayList<Bid>();*/
		
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
		model.addAttribute("bids", bidService.findAll());
		model.addAttribute("buys",buys);
		model.addAttribute("sells", sells);
		model.addAttribute("clearingPrice", clearingPrice);
		
		model.addAttribute("residualA", auc.getResidualSell());
		model.addAttribute("residualB", auc.getResidualBuy());
        return "match";
    }
}
