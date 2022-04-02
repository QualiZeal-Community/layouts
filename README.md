# Layouts

## Why use layouts
Layouts is an attempt to remove redundancies while creating page-objects and the large amount of boilerplate methods on each of the page element. At the same time layouts also emphasize on further organizing the pages into multiple sections within the yaml. It's opinionated in this aspect and expect a specific pattern to be followed. Layout basically remains as a placeholder for all the static page objects in the application under test.

## Layout Yaml

```yaml
---
name: HomePage
sections:
  - name: Main
    locators:
      - name: Welcome Message
        web: //h1
        mobile: //*[@acccesibility-id='welcome']
      - name: Canvas
        web: //canvas
        ios: //*[@acccesibility-id='welcomeIOS']
        android: //*[@acccesibility-id='welcomeDroid']
  - name: SideNav
    locators: 
      - name: Home Icon
        web: "#homeIcon"
        mobile: "#homeIcon"
---
name: AccountsPage
sections:
  - name: Main
    locators:
      - name: Account Header
        web: h1.green
---
```
## Usage
```java
//get layout map
Map<String, Layout> layouts = new LayoutParser().parseLayouts("pages/testPages.yaml");
Layout homePage = layouts.get("HomePage");
homePage.setSection("SideNav");
homePage.get("Welcome Message");

//get single layout
Layout accountsPage = new LayoutParser().parseLayout("AccountsPage", "pages/testPages.yaml");

//merge multiple yaml files into a single layout map
Map<String, Layout> allLayouts = new LayoutParser().parseAndMergeLayouts(
        "pages/testPages.yaml",
        "pages/anotherTestPage.yaml");
```
## Bugs and Feature requests
You can register bugs and feature request using the [GitHub Issue Tracker](https://github.com/QualiZeal-Community/layouts/issues)

Please bear in mind that this project is almost entirely developed by volunteers. If you do not provide the implementation yourself, the bug might never get fixed. If it is a serious bug, other people than you might care enough to provide a fix.

