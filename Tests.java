import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.internal.Debug;

import dev.failsafe.internal.util.Assert;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.*;


public class Tests {
    public  static void main(String[] args) { }
    WebDriver driver = new ChromeDriver();

    @Before

   /*  public  void Setup()  
    {
       System.setProperty("webdriver.chrome.driver","C:\\SeleniumJavaPractice\\SeleniumJava\\ChromeDriver\\chromedriver.exe");
               
        driver.get("https://www.google.com/");
        driver.manage().window().maximize();   

    } */

    public  void Setup()  
    {
       System.setProperty("webdriver.chrome.driver","C:\\SeleniumJavaPractice\\SeleniumJava\\ChromeDriver\\chromedriver.exe");
       driver.get("http://sdetchallenge.fetch.com/");       
       driver.manage().window().maximize(); //Normally code would be included to ensure the site/page has loaded completely before running test - SM

    }
  
@Test   
    public void FindFakeGoldBar()//The driver is instantiated in the base class and is called in the setup above.  Or in the case of JUnit the @Before method.
    {    
        WebElement left_0 = driver.findElement(By.id("left_0")); //Note normally this code would be in a page model class for the page.  In this case the weighing page. 
        WebElement left_1 = driver.findElement(By.id("left_1")); 
        WebElement left_2 = driver.findElement(By.id("left_2")); 
        WebElement right_0 = driver.findElement(By.id("right_0"));
        WebElement right_1 = driver.findElement(By.id("right_1")); 
        WebElement right_2 = driver.findElement(By.id("right_2"));
    
        //WebElement reset = driver.findElement(By.id("reset"));
        WebElement weigh = driver.findElement(By.id("weigh"));    
      
        List<String> numbersPossible = new ArrayList<>();       
        int numberOfWeighings = 0;
        String itemFound="o";
    
            left_0.sendKeys("0"); //Again...these would normally be in a page model class and called not in the test itself - SM
            left_1.sendKeys("1");
            left_2.sendKeys("2");
            right_0.sendKeys("6");
            right_1.sendKeys("7");
            right_2.sendKeys("8");              
            weigh.click();          
           
            numberOfWeighings++;            
                     
            try {
                Thread.sleep(10000);
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              } 
              
              driver.findElements(By.id("reset")).get(1).click();
                    
            WebElement getAllResults = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[1]/div[5]/ol/li[1]"));
            String listItem = getAllResults.getAttribute("innerText");
          
                if (listItem.contains("<"))
                {
                    numbersPossible.add("0");
                    numbersPossible.add("1");
                    numbersPossible.add("2");                          
                   
                }
    
                if (listItem.contains(">"))
                {
                    numbersPossible.add("6");
                    numbersPossible.add("7");
                    numbersPossible.add("8") ;                              
                   
                }    
                else
                {
                    numbersPossible.add("3");
                    numbersPossible.add("4");
                    numbersPossible.add("5");                          
                      
                }          
                       
            left_0.sendKeys(numbersPossible.get(0)); 
            right_0.sendKeys(numbersPossible.get(1));   
            weigh.click();
            numberOfWeighings++;

            try {
                Thread.sleep(5000);
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }

              WebElement getAllResults2 = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[1]/div[5]/ol/li[2]"));
              String listItem2 = getAllResults2.getAttribute("innerText");
                         
                if (listItem2.contains("<"))
                {               
                    itemFound = numbersPossible.get(0);               
                }
                if (listItem2.contains(">"))
                {                
                    itemFound = numbersPossible.get(1);                   
                }
                if (listItem2.contains("=")) 
                {
                    itemFound = numbersPossible.get(2);    
                }           

   
         driver.findElement(By.id("coin_" + itemFound)).click();

         String alertText = driver.switchTo().alert().getText(); // Integer.toString(numberOfWeighings)); 
         assertEquals(alertText,"Yay! You find it!");
        
         assertEquals(2,numberOfWeighings);
         //The lightest coin is found with only two weighings.  Beat that my friend....
    
        }
    }