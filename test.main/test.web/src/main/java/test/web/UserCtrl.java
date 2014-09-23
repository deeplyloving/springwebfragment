package test.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.test.base.service.MenuExpoint;
import org.test.base.service.MenuService;
import org.test.model.User;
import org.test.service.UserService;
import org.test.web.core.BaseCtrl;

@Controller
public class UserCtrl extends BaseCtrl implements MenuExpoint {

	@Autowired
	private UserService service;

	@Autowired
	private MenuService menuservice;

	@RequestMapping("index")
	public String index() {
		service.hello();
		model.addAttribute("menus", getMenus());
		menuservice.test();
		return "index";
	}

	@RequestMapping("save")
	public @ResponseBody String save() {
		service.saveOrUpdate(new User());
		return "保存用户成功!";
	}

	@RequestMapping("list")
	public @ResponseBody String list() {
		return service.list().toString();
	}

	@Override
	public List<Map<String, String>> registerMenu(
			List<Map<String, String>> menus) {
		Map<String,String> menu = new HashMap<String,String>();
		menu.put("name", "testweb0 index");
		menu.put("url", "/testweb0/index");
		menus.add(menu);
		menu = new HashMap<String,String>();
		menu.put("name", "testweb0 save");
		menu.put("url", "/testweb0/save");
		menus.add(menu);
		return menus;
	}
}
