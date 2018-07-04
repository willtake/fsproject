package com.txp.fs.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.txp.fs.common.DataServiceConstants;
import com.txp.fs.service.OutSideStockInfoService;


@RestController
@RequestMapping("/")
public class OutSideStockInfoController {

	@Autowired
	OutSideStockInfoService outSideStockInfoService;
		
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "1") int pageNumber) throws Exception {
		
		ModelAndView mav = new ModelAndView("bizinfo/list");
		
		PageRequest pageRequest = new PageRequest(pageNumber - 1, DataServiceConstants.MAX_PAGE_OFFST);
		       
		Map<String, Object> currentResults = outSideStockInfoService.getOutSideStockInfoList(pageRequest);
		
		int endIndex = 0;
		int totalPage = (Integer) currentResults.get("totalPage");
		
		if (totalPage == 0) {
			endIndex = totalPage = 1;
		} else {
			endIndex = (Integer) currentResults.get("endIndex");
			totalPage = (Integer) currentResults.get("totalPage");
		}
		
		model.addAttribute("cms", currentResults.get("cms"));
		model.addAttribute("beginIndex", currentResults.get("beginIndex"));
		model.addAttribute("endIndex", endIndex);
		model.addAttribute("currentIndex", currentResults.get("currentIndex"));
		model.addAttribute("totalPage", totalPage);

        mav.addObject(model);
		return mav;
	}

	
	
}
