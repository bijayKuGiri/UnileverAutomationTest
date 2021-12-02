package Pages;


import Utility.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import org.testng.Assert;

import java.time.Duration;
import java.util.*;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Home {

    private final RemoteWebDriver driver;

    public Home(RemoteWebDriver _driver) {
        //Helper.WaitForPageLoad(_driver,60);
        PageFactory.initElements(_driver, this);
        driver = _driver;
    }

    //String active_carousel = "//div[@class='cmp-carousel__content']/div[@class='cmp-carousel__item cmp-carousel__item--active']";
    String active_carousel = "//div[@class='cmp-carousel__content']/div[@class='cmp-carousel__item cmp-carousel__item--active']//div[@data-cmp-is='image']";
    String selectedCountry = "";
    String getSiteText = "";
    WebElement faqElement;
    List<String> productImgsBefore;
    List<String> productImgsAfter;
    Boolean prdTabValidation = false;


    @FindBy(css = "ul.cmp-navigation__group")
    WebElement lstHeader;

    @FindBy(xpath = "//a[text()='© 2021 Copyright Unilever ']")
    WebElement lnkCopyWrite;

    @FindBy(xpath = "//div[@class='search-list-label']")
    WebElement searchResult;

    @FindBy(css = "img[title='Magnum Logo']")
    WebElement logo;

    @FindBy(css = ".cmp-accordion__title")
    WebElement lnkNutritionDetails;

    @FindBy(css = "button.cmp-button[data-target='#searchmodal']")
    WebElement icnSearch;

    @FindBy(css = "#searchInput")
    WebElement txtSearch;

    @FindBy(css = ".search-bar-btn")
    WebElement lblSearch;

    @FindBy(css = "section.kr-summary-section>div")
    WebElement summarySection;

   /* @FindBy(css = "kr-aggregateRating kr-Stars")
    WebElement summarySection;*/

    @FindBy(css = "button.cmp-carousel__action.cmp-carousel__action--next>span.cmp-carousel__action-icon")
    WebElement carouselNavigateNext;


    @FindBy(css = "div.cmp-carousel__content")
    WebElement carouselContent;

    @FindBy(xpath = "//footer//div[@class='container responsivegrid'][1]")
    WebElement footerContainer;

    @FindBy(xpath = "//footer//li[contains(@data-cmp-data-layer,'ContactUs') or contains(@data-cmp-data-layer,'contato')]")
    WebElement contactUs;

    @FindBy(css = "div.kr-right-review-area>a")
    WebElement lblWriteReview;


    //    @FindBy(xpath = "//footer//a[@class='cmp-button' and contains(@href,'facebook')]")
    @FindBy(xpath = "//footer//a[contains(@href,'facebook')]")
    WebElement lnkFacebook;

    @FindBy(xpath = "//footer//a[contains(@href,'twitter')]")
    WebElement lnkTwitter;

    @FindBy(xpath = "//a[normalize-space()='Magnum Oversized Towels']")
    WebElement lnkMagnumOverSizedTowel;

    @FindBy(xpath = "//a[normalize-space()='Artigos']")
    WebElement lnkArticle;

    @FindBy(xpath = "//li//div[@class='cmp-teaser__image']//img")
    WebElement imgArticle;

    @FindBy(xpath = "//div[@class='productcarousel carousel c-related-products--fixed']")
    WebElement lnkFeatureProduct;

    @FindBy(xpath = "//div[contains(@class,'button cmp-container--back-to-top')] //button/span")
    WebElement btnBackToTop;

    @FindBy(xpath = "//a[contains(@href,'country')]//span")
    WebElement lnkSelectCountry;

    @FindBy(xpath = "//a[contains(@href,'frequentes') or contains(@href,'FAQ')]//span")
    WebElement lnkFAQ;

    @FindBy(xpath = "//a[contains(@href,'sitemap')]//span")
    WebElement lnkSiteMap;

    @FindBy(xpath = "//div[@class='productcarousel__root']")
    WebElement prodCarousal;

    @FindBy(xpath = "//button[@class='productcarousel__btn productcarousel__btn--next']")
    WebElement prodCarousalNext;

    @FindBy(xpath = "//button[@class='productcarousel__btn productcarousel__btn--prev']")
    WebElement prodCarousalPrev;

    @FindBy(xpath = "//div[@data-cmp-hook-accordion='panel']//div[@class='container responsivegrid']")
    WebElement nutritionDetails;

    @FindBy(xpath = "//ol[@role='tablist' and @class='cmp-tabs__tablist']//parent::div")
    WebElement productTabImages;


    public List<String> getProductImages() {
        List<String> imgSrc = new ArrayList<>();
        var tabElements = productTabImages.findElements(By.xpath("//div[@class='container responsivegrid']//img"))
                .stream().filter(WebElement::isDisplayed).collect(Collectors.toList());
        for (WebElement element : tabElements
        ) {
            imgSrc.add(element.getAttribute("src"));

        }
        return imgSrc;
    }

    public List<String> getProductImages(WebElement item) {
        List<String> imgSrc = new ArrayList<>();
        var tabElements = item.findElements(By.xpath("//parent::ol//parent::div//div[@class='container responsivegrid']//img"))
                .stream().filter(WebElement::isDisplayed).collect(Collectors.toList());
        for (WebElement element : tabElements
        ) {
            imgSrc.add(element.getAttribute("src"));

        }
        return imgSrc;
    }

    public void navigateProductTabs() {
        var tabItems = driver.findElements(By.xpath("//ol[@role='tablist' and @class='cmp-tabs__tablist']"))
                .stream().filter(ele -> ele.findElements(By.tagName("li")).size() > 1)
                .collect(Collectors.toList());
        for (WebElement element : tabItems
        ) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
        }

    }

    public void selectProductTabs() throws InterruptedException {
        var tabItems = driver.findElements(By.xpath("//ol[@role='tablist' and @class='cmp-tabs__tablist']"))
                .stream().filter(ele -> ele.findElements(By.tagName("li")).size() > 1)
                .collect(Collectors.toList());

        for (var item : tabItems
        ) {
            var tabElements = item.findElements(By.tagName("li"));
            Helper.scrollAndClick(driver, tabElements.get(0));

            System.out.println("There count of available tabs are " + tabElements.size());
            for (WebElement tabElement : tabElements) {
                productImgsBefore = getProductImages(item);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", tabElement);
                System.out.println(tabElement.getAttribute("aria-selected"));
                if (tabElement.getAttribute("aria-selected").equalsIgnoreCase("false")) {
                    System.out.println(tabElement.getText() + " going to selected");
                    Helper.scrollAndClick(driver, tabElement);
                }
                Thread.sleep(3000);
                productImgsAfter = getProductImages();
                prdTabValidation = productImgsAfter != productImgsBefore;
            }

        }
    }

    public void VerifyProductImages() {
        Assert.assertTrue(prdTabValidation);
    }

    public List<WebElement> getCarouselList() {
        return carouselContent
                .findElements(By.xpath("//div[@class='cmp-carousel__content']/div[contains(@id,'carousel')]"));

    }

    public void clickOnNutritionLnk() {
        Helper.scrollAndClick(driver, lnkNutritionDetails);
    }

    public void VerifyTheNutritionDetails() {
        Assert.assertTrue(nutritionDetails.isDisplayed(), "Expected nutrition Details should display");
        var elements = nutritionDetails.findElements(By.xpath("//div[contains(@class,'productInfoItem')]" +
                "//*[contains(@class,'product-info-item')][not(descendant::*)]"));
        for (WebElement item :
                elements) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", item);
            System.out.println(item.getText());
        }
    }

    public List<WebElement> getProductCarouselList() {
        return prodCarousal
                .findElements(By.xpath("//div[@class='productcarousel__cardscontainer']//h3")).stream().filter(WebElement::isDisplayed)
                .collect(Collectors.toList());

    }

    public void verifyRotation(List<WebElement> elements) throws InterruptedException {
        for (int i = 0; i < elements.size(); i++) {
            var itemSelected = carouselContent.findElement(By.xpath(active_carousel)).getAttribute("data-cmp-src");
            Helper.scrollAndClick(driver, carouselNavigateNext);
            Thread.sleep(2000);
            var itemSelected_Current = carouselContent.findElement(By.xpath(active_carousel)).getAttribute("data-cmp-src");
            Assert.assertNotEquals(itemSelected, itemSelected_Current);
        }
    }

    public void verifyProductCarouselRotation() throws InterruptedException {

        while (prodCarousalPrev.isEnabled()) {
            Helper.scrollAndClick(driver, prodCarousalPrev);
        }
        var currentItems = getProductCarouselList();
        if (currentItems.size() == driver.findElements(By.xpath("//div[@class='productcarousel__cardscontainer']//div[contains(@id,'productcarousel')]")).size())
            return;


        while (prodCarousalNext.isEnabled()) {
            Helper.scrollAndClick(driver, prodCarousalNext);
            Thread.sleep(5000);
            var newItems = getProductCarouselList();
            System.out.println(currentItems.size());
            System.out.println(newItems.size());
            Assert.assertNotEquals(newItems, currentItems);
        }

    }


    public void getFirstCarousel(List<WebElement> elements) throws InterruptedException {
        for (int i = 0; i < elements.size(); i++) {
            WebElement itemSelected = carouselContent.findElement(By.xpath(active_carousel));
            if (itemSelected.findElement(By.tagName("a")).getAttribute("href").contains("ifoodbr")) {
                itemSelected.click();
                break;
            }
            carouselNavigateNext.click();
            Thread.sleep(2000);

        }

    }

    public List<WebElement> getProducts() {
        return driver.findElements(By.xpath("//div[contains(@class,'button button--primary') or contains(@class,'button button--secondary')]//a"));
        ////a[@class='cmp-button' and not(@target)]//span[@class='cmp-button__text']
    }

   /* public List<String> getLinkText() {
        List<String> linkTxt = new ArrayList<>();
        List<WebElement> links = lstHeader.findElements(By.tagName("li"));
        for (WebElement var : links) {
            linkTxt.add(var.findElement(By.tagName("a")).getAttribute("href"));
        }
        return linkTxt;
    }*/

    public List<WebElement> getLink() {
        return lstHeader.findElements(By.tagName("li"));
    }

    public List<WebElement> getFooterLink() {
        return footerContainer.findElements(By.tagName("li"));
    }

   /* public List<String> getFooterLinkText() {
        List<String> linkTxt = new ArrayList<>();
        List<WebElement> links = footerContainer.findElements(By.tagName("li"));
        for (WebElement var : links) {
            linkTxt.add(var.findElement(By.tagName("a")).getAttribute("href"));
        }
        return linkTxt;
    }*/

    public String getHeader() {
        return driver.getTitle();
    }

    public boolean IsCopyRightExist() {
        return lnkCopyWrite.isDisplayed();
    }

    public boolean IsSummeryExist() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(summarySection));
        return summarySection.isDisplayed();
    }

    public boolean IsLogoImageDisplayed() {
        return logo.isDisplayed();
    }

    public String logoText() {
        return logo.getAttribute("alt");
    }


   /* public void getScreenShot() throws InterruptedException, IOException {
        System.out.println(logo.getAttribute("src"));
        Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(driver, logo);
        ImageIO.write(screenshot.getImage(), "PNG", new File(System.getProperty("user.dir") + "Logo.png"));
        Thread.sleep(2000);
    }*/

    public ContactUs navContactUs() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", contactUs);
        //Helper.click(driver, contactUs);
        contactUs.click();
        return new ContactUs(driver);
    }

    public void selectSiteMap() throws InterruptedException {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", lnkSiteMap);
        Helper.click(driver, lnkSiteMap);
        Thread.sleep(5000);
    }

    public void selectFAQ() throws InterruptedException {
//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", lnkFAQ);
//        Helper.click(driver, lnkFAQ);
        Helper.scrollAndClick(driver, lnkFAQ);
        Thread.sleep(5000);
        Helper.WaitForPageLoad(driver, 60);
    }

    public void selectAnySite() {
        ////a[@class='cmp-list__item-link']
        List<WebElement> lstElements = driver.findElements(By.xpath("//div[@id='contentWrapperSection']//a"));
        Random rand = new Random();
        int upperbound = lstElements.size() - 1;
        int int_random = rand.nextInt(upperbound);
        WebElement element = lstElements.get(int_random);
        getSiteText = driver.getCurrentUrl();
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
        driver.executeScript("arguments[0].click();", element);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

    }

    public boolean verifySitemapRedirection() {
        return !driver.getCurrentUrl().equals(getSiteText);
    }


    public void selectFAQAns() {
        List<WebElement> lstElements = driver.findElements(By.cssSelector("span.cmp-accordion__icon"));
        Random rand = new Random();
        int upperbound = lstElements.size() - 1;
        int int_random = rand.nextInt(upperbound);
        faqElement = lstElements.get(int_random);
        /*((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", faqElement);
        driver.executeScript("arguments[0].click();", faqElement);*/
        Helper.scrollAndClick(driver, faqElement);
    }

    public boolean isAnswerDisplay() {
        return faqElement.findElement(By.xpath("parent::button/parent::*[self::h1 or self::h2 or self::h3]/following-sibling::div")).isDisplayed();
    }

    public void clickCrossMark() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", faqElement);
        driver.executeScript("arguments[0].click();", faqElement);
    }

    public RemoteWebDriver selectCountry() {
        Helper.scrollAndClick(driver, lnkSelectCountry);
        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        List<WebElement> lstElements = driver.findElements(By.xpath("//a[@class='cmp-languagenavigation__item-link']"));
        Random rand = new Random();
        int upperbound = lstElements.size() - 1;
        int int_random = rand.nextInt(upperbound);
        selectedCountry = lstElements.get(int_random).getText();
        Helper.scrollAndClick(driver, lstElements.get(int_random));
        ArrayList<String> tabs2 = new ArrayList<>(driver.getWindowHandles());
        if (tabs2.size() > 1) {
            driver.switchTo().window(tabs2.get(1));
        }
        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        return driver;
    }

    public boolean isCountrySelected() {
        if (Objects.equals(selectedCountry, "Brazil"))
            return driver.getTitle().contains("Magnum Brasil");
        else
            return !driver.getTitle().contains("Magnum Brasil");
    }


    public void BackToStartClick() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", btnBackToTop);
        Helper.click(driver, btnBackToTop);

    }

    public boolean isHeaderCausalDisplay() {
        return carouselContent.isDisplayed();
    }

    public RemoteWebDriver navFacebook() {
        Helper.scrollAndClick(driver, lnkFacebook);
        var tabs2 = new ArrayList<>(driver.getWindowHandles());
        if (tabs2.size() > 1) {
            driver.switchTo().window(tabs2.get(1));
        }
        return driver;
    }

    public void navArticlePageByImg() {
        /*((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", imgArticle);
        Helper.click(driver, imgArticle);*/
        Helper.scrollAndClick(driver, imgArticle);
    }

    public boolean IsUrlContainArgos() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOf(lnkFeatureProduct));
        return driver.getCurrentUrl().contains("artigos");
    }

    public boolean IsNavigateFacebook(RemoteWebDriver driver) {
        return driver.getCurrentUrl().contains("facebook.com");
    }

    public Review navReview() {
        WebDriverWait wait = new WebDriverWait(driver, 120);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("section.kr-summary-section")));
        Helper.clickItem(driver, summarySection);
        //summarySection.click();
        //driver.executeScript("arguments[0].click();",summarySection);
        Helper.scrollAndClick(driver, lblWriteReview);
        var review = new Review(driver);
        review.WaitForReviewPageToLoad(15);
        return review;
    }

    public RemoteWebDriver navTwitter() {
        Helper.scrollAndClick(driver, lnkTwitter);
        var tabs2 = new ArrayList<>(driver.getWindowHandles());
        if (tabs2.size() > 1) {
            driver.switchTo().window(tabs2.get(1));
        }
        return driver;
    }

    public boolean IsNavigateTwitter(RemoteWebDriver driver) {
        return driver.getCurrentUrl().contains("twitter.com");
    }

    public void search(String productName) throws InterruptedException {
        Helper.click(driver, icnSearch);
        Helper.EnterText(driver, txtSearch, productName);
        Helper.click(driver, lblSearch);
        Thread.sleep(5000);
        var item = Helper.FindElement(driver, By.xpath("//div[@class='search-list-label']"), 120);
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.visibilityOf(item));
        Assert.assertTrue(item.isDisplayed());

    }

    public int getSearchCount() {

        var item = Helper.findElement(driver, By.xpath("//div[@class='search-list-label']"), 60);
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(item.getText());
        if (m.find())
            System.out.println(m.group(0));
        return Integer.parseInt(m.group(0));
        //return driver.findElements(By.cssSelector(".search-list-label")).size();
    }


    public boolean IsNavigateToIfoodPage() throws InterruptedException {
        Thread.sleep(2000);
        ArrayList<String> tabs2 = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(1));
        return driver.getCurrentUrl().contains("https://www.ifood.com");
    }

    public void isProductCarousalDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOf(prodCarousal));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", prodCarousal);
        Assert.assertTrue(prodCarousal.isDisplayed(), "Expected Product Carousal should displayed");
    }

    public MagnumTowel navMagnumOversizeTowel() {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOf(lnkMagnumOverSizedTowel)).click();
        return new MagnumTowel(driver);
    }

    public Article navArticlePage() {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOf(lnkArticle)).click();
        return new Article(driver);
    }

    public RemoteWebDriver NavTermsUse() {
        var element = footerContainer.findElements(By.tagName("li"));
        Helper.scrollAndClick(driver, element.get(3).findElement(By.tagName("a")));
        var tabs2 = new ArrayList<>(driver.getWindowHandles());
        if (tabs2.size() > 1) {
            driver.switchTo().window(tabs2.get(1));
        }
        return driver;

    }

    public RemoteWebDriver NavCookiesNotice() {
        var element = footerContainer.findElement(By.xpath("//li[contains(@data-cmp-data-layer,'cookie-notice')]"));
        Helper.scrollAndClick(driver, element.findElement(By.tagName("a")));
        var tabs2 = new ArrayList<>(driver.getWindowHandles());
        if (tabs2.size() > 1) {
            driver.switchTo().window(tabs2.get(1));
        }
        return driver;

    }

    public RemoteWebDriver NavPrivacyNotice() {
        var element = footerContainer.findElement(By.xpath("//li[contains(@data-cmp-data-layer,'privacy-notice')] "));
        Helper.scrollAndClick(driver, element.findElement(By.tagName("a")));
        ArrayList<String> tabs2 = new ArrayList<>(driver.getWindowHandles());
        if (tabs2.size() > 1) {
            driver.switchTo().window(tabs2.get(1));
        }
        return driver;

    }

    public RemoteWebDriver NavSignUp() {
        var element = footerContainer.findElements(By.tagName("li"));
        Helper.scrollAndClick(driver, element.get(1).findElement(By.tagName("a")));
        ArrayList<String> tabs2 = new ArrayList<>(driver.getWindowHandles());
        if (tabs2.size() > 1) {
            driver.switchTo().window(tabs2.get(1));
        }
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='form-paragraph-info']")));
        return driver;

    }

    public boolean IsNavigateTermsOfUse(RemoteWebDriver driver) {
        return driver.getCurrentUrl().contains("https://www.unilever.com.br/legal/");
    }

    public boolean IsNavigatePrivacyNotice(RemoteWebDriver driver) {
        return driver.getCurrentUrl().contains("privacy-notice");
    }

    public boolean IsNavigateCookiesNotice(RemoteWebDriver driver) {
        return driver.getCurrentUrl().contains("cookie-notice");
    }
}
