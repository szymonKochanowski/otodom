package com.otodom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Set;
import java.util.TreeSet;

@SpringBootApplication
public class ThreadApplication {

	public ThreadApplication() throws IOException {
	}

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ThreadApplication.class, args);


		URL otodomURL = new URL("https://www.otodom.pl/pl/oferty/sprzedaz/mieszkanie/krakow");
		BufferedReader in = new BufferedReader(
				new InputStreamReader(otodomURL.openStream()));

		StringBuilder stringBuilder = new StringBuilder();
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			stringBuilder.append(inputLine);
			stringBuilder.append(System.lineSeparator());
		}
		in.close();

		Set<String> setOfLink = new TreeSet<>();
		String content = stringBuilder.toString();
		for (int i = 0; i < content.length(); i++) {
			i = content.indexOf("/pl/oferta/", i);
			if (i < 0)
				break;
			String substring = content.substring(i);
			String prefixToWebsite = "https://www.otodom.pl/";
			String suffixToWebsite = substring.split(" class")[0];
			String link = prefixToWebsite.concat(suffixToWebsite);
			setOfLink.add(link);

		}
		setOfLink.forEach(linkToWeb -> System.out.println(linkToWeb));
		System.out.println(setOfLink.size());
	}

}
