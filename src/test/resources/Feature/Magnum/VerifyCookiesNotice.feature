Feature: Validate the CookiesNotice link in Home Page

  @P0
  @footer
  @brazil
  @magnum
  @All
  @thailand
  Scenario: Verifying CookiesNotice link navigation in Home Page
    Given The site is Up and Running
    When i try to click on CookiesNotice link in Home Page
    Then the page should redirect to CookiesNotice page from Home Page