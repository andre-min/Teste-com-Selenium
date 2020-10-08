package wiki;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@RunWith(Parameterized.class)
public class Busca {  //yyyy-MM-dd HH-mm"
	String url;
	WebDriver driver;
	static String pastaFoto = new SimpleDateFormat("yyyy-MM-dd HH-mm").format(Calendar.getInstance().getTime());
  //static String pastaFoto = new SimpleDateFormat("yyyy-MM-dd HH-mm").format(Calendar.getInstance().getTime());
	//métodos de apoio
	
	
	//Método para tirar print (screenshort)
	public void print(String nomeFoto) throws IOException{
		File foto = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(foto, new File("C:\\Users\\Leandro\\FTS126\\wiki\\target\\" + pastaFoto + "\\" +nomeFoto + ".png"));
	}
	
	//Método para ler um arquivo csv - Comma Separeted Valueds
	String id;
	String termo;
	String resultado;
	String tipo;
	String browser;
	
	//construtor para a leitura dos campos
	public Busca(String id, String termo, String resultado, String tipo, String browser) {
		this.id = id;
		this.termo = termo;
		this.resultado = resultado;
		this.tipo = tipo;
		this.browser = browser;
	}
	
	@Parameters
	//Collection que informa o local e o nome do arquivo de massa
	public static Collection <String[]> llerArquivo() throws IOException {
		return lerCSV("C:\\Users\\Leandro\\FTS126\\wiki\\DB\\massaWIKI.csv");
	}
	

	public static Collection<String[]> lerCSV(String nomeMassa) throws IOException {
		//Realmente lê o arquivo massaWIKI.csv
		String linha;
		List<String[]> dados = new ArrayList<String[]>();
		BufferedReader arquivo = new BufferedReader(new FileReader(nomeMassa));
		while((linha = arquivo.readLine()) != null) {
			String campos[] = linha.split(";");
			dados.add(campos);
			
		}
		arquivo.close();
			return dados;
		
		
	}
	
	
	

	@Before
	public void iniciar() {
		url = "https://pt.wikipedia.org";
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\Leandro\\FTS126\\wiki\\drivers\\chrome 85\\chromedriver.exe");
					driver = new ChromeDriver();
					driver.manage().window().maximize();
					driver.manage().timeouts().implicitlyWait(60000, TimeUnit.MILLISECONDS);
	

	}
	
	@After
	public void finalizar() {
		driver.quit();

	}
	@Test
	public void buscar() throws IOException {
		driver.get(url);// abrir o navegador na página alvo
		driver.findElement(By.id("searchInput")).sendKeys(Keys.chord(termo));
		print("Passo 1 - Consulta pelo termo");
		driver.findElement(By.id("searchInput")).sendKeys(Keys.ENTER);
		
		assertEquals(resultado, driver.findElement(By.id("firstHeading")).getText());
		print("Passo 2 - Valida o resultado");
	}
	
	
	
	
}
