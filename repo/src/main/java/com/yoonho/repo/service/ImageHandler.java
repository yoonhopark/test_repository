package com.yoonho.repo.service;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

@Service
public class ImageHandler {
	private final int max_image_cnt = 4;
	private final int max_width_cnt = 2;
	private final int max_height_cnt = 2;
	
	private final int width = 300;
	private final int height = 300;
	private final int max_boundary_size = 10;
	
	public BufferedImage mergedImage(List<BufferedImage> image_list) throws Exception{
		BufferedImage mergedImage = null;
		try {
			mergedImage = new BufferedImage(width * max_width_cnt, height * max_height_cnt, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = mergedImage.createGraphics();
			int image_idx = 0;
			for(int w=0; w < max_width_cnt; w++) {
				for(int h=0; h < max_height_cnt; h++) {
					g.drawImage(image_list.get(image_idx++), (width*w), (height*h), null);
				}
			}
			g.dispose();
		}catch(Exception e) {
			
		}
		return mergedImage;
	}
	
	public BufferedImage getImage() throws Exception{
		List<BufferedImage> result_list = new ArrayList<BufferedImage>();
		String url = "C:\\Users\\USER\\git\\test_repository\\repo";
		String imgName = "compression.jpeg";
		File inputFile = new File(url, imgName);
		InputStream in = new FileInputStream(inputFile);
		BufferedImage img = ImageIO.read(in);
		for(int i=0; i<4; i++) {
			result_list.add(img);
		}
		
		return boundaryImage(mergedImage(changeColorImage(result_list)));
	}
		
	public BufferedImage boundaryImage(BufferedImage image) throws Exception{
		int full_boundary_size = max_boundary_size*2;
		
		int red_rgb = 0;
		int green_rgb = 0;
		int blue_rgb = 0;
		int boundary_rgb = 0;
		
		Color color = null;
		for(int h=0; h < height * max_height_cnt; h++) {
			for(int w=width - max_boundary_size; w < width + max_boundary_size; w++) {
				color = new Color(image.getRGB(w, h));
				
				red_rgb += color.getRed();
				green_rgb += color.getGreen();
				blue_rgb += color.getBlue();
			}
			red_rgb = red_rgb/full_boundary_size;
			green_rgb = green_rgb/full_boundary_size;
			blue_rgb = blue_rgb/full_boundary_size;
			boundary_rgb = new Color(red_rgb, green_rgb, blue_rgb).getRGB();
			
			for(int w=width-max_boundary_size; w < width+max_boundary_size; w++) {
				image.setRGB(w, h, boundary_rgb);
			}
			red_rgb = green_rgb = blue_rgb = boundary_rgb = 0;
		}
		
		for(int w=0; w < width * max_width_cnt; w++) {
			if(w < width - max_boundary_size || w >= width + max_boundary_size) {
				for(int h=height - max_boundary_size; h < height + max_boundary_size; h++) {
					color = new Color(image.getRGB(w, h));
					
					red_rgb += color.getRed();
					green_rgb += color.getGreen();
					blue_rgb += color.getBlue();
				}
				red_rgb = red_rgb/full_boundary_size;
				green_rgb = green_rgb/full_boundary_size;
				blue_rgb = blue_rgb/full_boundary_size;
				boundary_rgb = new Color(red_rgb, green_rgb, blue_rgb).getRGB();
				
				for(int h=height - max_boundary_size; h < height + max_boundary_size; h++) {
					image.setRGB(w, h, boundary_rgb);
				}
				red_rgb = green_rgb = blue_rgb = boundary_rgb = 0;  
			}
		}
		
		return image;
	}
	
	public List<BufferedImage> changeColorImage(List<BufferedImage> image_list) throws Exception{
		List<BufferedImage> change_list = new ArrayList<BufferedImage>();
		Random random = new Random();
		BufferedImage grayFilter = null;
		Graphics2D g = null;
		for(BufferedImage image : image_list) {
			if(random.nextBoolean()) {
				grayFilter = new BufferedImage(width, height, image.TYPE_BYTE_GRAY);
				g = grayFilter.createGraphics();
				g.drawImage(image, 0, 0, null);
				
				change_list.add(grayFilter);	
			}else {
				change_list.add(image);
			}
		}
		g.dispose();
		
		return change_list;
	}
}
