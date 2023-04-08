package pro.sky.attestation.AutomationOfStockAccounting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;

@SpringBootApplication
public class AutomationOfStockAccountingApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutomationOfStockAccountingApplication.class, args);


//		headless - без UI
		System.setProperty("java.awt.headless", "true");
		Toolkit tk = Toolkit.getDefaultToolkit();
		GraphicsEnvironment ge =
				GraphicsEnvironment.getLocalGraphicsEnvironment();
		System.out.println("Headless mode: " + ge.isHeadless());

	}

}
