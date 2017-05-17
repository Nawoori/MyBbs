package com.nawoori.mybbs.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.util.ArrayList;

public class BbsLoader {
	final String DATABASE_DIR = "c:/temp/mybbs/";
	final String DATABASE_NAME = "mybbs.db";
	final String COLUMN_SEPERATOR = "@@"; // ��ĭ ����
	final String RECORD_SEPERATOR = "\r\n"; // ���� ����
	/**
	 * ����ҿ��� �����͸� �о��
	 * @return ��ȯ���� ���� ����
	 */
	public ArrayList<Bbs> read(){
		ArrayList<Bbs> result = new ArrayList<>();
		
		// TODO
		// 1. ���� ���� ���� �˻�
		File database = new File(DATABASE_DIR + DATABASE_NAME);
		if(database.exists()){
			// 2. ������ ������ �о �ٴ����� Bbs �� �����ϰ�
			// 2.1 ���Ͽ� ���븦 �ȾƼ� ���� �غ� �Ѵ�.
			BufferedReader br;
			try {
				br = new BufferedReader(          // 3. ���۸� �����(�ѹ��� �뷮�� �����͸� �о���δ�) - ���ְ� ������ �����͸� �д´�
						new InputStreamReader(    // 2. ����Ŭ���� �����ش� (�ΰ����� ����). ĳ���ͼ��� �������ִ� ����
								new FileInputStream(database),"UTF-8") // 1. ��Ʈ�� ���� - �������� �����ؼ� �ѱ��ھ� �о���δ�.
						);
				String temp = "";
				while( (temp = br.readLine()) != null ){ // �ٴ����� ������ �о�ͼ�
					String bbsTemp[] = temp.split(COLUMN_SEPERATOR);
					// Bbs �� ���
					Bbs bbs = new Bbs();
					bbs.setId(Long.parseLong(bbsTemp[0]));
					bbs.setTitle(bbsTemp[1]);
					bbs.setAuthor(bbsTemp[2]);
					bbs.setDate(bbsTemp[3]);
					bbs.setView(Integer.parseInt(bbsTemp[4]));
					bbs.setContent(bbsTemp[5]);
					// �ϼ��� Bbs �� result �� add �Ѵ�. (�ݺ�)
					result.add(bbs);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	/**
	 * ����ҿ� �����͸� ����
	 * @param bbs
	 * @throws  
	 */
	public void write(Bbs bbs) {
		// bbs�� �ִ� �����͸� �ؽ�Ʈ ���Ͽ� �����Ѵ�.
		// 1. ���丮�� �ִ��� �˻��ϰ� ������ ����
		File dir = new File(DATABASE_DIR);
		if(!dir.exists()){
			dir.mkdirs(); // �˻��� ��λ��� ��� ���丮�� �������ش�
		}
		// 1.1 ������ �ִ��� �˻��ϰ� ������ ����
		File database = new File(DATABASE_DIR + DATABASE_NAME);
		if(!database.exists()){
			try {
				database.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// 2. bbs �� ������ database ���Ͽ� ������ �Ѵ�.
		// 2.1 ���� �����ϴ� �������� ������ �����ؾ� �Ѵ�.
		// 2.1.1 �����ڸ� ���� ������ �ξ�� �Ѵ�.
		
		// 2.2 bbs �� ������ Ⱦ���� ��ģ��
		String oneData = bbs.getId() + COLUMN_SEPERATOR 
				+ bbs.getTitle() + COLUMN_SEPERATOR
				+ bbs.getAuthor() + COLUMN_SEPERATOR
				+ bbs.getDate() + COLUMN_SEPERATOR
				+ bbs.getView() + COLUMN_SEPERATOR
				+ bbs.getContent() + RECORD_SEPERATOR;
		
		// 3. Ⱦ���� ������ �����͸� ������ ���� �������ٿ� �����Ѵ�.
		// 3.1 FileWriter ��ü�� ����ؼ� ���� �����Ϳ� ��� ���ؾ���.
		try {
			//FileWriter writer = new FileWriter(database, true); // �ι�° ���ڰ� append �� ����Ұ����� ����
			Writer writer = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(database,true),"UTF-8"));
			writer.append(oneData); // 
			writer.close(); // ! �ʼ�
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}