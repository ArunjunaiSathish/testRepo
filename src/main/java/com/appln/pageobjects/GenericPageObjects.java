package com.appln.pageobjects;

import com.appln.wrappers.GenericWrappers;

public class GenericPageObjects extends GenericWrappers {


	// Mobile Web Objects
	public static final String MMainPage_MenuIcon_Button = "//button[starts-with(@class,'btn-link')]";
	public static final String MMainPage_SignIn_Link = "//a[contains(text(),'Sign in')]";
	public static final String MLoginPage_UserName_Tbox = "//input[@name='login']";
	public static final String MLoginPage_Password_Tbox = "//input[@name='password']";
	public static final String MLoginPage_SignIn_Button = "/button[@class='btn btn-block']";
	public static final String MHomePage_Search_Tbox = "//input[@name='q']";
	public static final String MHomePage_SearchResults_List = "//a[@class='list-item repo-list-item ']/strong[@class='list-item-title']";
	public static final String MRepositoryHomePage_SearchResults_TopBanner_Links = "//a[@itemprop='url']/span";
	public static final String MRepositoryHomePage_IssueAdd_Button = "//a[@class='header-button header-context-button']";
	public static final String MRepositoryHomePage_IssueTitle_Tbox = "//input[@name='issue[title]']";
	public static final String MRepositoryHomePage_Issue_Tbox = "//textarea[@name='issue[body]']";
	public static final String MRepositoryHomePage_Star_Button ="//button[@class='bubble-action']";
	public static final String MRepositoryHomePage_MenuIcon_Button = "//button[@class='header-button header-nav-button touchable js-show-global-nav']";
	public static final String MRepositoryHomePage_Menu_Links = "//nav[@class='nav-bar-tabs ']/ul/li/a";
	public static final String MRepositoryHomePage_IssueSubmit_Button = "/button[@class='btn btn-block']";
	
	public static final String MHomePage_SearchRepo_ListLinks = "//a[@class='list-item repo-list-item']/div";
	public static final String MHomePage_SearchRepo_ListCount_Links = "//a[@class='list-item repo-list-item']/strong";
}
