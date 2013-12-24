package org.cc.action.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.cc.entity.UserEntity;
import org.cc.service.UserService;
import org.cc.spring.security.DefineUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ModelDriven;

@Namespace("/user")
public class UserAction implements ModelDriven<UserEntity> {
	private static Logger logger = Logger.getLogger(UserAction.class);
	
	private UserEntity user;
	private UserEntity modifyUser = new UserEntity();
	private File hideFile;
	private String fileName;
	private String tempURL;//上传图片暂存url
	@Resource
	private UserService userService;
	
	@Action(value="index",results={
			@Result(name="index",location = "/WEB-INF/views/welcome.jsp")
	})
	public String index(){
		logger.error("it`s buliding and temp for jumpimg!!");
		return "index";
	}
	
	@Action(value = "account", results = {
			@Result(name = "account", location = "/WEB-INF/views/account/account.jsp")
		 }) 
	public String account(){
		logger.error("do this for temp");
		HttpServletRequest request = ServletActionContext.getRequest();
		Authentication   auth   =  SecurityContextHolder.getContext().getAuthentication();
        if(auth.getPrincipal()   instanceof   DefineUser)      
        {      
        	DefineUser userDetails = (DefineUser) auth.getPrincipal();
        	user = userService.getByLoginName(userDetails.getUsername());
			request.setAttribute("user",user);
        }
		String flag = request.getParameter("flag");
		request.setAttribute("flag", flag);
		return "account";
	}

	@Action(value="modify")
	public void modify(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		int flag = 1;
		if(modifyUser != null){
			user = userService.getByLoginName(modifyUser.getLoginName());
			user.setName(modifyUser.getName());
			user.setCellPhone(modifyUser.getCellPhone());
			user.setAddress(modifyUser.getAddress());
			userService.update(user);
			flag = 0;
		}
		try {
			response.getWriter().print(flag);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 头像设置
	 */
	@Action(value = "setPhoto", results = {
			@Result(name = "success", location = "/WEB-INF/views/account/setPhoto.jsp")
		 }) 
	public String setPhoto(){
		Authentication   auth   =  SecurityContextHolder.getContext().getAuthentication();
        if(auth.getPrincipal()   instanceof   DefineUser)      
        {      
        	DefineUser userDetails = (DefineUser) auth.getPrincipal();
        	user = userService.getByLoginName(userDetails.getUsername());
        }
		return "success";
	}
	
	/**
	 * 上传图片文件
	 * @throws IOException
	 * TODO 主要实现功能 一场处理等问题没有
	 */
	@Action(value="preUpload")
	public void preUpload() throws IOException{
		String path = ServletActionContext.getServletContext().getRealPath(File.separator);
		//删除上一次缓存的图片
		if(StringUtils.isNotBlank(tempURL)){
			tempURL = tempURL.replace("/", File.separator);
			String delPath = path +tempURL;
			FileUtils.forceDelete(new File(delPath));
		}
		//获取图片格式
		String type ="";
		if(fileName.indexOf(".")>0){
			type = fileName.substring(fileName.lastIndexOf("."),fileName.length());
		}
		String fileName = new Date().getTime()+""+type;
		if(!new File(path+"upload").exists()){
			new File(path+"upload").mkdirs();
		}
		String filename = path+"upload"+File.separator+fileName;  
        FileInputStream in = new FileInputStream(hideFile);
        FileOutputStream out = new FileOutputStream(filename);  
        byte[]b = new byte[1024];  
        int len = 0; 
        while((len=in.read(b))>0){  
            out.write(b,0,len);  
        }
        in.close();
        out.close();
        //返回文件路径
        HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		String virPath = "upload/"+fileName;
		try {
			response.getWriter().print(virPath);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 页面卸载时删除服务器上的图片
	 * @return
	 * @throws IOException 
	 */
	@Action(value="delTemp")
	public void delTemp() throws IOException{
		String path = ServletActionContext.getServletContext().getRealPath(File.separator);
		if(StringUtils.isNotBlank(tempURL)){
			tempURL = tempURL.replace("/", File.separator);
			String delPath = path +tempURL;
			FileUtils.forceDelete(new File(delPath));
		}
	}
	
	/**
	 * 保存头像的修改
	 * @return
	 */
	@Action(value = "savePhoto", results = {
			@Result(name = "success", location = "/user/setPhoto.do",type="redirect")
		 }) 
	public String savePhoto(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String x = request.getParameter("x");
		String y = request.getParameter("y");
		String tempURL = request.getParameter("tempURL");
		String destWidth = request.getParameter("destWidth");
		String destHeight = request.getParameter("destHeight");
		String finalWidth = request.getParameter("finalWidth");
		String finalHeight = request.getParameter("finalHeight");
		String path = ServletActionContext.getServletContext().getRealPath(File.separator);
		org.cc.utils.FileUtils.abscut(path+tempURL, Integer.parseInt(x), Integer.parseInt(y)
				, Integer.parseInt(destWidth), Integer.parseInt(destHeight),Integer.parseInt( finalWidth)
				, Integer.parseInt(finalHeight));
		Authentication   auth   =  SecurityContextHolder.getContext().getAuthentication();
        if(auth.getPrincipal()   instanceof   DefineUser)      
        {      
        	DefineUser userDetails = (DefineUser) auth.getPrincipal();
        	user = userService.getByLoginName(userDetails.getUsername());
        	user.setPicture(tempURL);
			userService.update(user);
        }
		return "success";
	}
	
	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public UserEntity getModel() {
		return modifyUser;
	}

	public File getHideFile() {
		return hideFile;
	}

	public void setHideFile(File hideFile) {
		this.hideFile = hideFile;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getTempURL() {
		return tempURL;
	}

	public void setTempURL(String tempURL) {
		this.tempURL = tempURL;
	}
}
