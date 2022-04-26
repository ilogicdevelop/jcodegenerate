package com.xzh.codeGenerate.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;

/**
 * Jaxb2工具类
 * @author		zhuc
 * @create		2013-3-29 下午2:40:14
 */
public class JaxbUtil {

	/**
	 * JavaBean转换成xml
	 * 默认编码UTF-8
	 * @param obj
	 * @param writer
	 * @return 
	 */
	public static String convertToXml(Object obj) {
		return convertToXml(obj, "UTF-8");
	}

	/**
	 * JavaBean转换成xml
	 * @param obj
	 * @param encoding 
	 * @return 
	 */
	public static String convertToXml(Object obj, String encoding) {
		String result = null;
		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
			StringWriter writer = new StringWriter();
			marshaller.marshal(obj, writer);
			result = writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * xml转换成JavaBean
	 * @param xml
	 * @param c
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T> T converyToJavaBean(String xml, Class<T> c) {
		T t = null;
		try {
			JAXBContext context = JAXBContext.newInstance(c);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			t = (T) unmarshaller.unmarshal(new StringReader(xml));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return t;
	}
	
	public static String getXmlFromFile(String filepath){
		StringBuilder	sb	=	new StringBuilder();
		if(StringUtils.isBlank(filepath))
			return "filepath is empty";
		File file	=	new File(filepath);
		if(!file.exists())
			return "file not exist";
		BufferedReader	bufferreader	=	null;
		FileInputStream	fis	=	null;
		try {
			fis	=	new FileInputStream(filepath);
			InputStreamReader	insreader	=	new InputStreamReader(fis);
			bufferreader	=	new BufferedReader(insreader);
			String tmp	=	StringUtils.EMPTY;
			String separator = System.getProperty("line.separator");
			while((tmp	=	bufferreader.readLine())!=null){
				sb.append(tmp);
				sb.append(separator);
			}
			bufferreader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
	public static void main(String[] args) {
		try {
			FileInputStream	fis	=	new FileInputStream("e:\\config.xml");
			StringBuilder	sb	=	new StringBuilder();
			//带缓存的字节流
		/*	BufferedInputStream breader	=	new BufferedInputStream(fis);
			byte[] bytes	=	new byte[1024];
			int	i=	0;
			while((i =breader.read(bytes)) !=-1){
				sb.append(new String(bytes,0,i));
			}
			System.out.println(sb.toString());
			sb.delete(0, sb.length());*/
//			带缓存的字符流
			InputStreamReader	insreader	=	new InputStreamReader(fis);
			BufferedReader bufferreader	=	new BufferedReader(insreader);
			String tmp	=	StringUtils.EMPTY;
			while((tmp	=	bufferreader.readLine())!=null){
				sb.append(tmp);
			}
			System.out.println(sb.toString());
			bufferreader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
