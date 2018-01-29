package com.hripple.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();

	@RequestMapping("/login")
	public ModelAndView getLoginForm(@RequestParam(required = false) String authfailed, String logout) {
		String message = "";
		if (authfailed != null) {
			message = "Invalid username of password, try again !";
		} else if (logout != null) {
			message = "Logged Out successfully, login again to continue !";
		}
		return new ModelAndView("login", "message", message);
	}
	
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(RedirectAttributes redirectAttributes, ModelMap model, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/login.html?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
	
	}


	public static String randomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}
	@RequestMapping(value = { "/web" }, method = RequestMethod.GET)
	public ModelAndView web(HttpServletRequest request) {
		String message = "";
		message = "Login successfully";
		return new ModelAndView("dashboard", "message", message);
		
	}
	@RequestMapping("/")
	public ModelAndView home1() {
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Who We Are");
		model.setViewName("home");
		return model;
	}

	@RequestMapping("/home")
	public ModelAndView home() {
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Who We Are");
		model.setViewName("home");
		return model;
	}

	@RequestMapping("/whatwedo")
	public ModelAndView whatwedo() {
		ModelAndView model = new ModelAndView();
		model.addObject("title", "What We Do");
		model.setViewName("whatwedo");
		return model;
	}

	@RequestMapping("/whyus")
	public ModelAndView whyus() {
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Why Us");
		model.setViewName("whyus");
		return model;
	}

	@RequestMapping("/ourstory")
	public ModelAndView ourstory() {
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Our Story");
		model.setViewName("ourstory");
		return model;
	}

	@RequestMapping("/clientportfolio")
	public ModelAndView clientportfolio() {
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Client Portfolio");
		model.setViewName("clientportfolio");
		return model;
	}

	@RequestMapping("/blog")
	public ModelAndView blog() {
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Blog");
		model.setViewName("blog");
		return model;
	}

	@RequestMapping("/Complete_HR_Setup_for_Digital_Domain_India")
	public ModelAndView Complete_HR_Setup_for_Digital_Domain_India() {
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Blog");
		model.setViewName("Complete_HR_Setup_for_Digital_Domain_India");
		return model;
	}

	@RequestMapping("/Life_at_HRipple_Solutions")
	public ModelAndView Life_at_HRipple_Solutions() {
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Blog");
		model.setViewName("Life_at_HRipple_Solutions");
		return model;
	}

	static String getFileExtension(MultipartFile file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		else
			return "";
	}

	@RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST)
	@ResponseBody
	public boolean uploadFile(@RequestParam(value = "file") MultipartFile file,
			@RequestParam(value = "description") String description) {

		System.out.println(file.getName());
		System.out.println(description);

		return false;

	}

	@RequestMapping(value = "/echofile", method = RequestMethod.POST)
	public @ResponseBody String echoFile(MultipartHttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "section") String section, @RequestParam(value = "action") String action)
			throws Exception {

		MultipartFile multipartFile = request.getFile("file");
		Long size = multipartFile.getSize();
		String contentType = multipartFile.getContentType();
		InputStream stream = multipartFile.getInputStream();
		byte[] bytes = IOUtils.toByteArray(stream);

		HashMap<String, String> map = new HashMap<String, String>();
		// map.put("fileoriginalsize", size);
		map.put("contenttype", contentType);
		map.put("base64", "helloo");

		return "success";
	}

	/**
	 * Upload single file using Spring Controller
	 */
	@RequestMapping(value = "/uploadMultipleFile1", method = RequestMethod.POST)
	public @ResponseBody String uploadFileHandler(@RequestParam("name") String name,
			@RequestParam("file") MultipartFile file) {
		String fileName = null;
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();
				fileName = file.getName() + "_" + randomString(10) + "."
						+ FilenameUtils.getExtension(file.getOriginalFilename());
				;
				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				System.out.println("Server File Location=" + serverFile.getAbsolutePath());

				String body = "Hello,"
						+ "<br/><br/><p><b>This is an automated system email. please do not reply to this email</b></p>";

				final String username = "connect@hripplesolutions.com";
				final String password = "hripple123";

				Properties props = new Properties();
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.port", "587");

				Session session = Session.getInstance(props, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

				try {

					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress("connect@hripplesolutions.com"));
					message.setRecipients(Message.RecipientType.TO,
							InternetAddress.parse("connect@hripplesolutions.com"));
					message.setSubject("Testing Subject");
					message.setText("Dear Mail Crawler," + "\n\n No spam to my email, please!");

					Transport.send(message);

					System.out.println("Done");

				} catch (MessagingException e) {
					throw new RuntimeException(e);
				}

				return "You successfully uploaded file=" + fileName;
			} catch (Exception e) {
				return "You failed to upload " + fileName + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + fileName + " because the file was empty.";
		}
	}

	@RequestMapping(value = "/sendEmailWithResume", method = RequestMethod.POST)
	public @ResponseBody String sendEmailWithResume(MultipartHttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "name") String name, @RequestParam(value = "loc") String loc,
			@RequestParam(value = "add") String add, @RequestParam(value = "dob") String dob,
			@RequestParam(value = "co") String co, @RequestParam(value = "cd") String cd,
			@RequestParam(value = "le") String le, @RequestParam(value = "cn") String cn,
			@RequestParam(value = "cs") String cs, @RequestParam(value = "message") String message,
			@RequestParam(value = "email") String email) throws Exception {

		System.out.println("DOB" + dob);
		System.out.println("Conatct Number" + cn);
		System.out.println("Email" + email);

		MultipartFile multipartFile = request.getFile("file");
		/*
		 * Long size = multipartFile.getSize(); String contentType =
		 * multipartFile.getContentType();
		 */
		InputStream stream = multipartFile.getInputStream();

		byte[] bytes = IOUtils.toByteArray(stream);
		String fileName = null;

		String rootPath = System.getProperty("catalina.home");
		File dir = new File(rootPath + File.separator + "tmpFiles");
		if (!dir.exists())
			dir.mkdirs();

		fileName = multipartFile.getName() + "_" + randomString(10) + "."
				+ FilenameUtils.getExtension(multipartFile.getOriginalFilename());
		;
		// Create the file on server
		File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
		BufferedOutputStream stream1 = new BufferedOutputStream(new FileOutputStream(serverFile));
		stream1.write(bytes);
		stream1.close();

		System.out.println("Server File Location=" + serverFile.getAbsolutePath());

		String body = "<b>Hello</b>,"
				+ "<br/><br/><p><b>Note=This is a system generated email, hit send button to reach the inbox of HRipple Solutions! </b></p><p>Name:"
				+ name + "</p><p>Location:" + loc + "</p><p>Address:" + add + "</p><p>Dob:" + dob
				+ "</p><p>Current Organization:" + co + "</p><p>Current Designation:" + cd + "</p><p>Last Education:"
				+ le + "</p><p>Current Salary:" + cs + "</p><p>Conatct Number:" + cn + "</p><p>Email:" + email
				+ "</p><br><br><p><b>Warm regards</b>,<p><b>Team Hripple</b></p>";

		final String username = "connect@hripplesolutions.com";
		final String password = "hripple123";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			MimeMessage msg = new MimeMessage(session);
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress("connect@hripplesolutions.com", "NoReply"));

			msg.setReplyTo(InternetAddress.parse("connect@hripplesolutions.com", false));

			msg.setSubject("Resume from Hripple Website (Position Applied For)", "UTF-8");

			msg.setSentDate(new Date());

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("connect@hripplesolutions.com", false));

			// Create the message body part
			MimeBodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setContent(body, "text/html");

			// Create a multipart message for attachment
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Second part is attachment
			messageBodyPart = new MimeBodyPart();
			// String filename = "D:\\123\\sanket.pdf";
			DataSource source = new FileDataSource(serverFile.getAbsolutePath());
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(multipartFile.getOriginalFilename());
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			msg.setContent(multipart);

			// Send message
			Transport.send(msg);
			System.out.println("Email Sent successfully");

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		serverFile.deleteOnExit();
		return "success";
	}

}
