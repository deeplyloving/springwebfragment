package test.web1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.test.base.service.MenuExpoint;
import org.test.base.service.MenuService;
import org.test.service1.OrderService;
import org.test.web.core.BaseCtrl;


@Controller
public class OrderCtrl extends BaseCtrl implements MenuExpoint{

	@Autowired
	private OrderService service;
	@Autowired
	private MenuService menuservice;

	@RequestMapping("index")
	public String index(){
		menuservice.test();
		model.addAttribute("menus", getMenus());
		return "index";
	}
	
	@RequestMapping("del")
	public @ResponseBody String del(String id){
		service.delete(service.get(id));
		return "删除用户成功!";
	}


	@Override
	public List<Map<String, String>> registerMenu(
			List<Map<String, String>> menus) {
		Map<String,String> menu = new HashMap<String,String>();
		menu.put("name", "testweb1 index");
		menu.put("url", "/testweb1/index");
		menus.add(menu);
		menu = new HashMap<String,String>();
		menu.put("name", "testweb1 save");
		menu.put("url", "/testweb1/save");
		menus.add(menu);
		return menus;
	}
}
