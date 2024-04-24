package com.demo.test.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class AlarmClient {
	public static void main(String[] args) {
		Socket socket = null;
		try {
			socket = new Socket("127.0.0.1", 10004);
			
			Scanner sc = new Scanner(socket.getInputStream(),"UTF-8");
			while (sc.hasNextLine()) {
                System.out.println(sc.nextLine());
            }
            sc.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
