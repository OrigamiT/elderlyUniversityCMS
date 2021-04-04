package cn.itechyou.cms.controller.admin;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.itechyou.cms.common.ResponseResult;
import cn.itechyou.cms.common.StateCodeEnum;
import cn.itechyou.cms.service.DatabaseService;

@Controller
@RequestMapping("admin/database")
public class DataBaseController {
	@Autowired
	private DatabaseService databaseService;
	
	/**
	 * 列表
	 */
	@RequestMapping({"","/showTables"})
	//@RequiresPermissions("5mqvsn91")
	public String list(Model model) {
		List<String> tables = databaseService.showTables();
		model.addAttribute("tables", tables);
		return "admin/database/list";
	}
	
	/**
	 * 备份
	 */
	@ResponseBody
	@RequestMapping(value = "/backup", method = RequestMethod.GET)
	//@RequiresPermissions("6g6w462l")
	public ResponseResult backup(Model model, String tableName) {
		int i = databaseService.backup(tableName);
		return ResponseResult.Factory.newInstance(Boolean.TRUE, StateCodeEnum.HTTP_SUCCESS.getCode(), null, StateCodeEnum.HTTP_SUCCESS.getDescription());
	}
	
	/**
	 * 结构
	 */
	@ResponseBody
	@RequestMapping(value = "/showStruct", method = RequestMethod.GET)
	//@RequiresPermissions("6g6w462l")
	public ResponseResult showStruct(Model model, String tableName) {
		String struct = databaseService.showStruct(tableName);
		return ResponseResult.Factory.newInstance(Boolean.TRUE, StateCodeEnum.HTTP_SUCCESS.getCode(), struct, StateCodeEnum.HTTP_SUCCESS.getDescription());
	}

	@RequestMapping({"/toRestore"})
	//@RequiresPermissions("5mqvsn91")
	public String toRestore(Model model) throws SQLException, FileNotFoundException {
		return "admin/database/restore";
	}
	
	/**
	 * 还原
	 */
	@ResponseBody
	@RequestMapping(value = "/restore", method = RequestMethod.GET)
	//@RequiresPermissions("6g6w462l")
	public ResponseResult restore(Model model, String fileNames) {
		int i = databaseService.restore(fileNames);
		return ResponseResult.Factory.newInstance(Boolean.TRUE, StateCodeEnum.HTTP_SUCCESS.getCode(), null, StateCodeEnum.HTTP_SUCCESS.getDescription());
	}
}
